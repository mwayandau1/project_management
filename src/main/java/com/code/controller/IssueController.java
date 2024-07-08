package com.code.controller;


import com.code.model.Issue;
import com.code.model.User;
import com.code.request.IssueDTO;
import com.code.request.IssueRequest;
import com.code.service.IssueService;
import com.code.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/issues")
public class IssueController {


    @Autowired
    private IssueService issueService;

    @Autowired
    private UserService userService;



    @GetMapping("/{issueId}")
    public ResponseEntity<Issue> getIssueById(@PathVariable Long issueId) throws Exception{
        return new ResponseEntity<>( issueService.getIssueById(issueId), HttpStatus.OK);
    }


    @GetMapping("/project/{projectId}")
    public ResponseEntity<List<Issue>> getIssuesByProjectId(@PathVariable Long projectId) throws Exception{
        return new ResponseEntity<>(issueService.getIssuesByProjectId(projectId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<IssueDTO> createIssue(@RequestBody IssueRequest issueRequest, @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);

        Issue createdIssue = issueService.createIssue(issueRequest, user);

        IssueDTO issueDTO = new IssueDTO();
        issueDTO.setTitle(createdIssue.getTitle());
        issueDTO.setDescription(createdIssue.getDescription());
        issueDTO.setPriority(createdIssue.getPriority());
        issueDTO.setTags(createdIssue.getTags());
        issueDTO.setAssignee(createdIssue.getAssignee());
        issueDTO.setProjectID(createdIssue.getProjectID());
        issueDTO.setPriority(createdIssue.getPriority());
        issueDTO.setProject(createdIssue.getProject());
        issueDTO.setStatus(createdIssue.getStatus());



        return new ResponseEntity<>(issueDTO, HttpStatus.OK);
    }


    @DeleteMapping("/{issueId}")
    public ResponseEntity<String> deleteIssue(@PathVariable Long issueId, @RequestHeader("Authorization") String jwt) throws Exception{
        User user = userService.findUserProfileByJwt(jwt);

        issueService.deleteIssue(issueId, user.getId());
        return new ResponseEntity<>("Issue has been deleted!", HttpStatus.OK);
    }


    @PutMapping("/issueId/assignee/{userId}")
    public ResponseEntity<Issue> addUserToIssue(@PathVariable Long issueId, @PathVariable Long userId, @RequestHeader("Authorization") String jwt) throws Exception{
        userService.findUserProfileByJwt(jwt);
        return new ResponseEntity<>(issueService.addUserToIssue(issueId, userId), HttpStatus.OK);
    }


    @PutMapping("/{issueId}/status/{status}")
    public ResponseEntity<Issue> updateIssueStatus(@PathVariable String status, @RequestBody Long issueId, @RequestHeader("Authorization") String jwt ) throws Exception{
        userService.findUserProfileByJwt(jwt);

        Issue updatedIssue = issueService.updateIssueStatus(issueId, status);
        return new ResponseEntity<>(updatedIssue, HttpStatus.OK);
    }
}

package com.code.controller;


import com.code.model.Project;
import com.code.model.User;
import com.code.response.MessageResponse;
import com.code.service.ProjectService;
import com.code.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("api/projects")
public class ProjectController {

    @Autowired
    private ProjectService projectService;


    @Autowired
    private UserService userService;


    @GetMapping
    public ResponseEntity<List<Project>> getAllProjects(@RequestParam(required = false) String category,
                                                        @RequestParam(required = false) String tag,
                                                        @RequestHeader("Authorization") String jwt
                                                        )throws Exception{
        User user   = userService.findUserProfileByJwt(jwt);
        List<Project> projects = projectService.getProjectByTeam(user, category, tag);
        return new ResponseEntity<>(projects, HttpStatus.OK);
    }

    @GetMapping("/{projectId}")
    public ResponseEntity<Project> getProjectById(@PathVariable Long projectId, @RequestHeader("Authorization") String jwt) throws Exception{
        userService.findUserProfileByJwt(jwt);
        Project project = projectService.getProjectById(projectId);
        return new ResponseEntity<>(project, HttpStatus.OK);
    }


    @PostMapping
    public ResponseEntity<Project> createProject(@PathVariable Long projectId,
                                                 @RequestHeader("Authorization") String jwt,
                                                 @RequestBody Project project) throws Exception {

        User user = userService.findUserProfileByJwt(jwt);
        Project createdProject = projectService.createProject(project, user);

        return new ResponseEntity<>(createdProject, HttpStatus.CREATED);
    }

    @PatchMapping("/{projectId}")
    public ResponseEntity<Project> updateProject(@PathVariable Long projectId,
                                          @RequestHeader("Authorization") String jwt,
                                          @RequestBody Project project) throws Exception{
        userService.findUserProfileByJwt(jwt);
        Project updateProject= projectService.updateProject(project, projectId);
        return new ResponseEntity<>(updateProject, HttpStatus.OK);
    }

    @DeleteMapping("/{projectId}")
    public ResponseEntity<MessageResponse> deleteProject(@PathVariable Long projectId, @RequestHeader("Authorization") String jwt) throws Exception{
        User user = userService.findUserProfileByJwt(jwt);

        projectService.deleteProject(projectId, user.getId());
        MessageResponse response = new MessageResponse("Project deleted");
      return  new ResponseEntity<>(response, HttpStatus.OK);

    }

    public ResponseEntity<List<Project>> searchProject(@RequestParam String keyword, @RequestHeader("Authorization") String jwt) throws Exception{
        User user = userService.findUserProfileByJwt(jwt);
        List<Project> projects = projectService.searchedProjects(keyword,user );
        return  new ResponseEntity<>(projects, HttpStatus.OK);
    }
}

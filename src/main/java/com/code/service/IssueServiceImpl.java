package com.code.service;


import com.code.model.Issue;
import com.code.model.Project;
import com.code.model.User;
import com.code.repository.IssueRepository;
import com.code.request.IssueRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IssueServiceImpl implements IssueService{

    @Autowired
    private IssueRepository issueRepository;

   @Autowired
    private ProjectService projectService;

   @Autowired
   private UserService userService;

    @Override
    public Issue getIssueById(Long issueId) throws Exception {
        Optional<Issue> optionalIssue = issueRepository.findById(issueId);
        
        if(optionalIssue.isPresent())
            return optionalIssue.get();

        throw  new Exception("Issue not found!");
    }

    @Override
    public List<Issue> getIssuesByProjectId(Long projectId) throws Exception {
        return issueRepository.getIssueByProjectId(projectId);
    }

    @Override
    public Issue createIssue(IssueRequest issue, User user) throws Exception {
        Project project = projectService.getProjectById(issue.getProjectID());
        Issue newIssue = new Issue();

        newIssue.setTitle(issue.getTitle());
//        newIssue.setAssignee(user);
        newIssue.setDescription(issue.getDescription());
        newIssue.setProjectID(issue.getProjectID());
        newIssue.setStatus(issue.getStatus());
        newIssue.setDueDate(issue.getDueDate());
        newIssue.setPriority(issue.getPriority());
        newIssue.setProject(project);
        return issueRepository.save(newIssue);
    }

    @Override
    public void deleteIssue(Long issueId, Long userId) throws Exception {

        getIssueById(issueId);
        issueRepository.deleteById(issueId);

    }

    @Override
    public Issue addUserToIssue(Long issueId, Long userId) throws Exception {
        User user = userService.findUserById(userId);
        Issue issue = getIssueById(issueId);

        issue.setAssignee(user);
        return issueRepository.save(issue) ;
    }

    @Override
    public Issue updateIssueStatus(Long issueId, String status) throws Exception {
        Issue issue = getIssueById(issueId);
        issue.setStatus(status);
        return issueRepository.save(issue);
    }
}

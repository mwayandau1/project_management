package com.code.service;

import com.code.model.Issue;
import com.code.model.User;
import com.code.request.IssueRequest;

import java.util.List;
import java.util.Optional;

public interface IssueService {

  Issue getIssueById(Long issueId) throws Exception;

    List<Issue> getIssuesByProjectId(Long projectId) throws Exception;

    Issue createIssue(IssueRequest issue, User user) throws Exception;

    void deleteIssue(Long issueId, Long userId) throws Exception;

    Issue addUserToIssue(Long issueId, Long userId) throws Exception;

    Issue updateIssueStatus(Long issueId, String status) throws Exception;
}

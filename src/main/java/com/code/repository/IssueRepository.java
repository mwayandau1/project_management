package com.code.repository;

import com.code.model.Issue;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IssueRepository extends JpaRepository<Issue, Long> {

    public List<Issue> getIssueByProjectId(Long projectId);
}

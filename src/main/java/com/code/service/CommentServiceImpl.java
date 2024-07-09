package com.code.service;


import com.code.model.Comment;
import com.code.model.Issue;
import com.code.model.User;
import com.code.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private IssueService issueService;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserService userService;

    @Override
    public Comment createComment(Long issueId, Long userId, String comment) throws Exception {
        Issue issue = issueService.getIssueById(issueId);
        User user = userService.findUserById(userId);
        Comment newComment = new Comment();
        newComment.setContent(comment);
        newComment.setUser(user);
        newComment.setIssue(issue);
        newComment.setCreatedDateTime(LocalDateTime.now());

        Comment savedComment = commentRepository.save(newComment);
        issue.getComments().add(savedComment);
        return savedComment;
    }

    @Override
    public void deleteComment(Long commentId, Long userId) throws Exception {

        User user = userService.findUserById(userId);
        try {
            commentRepository.findById(commentId);
            commentRepository.deleteById(commentId);
        } catch (Exception e) {
            throw new Exception("No comment found with this id " + commentId);
        }

    }

    @Override
    public List<Comment> findCommentByIssueId(Long issueId) {
        return commentRepository.findByIssueId(issueId);
    }
}

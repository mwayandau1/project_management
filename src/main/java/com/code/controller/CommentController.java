package com.code.controller;


import com.code.model.Comment;
import com.code.model.User;
import com.code.request.CommentRequest;
import com.code.response.MessageResponse;
import com.code.service.CommentService;
import com.code.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private UserService userService;


    @PostMapping("/")
    public ResponseEntity<Comment> createComment(@RequestBody CommentRequest commentRequest,
                                                 @RequestHeader("Authorization") String jwt) throws Exception {

    User user = userService.findUserProfileByJwt(jwt);
    Comment createdComment = commentService.createComment(commentRequest.getIssueId(), user.getId(), commentRequest.getContent());

        return new ResponseEntity<>(createdComment, HttpStatus.CREATED);

    }


    @GetMapping("/{commentId}")
    public ResponseEntity<MessageResponse> deleteComment(@PathVariable Long commentId, @RequestHeader("Authorization") String jwt) throws Exception{
        User user = userService.findUserProfileByJwt(jwt);
        commentService.deleteComment(commentId, user.getId());
        MessageResponse messageResponse = new MessageResponse();

        messageResponse.setMessage("Comment deleted successfully");
        return new ResponseEntity<>(messageResponse, HttpStatus.OK);
    }



    @GetMapping("/{issueId}")
    public ResponseEntity<List<Comment>> commentsByIssueId(@PathVariable Long issueId) throws Exception{
        return new ResponseEntity<>(commentService.findCommentByIssueId(issueId), HttpStatus.OK);
    }
}


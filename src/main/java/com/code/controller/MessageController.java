package com.code.controller;


import com.code.model.Chat;
import com.code.model.Message;
import com.code.model.User;
import com.code.request.MessageRequest;
import com.code.service.MessageService;
import com.code.service.ProjectService;
import com.code.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/messages")
public class MessageController {
    @Autowired
    private MessageService messageService;

    @Autowired
    private UserService userService;

    @Autowired
    private ProjectService projectService;


    public ResponseEntity<Message> sendMessage(@RequestBody MessageRequest request) throws Exception{
        User user = userService.findUserById(request.getSenderId());
        if(user == null){
            throw new Exception("User not found");
        }

        Chat chat = projectService.getProjectById(request.getProjectId()).getChat();
        if(chat == null){
            throw new Exception("No chat found with this id");
        }

        Message sendMessage = messageService.sendMessage(request.getSenderId(), request.getProjectId(), request.getContent());
        return ResponseEntity.ok(sendMessage);
    }


    public ResponseEntity<List<Message>> getMessagesByProjectId(@PathVariable Long projectId) throws Exception{
        List<Message> messages = messageService.getMessagesByProjectId(projectId);
        return ResponseEntity.of(Optional.ofNullable(messages));
    }



}

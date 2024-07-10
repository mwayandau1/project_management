package com.code.service;


import com.code.model.Chat;
import com.code.model.Message;
import com.code.model.User;
import com.code.repository.MessageRepository;
import com.code.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MessageServiceImpl implements MessageService{


    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProjectService projectService;

    @Override
    public Message sendMessage(Long senderId, Long projectId, String content) throws Exception {

        User user = userRepository.findById(senderId).orElseThrow(()->new Exception("No user found with this Id "+ senderId));
        Chat chat = projectService.getProjectById(projectId).getChat();

        Message message = new Message();
        message.setContent(content);
        message.setSender(user);
        message.setCreatedAt(LocalDateTime.now());
        message.setChat(chat);

        Message saveMessage = messageRepository.save(message);

        chat.getMessages().add(saveMessage);
        return saveMessage;
    }

    @Override
    public List<Message> getMessagesByProjectId(Long projectId) throws Exception {
        Chat chat = projectService.getChatProjectId(projectId);
        return messageRepository.findByChatIdOrderByCreatedAtAsc(chat.getId());
    }
}

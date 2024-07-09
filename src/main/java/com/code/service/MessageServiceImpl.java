package com.code.service;


import com.code.model.Message;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageServiceImpl implements MessageService{
    @Override
    public Message sendMessage(Long senderId, Long chatId, String content) throws Exception {
        return null;
    }

    @Override
    public List<Message> getMessagesByProjectId(Long projectId) throws Exception {
        return List.of();
    }
}

package com.stackroute.chatservice.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.stackroute.chatservice.exception.ChatNotFoundException;
import com.stackroute.chatservice.model.Chat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.stackroute.chatservice.repository.ChatRepository;

@Service
public class ChatServiceImpl implements ChatService {
    @Autowired
    private ChatRepository chatRepository;

    public Chat saveChat(Chat chat) {
        int min = 1;
        int max = 99999;
        chat.setMessageId((long) (Math.random() * (max - min) + min));
        chat.getMessage().get(0).setDatetime(new Date());
        return chatRepository.save(chat);
    }

    @Override
    public Chat updateChat(Chat chat, Long messageId) {
        Chat existingmessage = chatRepository.findById(messageId).orElseThrow(
                () -> new ChatNotFoundException("Message with Id" + messageId + "not Found"));
        existingmessage.setMessage(chat.getMessage());
        chatRepository.save(existingmessage);
        chat.getMessage().get(0).setDatetime(new Date());
        return existingmessage;
    }

    @Override
    public Chat getChatByMessageId(long messageId) {
        Optional<Chat> chat = chatRepository.findById(messageId);
        if (chat.isPresent()) {
            return chat.get();
        } else {
            throw new ChatNotFoundException("No reply found by Message with Id " + messageId);
        }

    }

    @Override
    public void deleteChatByMessageId(long messageId) {
        chatRepository.findById(messageId).orElseThrow(()
                -> new ChatNotFoundException("There is no chat found by Id" + messageId));
        chatRepository.deleteById(messageId);
    }

    @Override
    public List<Chat> getChatByCustomerId(long customerId) {
        if (chatRepository.findByCustomerId(customerId).isEmpty()) {
            throw new ChatNotFoundException("Chat is not present with Customer Id" + customerId);
        }
        return chatRepository.findByCustomerId(customerId);
    }


    @Override
    public Chat placeMessageInTheChat(long hotelId) {
        Chat chat = chatRepository.findById(hotelId).orElseThrow(
                () -> new ChatNotFoundException("Chat does not Exist with Id" + hotelId));
        chatRepository.save(chat);
        return chat;
    }


}
package com.stackroute.chatservice.Repository;

import com.stackroute.chatservice.model.Chat;
import com.stackroute.chatservice.model.Message;
import com.stackroute.chatservice.repository.ChatRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;


import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@DataMongoTest

public class ChatRepositoryTest {

    private List<Message> message;

    @Autowired
    private ChatRepository chatRepository;

    private Chat chat = new Chat(44l,55l,66l,message);


    @BeforeEach
    public void setUp() {
        chat = new Chat (44l,55l,66l,message);
        new Message(4l,"Hii",null);
    }
    @AfterEach
    public void tearDown() throws Exception{
        chat = null;
        chatRepository.deleteAll();
    }

    @Test
    public void findByCustomerId() {
        chatRepository.insert(chat);
        List<Chat> list = chatRepository.findByCustomerId(chat.getCustomerId());
        assertEquals(1, list.size());
        assertEquals(chat.getCustomerId(), list.get(0).getCustomerId());
    }



}

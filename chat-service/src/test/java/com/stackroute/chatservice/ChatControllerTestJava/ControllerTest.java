//package com.stackroute.chatservice.ChatControllerTestJava;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.stackroute.chatservice.Service.ChatService;
//import com.stackroute.chatservice.controller.ChatController;
//import com.stackroute.chatservice.model.Chat;
//import com.stackroute.chatservice.model.Message;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.MockMvcBuilder;
//
//import java.util.ArrayList;
//import java.util.List;
//
////import org.springframework.test.web.servlet.MockMvc;
////import com.fasterxml.jackson.databind.ObjectMapper;
////import com.stackroute.chatservice.Service.ChatService;
////import com.stackroute.chatservice.controller.ChatController;
////import com.stackroute.chatservice.model.Chat;
////import com.stackroute.chatservice.model.Message;
////import org.junit.jupiter.api.AfterEach;
////import org.junit.jupiter.api.BeforeEach;
////import org.junit.jupiter.api.extension.ExtendWith;
////import org.mockito.InjectMocks;
////import org.mockito.Mock;
////import org.mockito.junit.jupiter.MockitoExtension;
////import org.springframework.beans.factory.annotation.Autowired;
////import org.springframework.test.web.servlet.MockMvc;
////
////import java.util.ArrayList;
////import java.util.List;
////import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//
//@ExtendWith(MockitoExtension.class)
//public class ControllerTest {
//    @Autowired
//    MockMvc mockMvc;
//
//    @Mock
//    private ChatService chatService;
//
//    @InjectMocks
//    private ChatController chatController;
//
//    List<Chat> chats;
//    List<Message>message;
//    Message update_message;
//
//    public  static  String asJsonString(final Object obj){
//        try{
//            return new ObjectMapper().writeValueAsString(obj);
//        }catch (Exception e){
//            throw new RuntimeException(e);
//        }
//    }
//
//    @BeforeEach
//    public  void  init() {
//        mockMvc = MockMvcBuilder.standaloneSetup(chatController).build();
//        chats = new ArrayList<>();
//        message = new ArrayList<>();
//        message= (List<Message>) new Message(4l, "Hello" ,null);
//        message.add(message);
//        chats.add(new Chat(1l, 2l, 3l, message));
//
//    }
//    @AfterEach
//    public void  close() {
//        message.clear();
//        chats.clear();
//        update_message = null;
//    }
//}

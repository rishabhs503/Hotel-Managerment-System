//package com.stackroute.chatservice.ServiceTest.ServiceTestImpl;
//
//import com.stackroute.chatservice.Service.ChatServiceImpl;
//import com.stackroute.chatservice.exception.ChatNotFoundException;
//import com.stackroute.chatservice.model.Chat;
//import com.stackroute.chatservice.model.Message;
//import com.stackroute.chatservice.repository.ChatRepository;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//
//import static com.google.common.base.Verify.verify;
//import static org.graalvm.compiler.phases.common.DeadCodeEliminationPhase.Optionality.Optional;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//public class ServiceTest {
//
//
//    private List<Chat> chat1;
//
//    private List<Message> message;
//    Date date =new Date();
//    Message message1=new Message(1l,"Steven",date);
//
//
//    private Chat chat;
//
//
//
//    @InjectMocks
//    private ChatServiceImpl chatService;
//
//    @Mock
//    private ChatRepository chatRepository;
//
//    @BeforeEach
//    void setUp() {
//        message = new ArrayList<>();
//        chat = new Chat(44l, 55l, 66l, message);
//        message.add(message1);
//        chat1=new ArrayList<>();
//        chat1.add(chat);
//    }
//    @AfterEach
//    public void tearDown() {
//        chat = null;
//    }
//
//    @Test
//    void saveChatTest() throws ChatNotFoundException {
//        when(chatRepository.save(chat)).thenReturn(chat);
//        assertEquals(chat, chatService.saveChat(chat));
//        verify(chatRepository,times(1)).save(any());
//    }
//
//    @Test
//
//    void Updatechat() throws ChatNotFoundException {
//        lenient().when(chatRepository.findById(chat.getMessageId())).thenReturn(Optional.ofNullable(chat));
//        assertEquals(chat, chatService.updateChat(message1, 44l));
//        verify(chatRepository,times(1)).findById());
////        verify(chatRepository, times(1)).findById(any());
//    }
//
//    @Test
//    void getChatByMessageId() throws ChatNotFoundException {
//        when(chatRepository.findById(chat.getMessageId())).thenReturn(Optional.ofNullable(chat));
//        assertEquals(chat, chatService.getChatByMessageId(44l));
//        verify(chatRepository, times(1)).findById(any());
//    }
//    @Test
//    void deleteChatByMessageId() throws ChatNotFoundException {
//        when(chatRepository.findById(chat.getMessageId())).thenReturn(Optional.ofNullable(chat));
//        assertEquals("This Chat is Deleted successfully", chatService.deleteChatByMessageId(44l));
//        verify(chatRepository, times(1)).findById(any());
//    }
//    @Test
//    void getChatByCustomerId() throws ChatNotFoundException{
//        when(chatRepository.findByCustomerId()(chat.getCustomerId())).thenReturn(Hotelchatlist);
//        assertEquals(chatlist, chatService.getChatByCustomerId()(chat.getCustomerId()));
//        verify(chatRepository,times(2)).findByCustomerId(chat.getCustomerId());
//    }
//}
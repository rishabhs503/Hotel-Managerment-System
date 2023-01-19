package com.stackroute.chatservice.Service;

import com.stackroute.chatservice.model.Chat;
import java.util.List;


public interface ChatService {

	Chat saveChat(Chat chat);
	
	
	Chat updateChat(Chat chat, Long messageId);
	Chat getChatByMessageId(long messageId);
	void deleteChatByMessageId(long messageId);


	List<Chat> getChatByCustomerId(long CustomerId);

	Chat placeMessageInTheChat(long hotelId);
}

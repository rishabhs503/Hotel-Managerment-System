package com.stackroute.chatservice.model;

import java.util.List;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@ToString
@Document(collection = "chats")

public class Chat {

	@Id
	private Long messageId;
	private long customerId;
	private long hotelId;
	private List<Message> message;



}






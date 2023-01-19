package com.stackroute.chatservice.controller;

import java.util.List;

import com.stackroute.chatservice.Service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.stackroute.chatservice.model.Chat;

@RestController
@RequestMapping("/chats")
public class ChatController {
	   @Autowired
	     private ChatService chatService;

//	  to save the new chat
	    @PostMapping("/message")
	    public Chat saveChat(@RequestBody Chat chat){
	    	Chat xyz = chatService.saveChat(chat);
	    	System.out.println(xyz);
	    	return xyz;
	    }
	    
	    @PutMapping("/chat/{messageId}")
	    public ResponseEntity<Chat> updateChat(@PathVariable("messageId") Long messageId, @RequestBody Chat chat){
         return new ResponseEntity<Chat>(chatService.updateChat(chat, messageId), HttpStatus.OK);
	    }
	    
	    @GetMapping("/reply/{messageId}")
		   public ResponseEntity<Chat> getChatBymessageId(@PathVariable("messageId") long messageId){
		       return new ResponseEntity<Chat>(chatService.getChatByMessageId(messageId), HttpStatus.OK);
	   }
	    

	    @DeleteMapping("{messageId}")
	    public ResponseEntity<String> deleteChat(@PathVariable("messageId") long messageId){
	        chatService.deleteChatByMessageId(messageId);
	        return new ResponseEntity<String>("Chat deleted successfully.", HttpStatus.OK);
	    }


	    @GetMapping ("/chat/{customerId}")
	    public ResponseEntity<List<Chat>> getChatBycustomerId(@PathVariable("customerId") long CustomerId){
	        return new ResponseEntity<List<Chat>>(chatService.getChatByCustomerId(CustomerId), HttpStatus.OK);
	    }


	}



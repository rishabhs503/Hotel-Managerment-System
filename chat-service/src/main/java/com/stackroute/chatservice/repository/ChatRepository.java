package com.stackroute.chatservice.repository;

import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import com.stackroute.chatservice.model.Chat;

@Repository
public interface ChatRepository extends MongoRepository <Chat,Long>{

	List<Chat> findByCustomerId(long customerId);
}

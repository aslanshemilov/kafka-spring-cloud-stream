package com.kafka.data.helper;

import com.kafka.data.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;


public class KafkaProducer {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private KafkaTemplate<String, User> kafkaTemplate;

    public KafkaProducer(){
        try {
            logger.info("KafkaProducer::KafkaProducer(): {}", "Start");

        } catch (Exception ex){
            logger.error("KafkaProducer::KafkaProducer(): Exception: {}", ex.getMessage());
        }
    }

    public void messageSend(User user){
    	logger.info(String.format("#### -> Producing message -> %s", user));
    	ListenableFuture<SendResult<String, User>> result = kafkaTemplate.send("customer-topic-1", user);
    	
    	result.addCallback(new ListenableFutureCallback<SendResult<String, User>>() {

	        @Override
	        public void onSuccess(SendResult<String, User> result) {
	        	logger.info("ApiController::ProduceKafkaTemplate(): Sent message=[" +  
	              "] with offset=[" + result.getRecordMetadata().offset() + "]");
	        }
	        @Override
	        public void onFailure(Throwable ex) {
	        	logger.error("ApiController::ProduceKafkaTemplate(): Unable to send message=[" 
	               + "] due to : " + ex.getMessage());
	        }
	    });
    	//kafkaTemplate.send("customer-topic-1", user);
    }
} //end class

package com.kafka.data.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class KafkaServiceImpl {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    public KafkaServiceImpl(){
        super();
        try {
            logger.info("KafkaServiceImpl::KafkaServiceImpl(): {}", "Start");
        } catch (Exception ex){
            logger.error("KafkaServiceImpl::KafkaServiceImpl(): Exception: {}", ex.getMessage());
        }
    }


} //end class

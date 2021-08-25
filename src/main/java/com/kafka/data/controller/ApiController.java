package com.kafka.data.controller;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.kafka.data.helper.KafkaProducer;
import com.kafka.data.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.MessageChannel;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class ApiController {
    //private final Logger logger = LoggerFactory.getLogger(getClass());
    private Logger logger = LoggerFactory.getLogger(ApiController.class.getName());
    private ObjectMapper objMapper;
    private Gson gson;
    private KafkaProducer kp;

    @Value("${spring.application.name}")
    private String appname;


    public ApiController() {
        this.objMapper = new ObjectMapper();
        this.objMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        this.gson = new Gson();
        this.kp = new KafkaProducer();
    }

    @GetMapping("")
    public String Default() {
        return "This is API controller. " + appname;
    }

    @RequestMapping(path = {"/health", "/health.html"}, method = RequestMethod.GET, produces = {MediaType.TEXT_HTML_VALUE, "text/html"})
    public String healthyText() {
        return "health";
    }

    @RequestMapping(path = {"/start", "/starting"},
            method = {RequestMethod.GET},
            produces = {MediaType.APPLICATION_JSON_VALUE, "application/json; charset=utf-8"},
            consumes = {MediaType.ALL_VALUE})
    @ResponseBody
    public ResponseEntity<?> start() {
        Map<String, String> map = new HashMap<String, String>();
        logger.info("ApiController::start(): {}", "Start");

        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @RequestMapping(path = {"/produce"},
            method = {RequestMethod.POST},
            produces = {MediaType.APPLICATION_JSON_VALUE, "application/json; charset=utf-8"},
            consumes = {MediaType.ALL_VALUE})
    @ResponseBody
    public User produceEvent(@RequestBody User user){
        kp.messageSend(user);
        return user;
    }

} //end class

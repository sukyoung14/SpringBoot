package com.example.restapi.controller;

import com.example.restapi.dto.MessageReponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class HelloController {
    @GetMapping("/api/hello")
    public String hello(){
        return "Hello World";
    }
    @GetMapping("/api/message")
    public MessageReponse message(){
        return new MessageReponse("hello",200);
    }
    @GetMapping("/api/messages")
    public List<MessageReponse> messages(){
        return List.of(
                new MessageReponse("hello1",200),
                new MessageReponse("hello2",200),
                new MessageReponse("hello3",200)
        );
    }

}

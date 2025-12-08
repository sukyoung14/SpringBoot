package com.example.restapi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.awt.*;

@Getter
@AllArgsConstructor
public class MessageReponse {
    private String message;
    private int code;
}

package com.example.application.components.data;

import lombok.Getter;

import java.time.LocalDateTime;

public record Message(User userFrom, String content, LocalDateTime localDateTime) {}

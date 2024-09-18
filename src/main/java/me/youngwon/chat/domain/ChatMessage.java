package me.youngwon.chat.domain;

import java.util.Date;

public record ChatMessage(
        Long id,
        Long chatRoomId,
        Long userId,
        String message,
        Date createdAt,
        Date deletedAt
) {
    public ChatMessage(Long chatRoomId, Long userId, String message){
        this(null, chatRoomId, userId, message, new Date(), null);
    }
}

package me.youngwon.chat.domain;


import java.util.Date;

public record ChatRoomUserHistory(
        Long id,
        Long userId,
        ChatRoom chatRoom,
        Date createdAt
) {
    public ChatRoomUserHistory(Long userId, ChatRoom chatRoom) {
        this(null, userId, chatRoom, new Date());
    }
}

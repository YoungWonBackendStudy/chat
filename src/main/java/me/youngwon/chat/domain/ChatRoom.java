package me.youngwon.chat.domain;

import java.util.Date;

public record ChatRoom(
        Long id,
        String title,
        Long recentChatUsers,
        String recentChatMessage,
        Date createdAt,
        Date deletedAt
) {
    public ChatRoom(String title) {
        this(null, title, 1L, null, new Date(), null);
    }
}

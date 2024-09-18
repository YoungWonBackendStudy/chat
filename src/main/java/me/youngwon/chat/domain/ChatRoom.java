package me.youngwon.chat.domain;

import lombok.*;

import java.util.Date;

@Getter
@AllArgsConstructor
public class ChatRoom{
    Long id;
    String title;
    @Setter
    Long recentChatUsers;
    String recentChatMessage;
    Date createdAt;
    Date deletedAt;
    public ChatRoom(String title) {
        this.title = title;
        this.createdAt = new Date();
        this.recentChatUsers = 0L;
    }

    public void newMessageUpdated(String message) {
        this.recentChatMessage = message;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof ChatRoom chatRoom) {
            return this.id.equals(chatRoom.getId());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}

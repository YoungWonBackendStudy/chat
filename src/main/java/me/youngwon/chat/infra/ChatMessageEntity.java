package me.youngwon.chat.infra;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.youngwon.chat.domain.ChatMessage;

import java.util.Date;

@Entity
@Table(name = "chat_message")
@Getter
@NoArgsConstructor
public class ChatMessageEntity {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @Column(name = "chat_room_id")
    Long chatRoomId;

    @Column(name = "user_id")
    Long userId;

    @Column(name = "message")
    String message;

    @Column(name = "created_at")
    Date createdAt;

    @Column(name = "deleted_at")
    Date deletedAt;

    public ChatMessageEntity(ChatMessage chatMessage) {
        this.id = chatMessage.id();
        this.chatRoomId = chatMessage.chatRoomId();
        this.userId = chatMessage.userId();
        this.message = chatMessage.message();
        this.createdAt = chatMessage.createdAt();
        this.deletedAt = chatMessage.deletedAt();
    }

    public ChatMessage toDomain() {
        return new ChatMessage(id, chatRoomId, userId, message, createdAt, deletedAt);
    }
}

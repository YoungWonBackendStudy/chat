package me.youngwon.chat.infra;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
}

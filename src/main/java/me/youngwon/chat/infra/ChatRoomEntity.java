package me.youngwon.chat.infra;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.youngwon.chat.domain.ChatRoom;

import java.util.Date;

@Entity
@Table(name = "chat_room")
@Getter
@NoArgsConstructor
public class ChatRoomEntity {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @Column(name = "title")
    String title;

    @Column(name = "recent_chat_users")
    Long recentChatUsers;

    @Column(name = "recent_chat_message")
    String recentChatMessage;

    @Column(name = "created_at")
    Date createdAt;

    @Column(name = "deleted_at")
    Date deletedAt;

    public ChatRoomEntity(ChatRoom chatRoom) {
        this.id = chatRoom.getId();
        this.title = chatRoom.getTitle();
        this.recentChatUsers = chatRoom.getRecentChatUsers();
        this.recentChatMessage = chatRoom.getRecentChatMessage();
        this.createdAt = chatRoom.getCreatedAt();
        this.deletedAt = chatRoom.getDeletedAt();
    }
    public ChatRoom toDomain() {
        return new ChatRoom(this.id, this.title, this.recentChatUsers, this.recentChatMessage, this.createdAt, this.deletedAt);
    }
}

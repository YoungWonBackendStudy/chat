package me.youngwon.chat.infra;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.youngwon.chat.domain.ChatRoomUserHistory;

import java.util.Date;

@Entity
@Table(name = "chat_room_user_history")
@Getter
@NoArgsConstructor
public class ChatRoomUserHistoryEntity {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @JoinColumn(name = "chat_room_id")
    @ManyToOne(fetch = FetchType.EAGER)
    private ChatRoomEntity chatRoom;

    @Column(name = "created_at")
    private Date createdAt;

    public ChatRoomUserHistoryEntity(ChatRoomUserHistory chatRoomUserHistory) {
        this.id = chatRoomUserHistory.id();
        this.userId = chatRoomUserHistory.userId();
        this.chatRoom = new ChatRoomEntity(chatRoomUserHistory.chatRoom());
        this.createdAt = chatRoomUserHistory.createdAt();
    }

    public ChatRoomUserHistory toDomain() {
        return new ChatRoomUserHistory(this.id, this.userId, this.chatRoom.toDomain(), this.createdAt);
    }
}

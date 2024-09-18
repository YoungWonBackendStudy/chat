package me.youngwon.chat.infra;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface ChatRoomUserHistoryJpaRepository extends JpaRepository<ChatRoomUserHistoryEntity, Long> {
    List<ChatRoomUserHistoryEntity> findByCreatedAtAfter(Date createdAt);
}

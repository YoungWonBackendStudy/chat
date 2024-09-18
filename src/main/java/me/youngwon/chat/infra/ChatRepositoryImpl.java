package me.youngwon.chat.infra;

import lombok.RequiredArgsConstructor;
import me.youngwon.chat.domain.ChatMessage;
import me.youngwon.chat.domain.ChatRepository;
import me.youngwon.chat.domain.ChatRoom;
import me.youngwon.chat.domain.ChatRoomUserHistory;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
@Repository
@RequiredArgsConstructor
public class ChatRepositoryImpl implements ChatRepository {
    private final ChatRoomJpaRepository chatRoomJpaRepository;
    private final ChatMessageJpaRepository chatMessageJpaRepository;
    private final ChatRoomUserHistoryJpaRepository chatRoomUserHistoryJpaRepository;

    @Override
    public List<ChatRoom> getChatRoomsOrderByRecentUsersDesc() {
        return chatRoomJpaRepository.findAll(Sort.by(Sort.Direction.DESC, "recentChatUsers")).stream().map(ChatRoomEntity::toDomain).toList();
    }

    @Override
    public ChatRoom getChatRoom(Long chatRoomId) {
        return chatRoomJpaRepository.findById(chatRoomId).orElseThrow().toDomain();
    }

    @Override
    public ChatRoom saveChatRoom(ChatRoom chatRoom) {
        var entity = new ChatRoomEntity(chatRoom);
        entity = chatRoomJpaRepository.save(entity);
        return entity.toDomain();
    }

    @Override
    public void saveChatRooms(List<ChatRoom> chatRooms) {
        chatRoomJpaRepository.saveAll(chatRooms.stream().map(ChatRoomEntity::new).toList());
    }

    @Override
    public List<ChatMessage> getChatMessages(ChatRoom chatRoom) {
        return chatMessageJpaRepository.findAll().stream().map(ChatMessageEntity::toDomain).toList();
    }

    @Override
    public void saveChatRoomUserHistory(ChatRoomUserHistory chatRoomUserHistory) {
        var entity = new ChatRoomUserHistoryEntity(chatRoomUserHistory);
        this.chatRoomUserHistoryJpaRepository.save(entity);
    }

    @Override
    public void saveNewChatMessage(ChatMessage chatMessage) {
        var entity = new ChatMessageEntity(chatMessage);
        chatMessageJpaRepository.save(entity);
    }

    @Override
    public List<ChatRoomUserHistory> getChatHistoriesIn30mins() {
        Date dateBefore30mins = new Date(System.currentTimeMillis() - 30 * 60 * 1000);
        return chatRoomUserHistoryJpaRepository.findByCreatedAtAfter(dateBefore30mins).stream().map(ChatRoomUserHistoryEntity::toDomain).toList();
    }
}

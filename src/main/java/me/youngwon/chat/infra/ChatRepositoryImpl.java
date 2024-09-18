package me.youngwon.chat.infra;

import me.youngwon.chat.domain.ChatMessage;
import me.youngwon.chat.domain.ChatRepository;
import me.youngwon.chat.domain.ChatRoom;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public class ChatRepositoryImpl implements ChatRepository {
    @Override
    public List<ChatRoom> getChatRooms() {
        return List.of();
    }

    @Override
    public ChatRoom getChatRoom(Long chatRoomId) {
        return null;
    }

    @Override
    public void makeNewChatRoom(ChatRoom chatRoom) {
    }

    @Override
    public List<ChatMessage> getChatMessages(ChatRoom chatRoom) {
        return List.of();
    }

    @Override
    public void saveNewChatMessage(ChatMessage chatMessage) {
    }
}

package me.youngwon.chat.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatService {
    private final ChatRepository chatRepository;

    public List<ChatRoom> getChatRooms() {
        return chatRepository.getChatRooms();
    }

    public void makeChatRoom(Long userId, String chatRoomName) {
        chatRepository.makeNewChatRoom(new ChatRoom(chatRoomName));
    }

    public List<ChatMessage> getChatsMessages(Long userId, Long chatRoomId) {
        ChatRoom chatRoom = chatRepository.getChatRoom(chatRoomId);
        return chatRepository.getChatMessages(chatRoom);
    }

    public void sendChatMessage(Long userId, Long chatRoomId, String message) {
        chatRepository.saveNewChatMessage(new ChatMessage(chatRoomId, userId, message));
    }
}

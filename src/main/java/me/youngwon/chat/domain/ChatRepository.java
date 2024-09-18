package me.youngwon.chat.domain;

import java.util.List;

public interface ChatRepository {
    List<ChatRoom> getChatRooms();
    ChatRoom getChatRoom(Long chatRoomId);
    void makeNewChatRoom(ChatRoom chatRoom);
    List<ChatMessage> getChatMessages(ChatRoom chatRoom);
    void saveNewChatMessage(ChatMessage chatMessage);
}

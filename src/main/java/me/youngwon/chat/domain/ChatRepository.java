package me.youngwon.chat.domain;

import java.util.List;

public interface ChatRepository {
    List<ChatRoom> getChatRoomsOrderByRecentUsersDesc();
    ChatRoom getChatRoom(Long chatRoomId);
    ChatRoom saveChatRoom(ChatRoom chatRoom);
    void saveChatRooms(List<ChatRoom> chatRooms);
    List<ChatMessage> getChatMessages(ChatRoom chatRoom);

    void saveChatRoomUserHistory(ChatRoomUserHistory chatRoomUserHistory);

    void saveNewChatMessage(ChatMessage chatMessage);

    List<ChatRoomUserHistory> getChatHistoriesIn30mins();
}

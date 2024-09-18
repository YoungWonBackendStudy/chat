package me.youngwon.chat.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
public class ChatService {
    private final ChatRepository chatRepository;

    public List<ChatRoom> getChatRooms() {
        return chatRepository.getChatRoomsOrderByRecentUsersDesc();
    }

    public ChatRoom makeChatRoom(Long userId, String chatRoomName) {
        return chatRepository.saveChatRoom(new ChatRoom(chatRoomName));
    }

    public List<ChatMessage> getChatsMessages(Long userId, Long chatRoomId) {
        ChatRoom chatRoom = chatRepository.getChatRoom(chatRoomId);
        var chatHistory = new ChatRoomUserHistory(userId, chatRoom);
        chatRepository.saveChatRoomUserHistory(chatHistory);
        return chatRepository.getChatMessages(chatRoom);
    }

    @Transactional
    public void sendChatMessage(Long userId, Long chatRoomId, String message) {
        ChatRoom chatRoom = chatRepository.getChatRoom(chatRoomId);
        chatRepository.saveNewChatMessage(new ChatMessage(chatRoomId, userId, message));
        chatRoom.newMessageUpdated(message);
        chatRepository.saveChatRoom(chatRoom);
    }

    public void schedulerChatRoomActiveUser() {
        List<ChatRoomUserHistory> chatRoomUserHistories = chatRepository.getChatHistoriesIn30mins();
        Map<ChatRoom, Set<Long>> chatRoomActiveUsers = new HashMap<>();
        for (ChatRoomUserHistory chatRoomUserHistory : chatRoomUserHistories) {
            if(!chatRoomActiveUsers.containsKey(chatRoomUserHistory.chatRoom()))
                chatRoomActiveUsers.put(chatRoomUserHistory.chatRoom(), new HashSet<>());

            chatRoomActiveUsers.get(chatRoomUserHistory.chatRoom()).add(chatRoomUserHistory.userId());
        }

        List<ChatRoom> chatRooms = new ArrayList<>(chatRoomActiveUsers.keySet());
        chatRooms.forEach(chatRoom -> {
            var activeUserCnt = chatRoomActiveUsers.get(chatRoom).size();
            chatRoom.setRecentChatUsers((long) activeUserCnt);
        });
        chatRepository.saveChatRooms(chatRooms);
    }
}

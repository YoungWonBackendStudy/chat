package me.youngwon.chat.interfaces.presentation;

import me.youngwon.chat.domain.ChatRoom;

public record ChatRoomDto(
        long id,
        String title,
        Long recentUserUsage,
        String recentChatMessage
){
    public static ChatRoomDto of(ChatRoom chatRoom) {
        return new ChatRoomDto(chatRoom.getId(), chatRoom.getTitle(), chatRoom.getRecentChatUsers(), chatRoom.getRecentChatMessage());
    }
}

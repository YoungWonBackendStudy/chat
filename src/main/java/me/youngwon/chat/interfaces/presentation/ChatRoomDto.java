package me.youngwon.chat.interfaces.presentation;

import me.youngwon.chat.domain.ChatRoom;

public record ChatRoomDto(
        long id,
        String title,
        Long recentUserUsage,
        Long recentChatMessage
){
    public static ChatRoomDto of(ChatRoom chatRoom) {
        return new ChatRoomDto(chatRoom.id(), chatRoom.title(), chatRoom.recentChatUsers(), chatRoom.recentChatUsers());
    }
}

package me.youngwon.chat.interfaces.presentation;

import me.youngwon.chat.domain.ChatMessage;

public record ChatMessageDto(
        Long id,
        Long userId,
        String message
) {
    public static ChatMessageDto of(ChatMessage chatMessage) {
        return new ChatMessageDto(chatMessage.id(), chatMessage.userId(), chatMessage.message());
    }
}

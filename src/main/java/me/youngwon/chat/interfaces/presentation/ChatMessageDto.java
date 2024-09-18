package me.youngwon.chat.interfaces.presentation;

public record ChatMessageDto(
        Long id,
        Long userId,
        String message
) {
}

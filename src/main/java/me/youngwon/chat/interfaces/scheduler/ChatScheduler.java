package me.youngwon.chat.interfaces.scheduler;

import lombok.RequiredArgsConstructor;
import me.youngwon.chat.domain.ChatService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ChatScheduler {
    private final ChatService chatService;

    @Scheduled(fixedDelay = 5000L)
    public void scheduleChatRoomActiveUserCnt() {
        chatService.schedulerChatRoomActiveUser();
    }
}

package me.youngwon.chat.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class ChatServiceIntegTest {
    @Autowired
    private ChatService chatService;
    @Autowired
    private ChatRepository chatRepository;

    @Test
    @DisplayName("채팅방 생성 후 조회")
    void testChatRoom() {
        long userId = 0;

        //when
        var chatRooms = chatService.getChatRooms();
        var newChatRoom = chatService.makeChatRoom(userId, "채팅방1");
        var chatRoomsAfterMake = chatService.getChatRooms();

        //then
        assertThat(chatRoomsAfterMake.size()).isGreaterThan(chatRooms.size());
        assertThat(chatRoomsAfterMake).contains(newChatRoom);
    }

    @Test
    @DisplayName("채팅 보낸 후 조회")
    void testChatMessage() {
        //given
        long userId = 0;
        var chatRoom = chatService.makeChatRoom(userId, "채팅방1");

        //when
        var chatMessages = chatService.getChatsMessages(userId, chatRoom.getId());
        chatService.sendChatMessage(userId, chatRoom.getId(), "테스트 채팅");
        var chatMessagesAfterChat = chatService.getChatsMessages(userId, chatRoom.getId());
        chatService.schedulerChatRoomActiveUser();

        //then
        assertThat(chatMessagesAfterChat.size()).isGreaterThan(chatMessages.size());

        var chatRoomAfterChat = chatRepository.getChatRoom(chatRoom.getId());
        assertThat(chatRoomAfterChat.getRecentChatUsers()).isGreaterThan(chatRoom.getRecentChatUsers());
        assertThat(chatRoomAfterChat.getRecentChatMessage()).isNotEmpty();
        assertThat(chatRoomAfterChat.getRecentChatMessage()).isNotEqualTo(chatRoom.getRecentChatMessage());
    }
}

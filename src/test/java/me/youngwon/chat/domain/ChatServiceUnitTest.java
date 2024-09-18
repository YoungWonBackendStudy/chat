package me.youngwon.chat.domain;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class ChatServiceUnitTest {
    ChatService chatService;
    ChatRepository mockChatRepository;

    @BeforeEach
    void setUp() {
        mockChatRepository = mock(ChatRepository.class);
        chatService = new ChatService(mockChatRepository);
    }

    @Test
    @DisplayName("채팅방 조회 성공")
    void testGetChatRooms() {
        //given
        when(mockChatRepository.getChatRooms()).thenReturn(List.of(
                new ChatRoom("채팅방1")
        ));

        //when
        var chatRooms = chatService.getChatRooms();

        //then
        assertThat(chatRooms).isNotEmpty();
    }

    @Test
    @DisplayName("채팅방 생성 성공")
    void testMakeChatRoom() {
        //given
        Long chatUserId = 0L;

        //when
        chatService.makeChatRoom(chatUserId, "채팅방1");

        //then
        verify(mockChatRepository).makeNewChatRoom(argThat(chatRoom -> {
            assertThat(chatRoom.title()).isEqualTo("채팅방1");
            return true;
        }));
    }

    @Test
    @DisplayName("채팅 메시지 조회 성공")
    void testGetChatMessage() {
        //given
        ChatRoom chatRoom = new ChatRoom(0L, "채팅방 1", 1L, "Test", new Date(), null);
        Long chatUserId = 0L;
        when(mockChatRepository.getChatMessages(chatRoom)).thenReturn(List.of(
                new ChatMessage(0L, chatRoom.id(), chatUserId, "Test", new Date(), null)
        ));

        //when
        var chatsMessages = chatService.getChatsMessages(chatUserId, chatRoom.id());

        //then
        assertThat(chatsMessages).isNotEmpty();
    }

    @Test
    @DisplayName("채팅 보내기 성공")
    void testSendChatMessage() {
        //given
        Long chatRoomId = 0L;
        Long chatUserId = 0L;

        //when
        chatService.sendChatMessage(chatUserId, chatRoomId, "Test");

        //then
        verify(mockChatRepository).saveNewChatMessage(argThat(newChatMessage -> {
            assertThat(newChatMessage.chatRoomId()).isEqualTo(chatRoomId);
            assertThat(newChatMessage.userId()).isEqualTo(chatUserId);
            assertThat(newChatMessage.message()).isEqualTo("Test");
            return true;
        }));
    }
}

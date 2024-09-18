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
        when(mockChatRepository.getChatRoomsOrderByRecentUsersDesc()).thenReturn(List.of(
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
        verify(mockChatRepository).saveChatRoom(argThat(chatRoom -> {
            assertThat(chatRoom.getTitle()).isEqualTo("채팅방1");
            return true;
        }));
    }

    @Test
    @DisplayName("채팅 메시지 조회 성공")
    void testGetChatMessage() {
        //given
        ChatRoom chatRoom = new ChatRoom(0L, "채팅방 1", 1L, "Test", new Date(), null);
        Long chatUserId = 0L;
        when(mockChatRepository.getChatRoom(chatRoom.getId())).thenReturn(chatRoom);
        when(mockChatRepository.getChatMessages(chatRoom)).thenReturn(List.of(
                new ChatMessage(0L, chatRoom.getId(), chatUserId, "Test", new Date(), null)
        ));

        //when
        var chatsMessages = chatService.getChatsMessages(chatUserId, chatRoom.getId());

        //then
        assertThat(chatsMessages).isNotEmpty();
        verify(mockChatRepository).saveChatRoomUserHistory(argThat(chatRoomUserHistory -> {
            assertThat(chatRoomUserHistory.userId()).isEqualTo(chatUserId);
            assertThat(chatRoomUserHistory.chatRoom()).isEqualTo(chatRoom);
            return true;
        }));
    }

    @Test
    @DisplayName("채팅 보내기 성공")
    void testSendChatMessage() {
        //given
        Long chatRoomId = 0L;
        Long chatUserId = 0L;
        when(mockChatRepository.getChatRoom(chatRoomId)).thenReturn(new ChatRoom(chatRoomId, "채팅방 1", 0L, "Test", new Date(), null));

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

    @Test
    @DisplayName("채팅방 접속자 수 스케줄링")
    void testSchedule() {
        //given
        when(mockChatRepository.getChatHistoriesIn30mins()).thenReturn(List.of(
           new ChatRoomUserHistory(0L, new ChatRoom(0L, "", 0L, null, new Date(), null)),
            new ChatRoomUserHistory(1L, new ChatRoom(0L, "", 0L, null, new Date(), null)),
            new ChatRoomUserHistory(2L, new ChatRoom(0L, "", 0L, null, new Date(), null)),
            new ChatRoomUserHistory(3L, new ChatRoom(0L, "", 0L, null, new Date(), null)),
                new ChatRoomUserHistory(3L, new ChatRoom(0L, "", 0L, null, new Date(), null))
        ));

        //when
        chatService.schedulerChatRoomActiveUser();

        //then
        verify(mockChatRepository).saveChatRooms(argThat(chatRooms -> {
            assertThat(chatRooms).isNotEmpty();
            assertThat(chatRooms.size()).isEqualTo(1);
            assertThat(chatRooms.get(0).getRecentChatUsers()).isEqualTo(4);
            return true;
        }));
    }
}

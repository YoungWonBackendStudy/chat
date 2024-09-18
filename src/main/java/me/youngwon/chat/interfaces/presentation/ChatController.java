package me.youngwon.chat.interfaces.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import me.youngwon.chat.domain.ChatService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/chat")
@Tag(name = "채팅 API")
@RequiredArgsConstructor
public class ChatController {
    private final ChatService chatService;

    @Operation(summary = "채팅방 목록 조회")
    @GetMapping("rooms")
    List<ChatRoomDto> chatRooms() {
        return chatService.getChatRooms().stream().map(ChatRoomDto::of).toList();
    }

    @Operation(summary = "채팅방 생성")
    @Parameters({
            @Parameter(in = ParameterIn.QUERY, name = "userId", required = true, description = "사용자 ID")
    })
    @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true, description = "채팅방 제목")
    @PostMapping("rooms")
    ChatRoomDto chatRoom(
            @RequestParam(name = "userId") Long userId,
            @RequestBody String title
    ) {
        return ChatRoomDto.of(chatService.makeChatRoom(userId, title));
    }

    @Operation(summary = "채팅 내용 조회")
    @Parameters({
            @Parameter(in = ParameterIn.QUERY, name = "userId", required = true, description = "사용자 ID"),
            @Parameter(in = ParameterIn.PATH,name ="chatRoomId", required = true, description = "채팅방 ID")
    })
    @GetMapping("rooms/{chatRoomId}/messages")
    List<ChatMessageDto> chatMessages(
            @RequestParam(name = "userId") Long userId,
            @PathVariable(name = "chatRoomId") Long chatRoomId
    ) {
        return chatService.getChatsMessages(userId, chatRoomId).stream().map(ChatMessageDto::of).toList();
    }

    @Operation(summary = "채팅 보내기")
    @Parameters({
            @Parameter(in = ParameterIn.QUERY, name = "userId", required = true, description = "사용자 ID"),
            @Parameter(in = ParameterIn.PATH,name ="chatRoomId", required = true, description = "채팅방 ID")
    })
    @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true,description = "채팅 메시지 내용")
    @PostMapping("rooms/{chatRoomId}/messages")
    void chatMessage(
            @RequestParam(name = "userId") Long userId,
            @PathVariable(name = "chatRoomId") Long chatRoomId,
            @RequestBody String message
    ) {
        chatService.sendChatMessage(userId, chatRoomId, message);
    }
}

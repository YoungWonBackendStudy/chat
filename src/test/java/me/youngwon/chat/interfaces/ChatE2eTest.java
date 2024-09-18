package me.youngwon.chat.interfaces;

import io.restassured.RestAssured;
import me.youngwon.chat.interfaces.controlleradvice.ErrorResponse;
import me.youngwon.chat.interfaces.presentation.ChatMessageDto;
import me.youngwon.chat.interfaces.presentation.ChatRoomDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ChatE2eTest {
    @LocalServerPort
    int port;

    @Test
    @DisplayName("전체 시나리오(채팅방 생성 -> 채팅방 조회 -> 채팅 조회 -> 채팅 보내기 -> 채팅 조회)")
    void testAllE2e() {
        //given
        long userId = 0;

        // 채팅방 생성
        var newChatRoom = RestAssured
                .given()
                    .contentType("application/json")
                    .port(port)
                    .queryParam("userId", userId)
                    .body("\"채팅방1\"")
                .when()
                    .post("/chat/rooms")
                .then()
                    .assertThat()
                    .statusCode(200)
                .extract().as(ChatRoomDto.class);

        // 채팅방 조회
        var chatRooms = RestAssured
                .given()
                    .port(port)
                .when()
                    .get("/chat/rooms")
                .then()
                    .assertThat()
                    .statusCode(200)
                .extract().as(ChatRoomDto[].class);

        assertThat(chatRooms.length).isGreaterThan(0);

        // 채팅 조회
        var chatMessages = RestAssured
                .given()
                    .queryParam("userId", userId)
                    .pathParam("chatRoomId", newChatRoom.id())
                    .port(port)
                .when()
                    .get("/chat/rooms/{chatRoomId}/messages")
                .then()
                    .assertThat()
                    .statusCode(200)
                    .extract().as(ChatMessageDto[].class);

        // 채팅 보내기
        RestAssured
                .given()
                    .contentType("application/json")
                    .queryParam("userId", userId)
                    .pathParam("chatRoomId", newChatRoom.id())
                    .body("\"테스트 메시지\"")
                    .port(port)
                .when()
                    .post("/chat/rooms/{chatRoomId}/messages")
                .then()
                    .assertThat()
                    .statusCode(200);

        // 채팅 다시 조회
        var chatMessagesAfter = RestAssured
                .given()
                    .queryParam("userId", userId)
                    .pathParam("chatRoomId", newChatRoom.id())
                    .port(port)
                .when()
                    .get("/chat/rooms/{chatRoomId}/messages")
                .then()
                    .assertThat()
                    .statusCode(200)
                    .extract().as(ChatMessageDto[].class);

        assertThat(chatMessagesAfter.length).isGreaterThan(chatMessages.length);
    }

    @Test
    @DisplayName("오류 발생 시 만들어 둔 Error Response가 오는 지 확인")
    void testError() {
        //when
        ErrorResponse errorResponse = RestAssured
                .given()
                    .queryParam("userId", 0L)
                    .pathParam("chatRoomId", 999999999999999999L)
                    .port(port)
                .when()
                    .get("/chat/rooms/{chatRoomId}/messages")
                .then()
                    .assertThat()
                    .statusCode(500)
                    .extract().as(ErrorResponse.class);
        //then
        assertThat(errorResponse.message()).isNotEmpty();
    }
}

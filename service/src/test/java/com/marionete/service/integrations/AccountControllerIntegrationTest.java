package com.marionete.service.integrations;

import com.marionete.backends.AccountInfoMock;
import com.marionete.backends.UserInfoMock;
import com.marionete.service.AccountServiceApplication;
import com.marionete.service.MockServer;
import com.marionete.service.client.UserServiceClient;
import com.marionete.service.dto.useraccount.UserAccountInfoRequestDto;
import com.marionete.service.exception.LoginException;
import com.marionete.service.grpc.LoginServiceClient;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = AccountServiceApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AccountControllerIntegrationTest extends MockServer {

    private final String DOMAIN = "http://localhost:";
    private final String CONTEXT_PATH = "/marionete/useraccount/";

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @MockBean
    LoginServiceClient loginServiceClient;

    @MockBean
    UserServiceClient userServiceClient;

    @Test
    void test_valid_payload() {
        when(loginServiceClient.login(anyString(), anyString())).thenReturn("134234234234");
        UserAccountInfoRequestDto requestDto = generateRequestDto("lokesh", "A123*@nasdaskds");
        ResponseEntity<String> responseEntity = this.restTemplate
                .postForEntity(DOMAIN + port + CONTEXT_PATH, requestDto, String.class);
        assertEquals(200, responseEntity.getStatusCodeValue());
    }

    @Test
    void test_invalid_password() {
        UserAccountInfoRequestDto requestDto = generateRequestDto("lokesh", "nasdaskds");
        ResponseEntity<String> responseEntity = this.restTemplate
                .postForEntity(DOMAIN + port + CONTEXT_PATH, requestDto, String.class);
        assertEquals(400, responseEntity.getStatusCodeValue());
    }

    @Test
    void test_invalid_payload() {
        UserAccountInfoRequestDto requestDto = generateRequestDto("", "");
        ResponseEntity<String> responseEntity = this.restTemplate
                .postForEntity(DOMAIN + port + CONTEXT_PATH, requestDto, String.class);
        assertEquals(400, responseEntity.getStatusCodeValue());
    }

    @Test
    void test_grpc_failure() {
        when(loginServiceClient.login(anyString(), anyString())).thenThrow(new LoginException());
        UserAccountInfoRequestDto requestDto = generateRequestDto("lokesh", "A123*@nasdaskds");
        ResponseEntity<String> responseEntity = this.restTemplate
                .postForEntity(DOMAIN + port + CONTEXT_PATH, requestDto, String.class);
        assertEquals(500, responseEntity.getStatusCodeValue());
    }

    @Test
    void test_user_service_failure() {
        when(loginServiceClient.login(anyString(), anyString())).thenReturn("134234234234");
        when(userServiceClient.fetchUserDetails(anyString())).thenThrow(new RestClientException("invalid domain"));
        UserAccountInfoRequestDto requestDto = generateRequestDto("lokesh", "A123*@nasdaskds");
        ResponseEntity<String> responseEntity = this.restTemplate
                .postForEntity(DOMAIN + port + CONTEXT_PATH, requestDto, String.class);
        assertEquals(500, responseEntity.getStatusCodeValue());
    }

    private UserAccountInfoRequestDto generateRequestDto(String username, String password) {
        return UserAccountInfoRequestDto.builder()
                .username(username)
                .password(password)
                .build();
    }
}

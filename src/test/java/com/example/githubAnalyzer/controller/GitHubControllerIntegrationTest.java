package com.example.githubAnalyzer.controller;

import com.example.githubAnalyzer.dto.RepositoryDTO;
import com.example.githubAnalyzer.exception.GlobalExceptionHandler;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GitHubControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void shouldReturnNonForkedRepositoriesWithBranchesForGivenUser() {
        // given
        String username = "NextStepProDev";

        // when
        ResponseEntity<RepositoryDTO[]> response = restTemplate.getForEntity(
                "http://localhost:" + port + "/api/repositories/" + username,
                RepositoryDTO[].class
        );
        // then

        RepositoryDTO[] repositories = response.getBody();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(repositories).isNotNull();
        assertThat(repositories.length).isGreaterThan(0);
        assertThat(repositories[0].name()).isNotNull();
        assertThat(repositories[0].ownerLogin()).isEqualTo(username);
        assertThat(repositories[0].branches()).isNotNull();
        assertThat(repositories[0].branches().get(0).name()).isNotNull();
        assertThat(repositories[0].branches().get(0).lastCommitSha()).isNotNull();
    }

    @Test
    void shouldReturn404WhenUserNotFound() {
        // given
        String invalidUsername = "DefinitelyNotExistingUser123456";

        // when
        ResponseEntity<GlobalExceptionHandler.ErrorResponse> response = restTemplate.getForEntity(
                "http://localhost:" + port + "/api/repositories/" + invalidUsername,
                GlobalExceptionHandler.ErrorResponse.class
        );

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().status()).isEqualTo(404);
        assertThat(response.getBody().message()).isEqualTo("User not found");
    }
}
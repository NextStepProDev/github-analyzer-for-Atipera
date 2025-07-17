package com.example.githubAnalyzer.controller;

import com.example.githubAnalyzer.dto.RepositoryDTO;
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
    void shouldReturnMyPublicNonForkRepositoriesWithBranches() {
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
        assertThat(repositories[0].getRepositoryName()).isNotNull();
        assertThat(repositories[0].getOwnerLogin()).isEqualTo(username);
        assertThat(repositories[0].getBranches()).isNotNull();
    }

    @Test
    void shouldReturn404WhenUserNotFound() {
        // given
        String invalidUsername = "DefinitelyNotExistingUser123456";

        // when
        ResponseEntity<String> response = restTemplate.getForEntity(
                "http://localhost:" + port + "/api/repositories/" + invalidUsername,
                String.class
        );

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).contains("\"status\":404");
        assertThat(response.getBody()).contains("\"message\":\"User not found\"");
    }
}
package com.example.githubAnalyzer.client;

import com.example.githubAnalyzer.client.dto.GitHubBranchResponse;
import com.example.githubAnalyzer.client.dto.GitHubRepositoryResponse;
import com.example.githubAnalyzer.exception.GitHubUserNotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Objects;

@Component
public class GitHubClient {

    private final RestClient restClient;

    @Value("${spring.application.name}")
    private String applicationName;

    public GitHubClient(
            RestClient.Builder restClientBuilder,
            @Value("${github.api.url}") String gitHubApiUrl
    ) {
        this.restClient = restClientBuilder.baseUrl(gitHubApiUrl).build();
    }

    public List<GitHubRepositoryResponse> fetchRepositories(String username) {
        String uri = UriComponentsBuilder
                .fromPath("/users/{username}/repos")
                .queryParam("per_page", 100)
                .build(username)
                .toString();
        try {
            GitHubRepositoryResponse[] response = restClient.get()
                    .uri(uri)
                    .header("User-Agent", applicationName)
                    .header("X-GitHub-Api-Version", "2022-11-28")
                    .header("Accept", "application/vnd.github+json")
                    .retrieve()
                    .body(GitHubRepositoryResponse[].class);

            return List.of(Objects.requireNonNull(response));

        } catch (HttpClientErrorException.NotFound e) {
            throw new GitHubUserNotFoundException(username); // my own exception 404
        }
    }

    public List<GitHubBranchResponse> fetchBranches(String username, String repoName) {
        String uri = UriComponentsBuilder
                .fromPath("/repos/{username}/{repoName}/branches")
                .build(username, repoName)
                .toString();

        return List.of(
                Objects.requireNonNull(restClient.get()
                        .uri(uri)
                        .header("User-Agent", applicationName)
                        .header("X-GitHub-Api-Version", "2022-11-28")
                        .header("Accept", "application/vnd.github+json")
                        .retrieve()
                        .onStatus(status -> status.value() == 404, (req, res) -> {
                            throw new GitHubUserNotFoundException("User or repo not found: %s/%s".formatted(username, repoName));
                        })
                        .body(GitHubBranchResponse[].class))
        );
    }
}

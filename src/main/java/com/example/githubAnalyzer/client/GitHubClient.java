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
    private final String githubApiUrl;

    public GitHubClient(
            RestClient.Builder restClientBuilder,
            @Value("${github.api.url}") String githubApiUrl
    ) {
        this.restClient = restClientBuilder.baseUrl(githubApiUrl).build();
        this.githubApiUrl = githubApiUrl;
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

        return List.of(Objects.requireNonNull(restClient.get()
                .uri(uri)
                .retrieve()
                .body(GitHubBranchResponse[].class)));
    }
}

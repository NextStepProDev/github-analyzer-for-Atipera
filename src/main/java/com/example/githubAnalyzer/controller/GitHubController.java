package com.example.githubAnalyzer.controller;

import com.example.githubAnalyzer.dto.RepositoryDTO;
import com.example.githubAnalyzer.service.GitHubService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "GitHub Repositories")
@RestController
@RequestMapping("/api/repositories")
public class GitHubController {

    private final GitHubService gitHubService;

    public GitHubController(GitHubService gitHubService) {
        this.gitHubService = gitHubService;
    }

    @GetMapping("/{username}")
    @Operation(
            summary = "Get non-forked repositories for GitHub user",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "List of repositories",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(
                                            name = "successful-response",
                                            summary = "Example 200 OK response",
                                            value = """
                                                    [
                                                      {
                                                        "name": "github-analyzer",
                                                        "ownerLogin": "nextstepprodev",
                                                        "branches": [
                                                          {
                                                            "name": "main",
                                                            "lastCommitSha": "abc123def456"
                                                          },
                                                          {
                                                            "name": "develop",
                                                            "lastCommitSha": "789xyz987"
                                                          }
                                                        ]
                                                      }
                                                    ]
                                                    """
                                    )
                            )
                    )
            }
    )
    public ResponseEntity<List<RepositoryDTO>> getUserRepositories(@PathVariable String username) {
        List<RepositoryDTO> repositories = gitHubService.getNonForkedRepositoriesWithBranches(username);
        return ResponseEntity.ok(repositories);
    }
}
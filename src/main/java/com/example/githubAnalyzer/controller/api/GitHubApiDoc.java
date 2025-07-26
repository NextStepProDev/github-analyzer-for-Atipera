package com.example.githubAnalyzer.controller.api;

import com.example.githubAnalyzer.dto.RepositoryDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface GitHubApiDoc {

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
    @SuppressWarnings("unused")
    ResponseEntity<List<RepositoryDTO>> getUserRepositories(@PathVariable String username);
}
package com.example.githubAnalyzer.controller;

import com.example.githubAnalyzer.controller.api.GitHubApiDoc;
import com.example.githubAnalyzer.dto.RepositoryDTO;
import com.example.githubAnalyzer.service.GitHubService;
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
@SuppressWarnings("unused")
public class GitHubController implements GitHubApiDoc {

    private final GitHubService gitHubService;

    @SuppressWarnings("unused")
    public GitHubController(GitHubService gitHubService) {
        this.gitHubService = gitHubService;
    }

    @GetMapping("/{username}")
    @SuppressWarnings("unused")
    @Override
    public ResponseEntity<List<RepositoryDTO>> getUserRepositories(@PathVariable String username) {
        List<RepositoryDTO> repositories = gitHubService.getNonForkedRepositoriesWithBranches(username);
        return ResponseEntity.ok(repositories);
    }
}
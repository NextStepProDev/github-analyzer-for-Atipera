package com.example.githubAnalyzer.controller;

import com.example.githubAnalyzer.dto.RepositoryDTO;
import com.example.githubAnalyzer.service.GitHubService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/repositories")
@RequiredArgsConstructor
public class GitHubController {

    private final GitHubService gitHubService;

    @GetMapping("/{username}")
    public ResponseEntity<List<RepositoryDTO>> getUserRepositories(@PathVariable String username) {
        List<RepositoryDTO> repositories = gitHubService.getNonForkedRepositoriesWithBranches(username);
        return ResponseEntity.ok(repositories);
    }
}
package com.example.githubAnalyzer.client;

import com.example.githubAnalyzer.client.dto.GitHubBranchResponse;
import com.example.githubAnalyzer.client.dto.GitHubRepositoryResponse;

import java.util.List;

public interface GitHubClient {

    List<GitHubRepositoryResponse> fetchRepositories(String username);

    List<GitHubBranchResponse> fetchBranches(String username, String repoName);
}

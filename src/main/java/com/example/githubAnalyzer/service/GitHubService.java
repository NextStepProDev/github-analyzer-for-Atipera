package com.example.githubAnalyzer.service;

import com.example.githubAnalyzer.client.GitHubClient;
import com.example.githubAnalyzer.client.dto.GitHubBranchResponse;
import com.example.githubAnalyzer.client.dto.GitHubRepositoryResponse;
import com.example.githubAnalyzer.dto.BranchDTO;
import com.example.githubAnalyzer.dto.RepositoryDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GitHubService {

    private final GitHubClient gitHubClient;

    public List<RepositoryDTO> getNonForkedRepositoriesWithBranches(String username) {
        List<GitHubRepositoryResponse> repositories = gitHubClient.fetchRepositories(username);

        return repositories.stream()
                .filter(repo -> !repo.isFork())
                .map(repo -> {
                    List<GitHubBranchResponse> branches = gitHubClient.fetchBranches(username, repo.getName());
                    List<BranchDTO> branchDTOs = branches.stream()
                            .map(branch -> new BranchDTO(branch.getName(), branch.getCommit().getSha()))
                            .collect(Collectors.toList());
                    return new RepositoryDTO(repo.getName(), repo.getOwner().getLogin(), branchDTOs);
                })
                .collect(Collectors.toList());
    }
}
package com.example.githubAnalyzer.service;

import com.example.githubAnalyzer.client.GitHubClient;
import com.example.githubAnalyzer.client.dto.GitHubBranchResponse;
import com.example.githubAnalyzer.client.dto.GitHubRepositoryResponse;
import com.example.githubAnalyzer.dto.BranchDTO;
import com.example.githubAnalyzer.dto.RepositoryDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GitHubService {

    private final GitHubClient gitHubClient;

    public GitHubService(GitHubClient gitHubClient) {
        this.gitHubClient = gitHubClient;
    }

    /**
     * Retrieves a list of non-forked repositories for a given GitHub user, along with their branches.
     *
     * @param username the GitHub username
     * @return a list of RepositoryDTO containing repository names, owners, and their branches
     */
    public List<RepositoryDTO> getNonForkedRepositoriesWithBranches(String username) {
        List<GitHubRepositoryResponse> repositories = gitHubClient.fetchRepositories(username);

        return repositories.stream()
                .filter(repo -> !repo.fork())
                .map(repo -> {
                    List<GitHubBranchResponse> branches = gitHubClient.fetchBranches(username, repo.name());
                    List<BranchDTO> branchDTOs = branches.stream()
                            .map(branch -> Optional.ofNullable(branch.commit())
                                    .map(GitHubBranchResponse.Commit::sha)
                                    .map(sha -> new BranchDTO(branch.name(), sha))
                                    .orElse(null))
                            .filter(Objects::nonNull)
                            .collect(Collectors.toList());
                    return new RepositoryDTO(repo.name(), repo.owner().login(), branchDTOs);
                })
                .collect(Collectors.toList());
    }
}
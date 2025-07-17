package com.example.githubAnalyzer.client.dto;

import lombok.Value;

@Value
public class GitHubBranchResponse {
    String name;
    Commit commit;

    @Value
    public static class Commit {
        String sha;
    }
}
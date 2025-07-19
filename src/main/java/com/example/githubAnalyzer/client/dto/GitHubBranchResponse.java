package com.example.githubAnalyzer.client.dto;

public record GitHubBranchResponse(String name, Commit commit) {
    public record Commit(String sha) {
    }
}
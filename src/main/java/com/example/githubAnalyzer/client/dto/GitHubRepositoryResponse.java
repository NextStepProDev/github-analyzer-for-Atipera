package com.example.githubAnalyzer.client.dto;

public record GitHubRepositoryResponse(String name, Owner owner, boolean fork) {
    public record Owner(String login) {
    }
}
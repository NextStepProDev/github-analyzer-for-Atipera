package com.example.githubAnalyzer.client.dto;

import lombok.Value;

@Value
public class GitHubRepositoryResponse {
    String name;
    Owner owner;
    boolean fork;

    @Value
    public static class Owner {
        private String login;
    }
}
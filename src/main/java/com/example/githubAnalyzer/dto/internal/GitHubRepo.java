package com.example.githubAnalyzer.dto.internal;

import lombok.Value;

@Value
public class GitHubRepo {
    String name;
    boolean fork;
    Owner owner;

    @Value
    public static class Owner {
        private String login;
    }
}
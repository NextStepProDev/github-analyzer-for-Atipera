package com.example.githubAnalyzer.exception;

public class GitHubUserNotFoundException extends RuntimeException {
    public GitHubUserNotFoundException(String username) {
        super("User not found: " + username);
    }
}

package com.example.githubAnalyzer.dto.internal;

import lombok.Value;

@Value
public class GitHubBranch {
    String name;
    String lastCommitSha;
}
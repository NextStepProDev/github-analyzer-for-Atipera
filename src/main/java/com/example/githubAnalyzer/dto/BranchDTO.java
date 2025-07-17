package com.example.githubAnalyzer.dto;

import lombok.Value;

@Value
public class BranchDTO {
    String name;
    String lastCommitSha;
}
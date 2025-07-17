package com.example.githubAnalyzer.dto;

import lombok.Value;

import java.util.List;

@Value
public class RepositoryDTO {
    String repositoryName;
    String ownerLogin;
    List<BranchDTO> branches;
}
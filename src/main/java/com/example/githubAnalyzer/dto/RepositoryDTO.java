package com.example.githubAnalyzer.dto;

import java.util.List;

public record RepositoryDTO(String name, String ownerLogin, List<BranchDTO> branches) {
}

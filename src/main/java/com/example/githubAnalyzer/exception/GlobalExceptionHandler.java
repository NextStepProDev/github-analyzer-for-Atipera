package com.example.githubAnalyzer.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles exceptions when a GitHub user is not found.
     *
     * @param ex the exception thrown when a user is not found
     * @return an ErrorResponse with status 404 and a message indicating the user was not found
     */
    @ExceptionHandler(GitHubUserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @SuppressWarnings("unused")
    public ErrorResponse handleGitHubUserNotFound(GitHubUserNotFoundException ex) {
        return new ErrorResponse(404, "User not found");
    }

    public record ErrorResponse(int status, String message) {
    }
}
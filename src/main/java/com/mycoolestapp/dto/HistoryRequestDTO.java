package com.mycoolestapp.dto;

public class HistoryRequestDTO {
    private String jokeText;
    private String category;
    private String generatedAt; // Accept as String, parse in controller
    private Long userId;

    // getters and setters
    public String getJokeText() { return jokeText; }
    public void setJokeText(String jokeText) { this.jokeText = jokeText; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public String getGeneratedAt() { return generatedAt; }
    public void setGeneratedAt(String generatedAt) { this.generatedAt = generatedAt; }
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
}
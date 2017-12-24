package com.merapar.assessment.model;

import java.time.LocalDateTime;

public class TopicMetrics {

    private LocalDateTime firstPost = null;

    private LocalDateTime lastPost = null;

    private int totalPosts = 0;

    private int totalAcceptedPosts = 0;

    private int totalScore = 0;

    private int totalViewCount = 0;

    private int totalAnswerCount = 0;

    private int totalCommentCount = 0;

    public void addPostData(PostData postData) {
        if (this.firstPost == null || this.firstPost.isAfter(postData.getCreationDate())) {
            this.firstPost = postData.getCreationDate();
        }

        if (this.lastPost == null || this.lastPost.isBefore(postData.getCreationDate())) {
            this.lastPost = postData.getCreationDate();
        }

        this.totalPosts += 1;
        this.totalAcceptedPosts += postData.getAcceptedAnswerId() != 0 ? 1 : 0;
        this.totalScore += postData.getScore();
        this.totalViewCount += postData.getViewCount();
        this.totalAnswerCount += postData.getAnswerCount();
        this.totalCommentCount += postData.getCommentCount();
    }

    public LocalDateTime getFirstPost() {
        return firstPost;
    }

    public LocalDateTime getLastPost() {
        return lastPost;
    }

    public int getTotalPosts() {
        return totalPosts;
    }

    public int getTotalAcceptedPosts() {
        return totalAcceptedPosts;
    }

    public int getTotalScore() {
        return totalScore;
    }

    public int getTotalViewCount() {
        return totalViewCount;
    }

    public int getTotalAnswerCount() {
        return totalAnswerCount;
    }

    public int getTotalCommentCount() {
        return totalCommentCount;
    }
}

package com.merapar.assessment.model;

import java.time.LocalDateTime;
import java.util.Objects;

public class TopicMetrics {

    private LocalDateTime firstPost = null;

    private LocalDateTime lastPost = null;

    private int totalPosts = 0;

    private int totalAcceptedPosts = 0;

    private int totalScore = 0;

    private int totalViewCount = 0;

    private int totalAnswerCount = 0;

    private int totalCommentCount = 0;

    public TopicMetrics() {
    }

    public TopicMetrics(LocalDateTime firstPost, LocalDateTime lastPost, int totalPosts, int totalAcceptedPosts, int totalScore, int totalViewCount, int totalAnswerCount, int totalCommentCount) {
        this.firstPost = firstPost;
        this.lastPost = lastPost;
        this.totalPosts = totalPosts;
        this.totalAcceptedPosts = totalAcceptedPosts;
        this.totalScore = totalScore;
        this.totalViewCount = totalViewCount;
        this.totalAnswerCount = totalAnswerCount;
        this.totalCommentCount = totalCommentCount;
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TopicMetrics that = (TopicMetrics) o;
        return totalPosts == that.totalPosts &&
                totalAcceptedPosts == that.totalAcceptedPosts &&
                totalScore == that.totalScore &&
                totalViewCount == that.totalViewCount &&
                totalAnswerCount == that.totalAnswerCount &&
                totalCommentCount == that.totalCommentCount &&
                Objects.equals(firstPost, that.firstPost) &&
                Objects.equals(lastPost, that.lastPost);
    }

    @Override
    public int hashCode() {

        return Objects.hash(firstPost, lastPost, totalPosts, totalAcceptedPosts, totalScore, totalViewCount, totalAnswerCount, totalCommentCount);
    }
}

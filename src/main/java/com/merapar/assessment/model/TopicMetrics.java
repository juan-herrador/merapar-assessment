package com.merapar.assessment.model;

import java.util.Date;

public class TopicMetrics {

    private Date firstPost;

    private Date lastPost;

    private int totalPosts;

    private int totalAcceptedPosts;

    private int totalScore;

    private int totalViewCount;

    private int totalAnswerCount;

    private int totalCommentCount;

    public Date getFirstPost() {
        return firstPost;
    }

    public Date getLastPost() {
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

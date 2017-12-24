package com.merapar.assessment.model;

import java.util.Date;

public class Output {

    private Date firstPost;

    private Date lastPost;

    private int totalPosts;

    private int totalAcceptedPosts;

    private float avgScore;

    private float avgViewCount;

    private float avgAnswerCount;

    private float avgCommentCount;

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

    public float getAvgScore() {
        return avgScore;
    }

    public float getAvgViewCount() {
        return avgViewCount;
    }

    public float getAvgAnswerCount() {
        return avgAnswerCount;
    }

    public float getAvgCommentCount() {
        return avgCommentCount;
    }
}

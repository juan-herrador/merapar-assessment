package com.merapar.assessment.model;

import java.util.Date;

public class PostData {

    private int acceptedAnswerId;

    private Date creationDate;

    private int score;

    private int viewCount;

    private int answerCount;

    private int commentCount;

    public int getAcceptedAnswerId() {
        return acceptedAnswerId;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public int getScore() {
        return score;
    }

    public int getViewCount() {
        return viewCount;
    }

    public int getAnswerCount() {
        return answerCount;
    }

    public int getCommentCount() {
        return commentCount;
    }
}

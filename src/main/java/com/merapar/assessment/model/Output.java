package com.merapar.assessment.model;

import java.time.LocalDateTime;

public class Output {

    private Details details;

    private LocalDateTime analyseDate;

    public Output(TopicMetrics topicMetrics) {
        this.analyseDate = topicMetrics.getAnalyseDate();
        this.details = new Details(topicMetrics);
    }

    public String getAnalyseDate() {
        return analyseDate.toString();
    }

    public Details getDetails() {
        return details;
    }

    private class Details {

        private TopicMetrics topicMetrics;

        public Details(TopicMetrics topicMetrics) {
            this.topicMetrics = topicMetrics;
        }

        public String getFirstPost() {
            return this.topicMetrics.getFirstPost().toString();
        }

        public String getLastPost() {
            return this.topicMetrics.getLastPost().toString();
        }

        public int getTotalPosts() {
            return this.topicMetrics.getTotalPosts();
        }

        public int getTotalAcceptedPosts() {
            return this.topicMetrics.getTotalAcceptedPosts();
        }

        public float getAvgScore() {
            return this.topicMetrics.getTotalPosts() != 0 ?
                    (float)this.topicMetrics.getTotalScore() / this.topicMetrics.getTotalPosts() :
                    0;
        }

        public float getAvgViewCount() {
            return this.topicMetrics.getTotalPosts() != 0 ?
                    (float)this.topicMetrics.getTotalViewCount() / this.topicMetrics.getTotalPosts() :
                    0;
        }

        public float getAvgAnswerCount() {
            return this.topicMetrics.getTotalPosts() != 0 ?
                    (float)this.topicMetrics.getTotalAnswerCount() / this.topicMetrics.getTotalPosts() :
                    0;
        }

        public float getAvgCommentCount() {
            return this.topicMetrics.getTotalPosts() != 0 ?
                    (float)this.topicMetrics.getTotalCommentCount() / this.topicMetrics.getTotalPosts() :
                    0;
        }
    }
}

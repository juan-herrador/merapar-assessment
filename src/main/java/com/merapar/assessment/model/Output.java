package com.merapar.assessment.model;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class Output {

    private Details details;

    private ZonedDateTime analyseDate;

    public Output(TopicMetrics topicMetrics) {
        this.analyseDate = topicMetrics.getAnalyseDate();
        this.details = new Details(topicMetrics);
    }

    public String getAnalyseDate() {
        return analyseDate.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
    }

    public Details getDetails() {
        return details;
    }

    public class Details {

        private TopicMetrics topicMetrics;

        public Details(TopicMetrics topicMetrics) {
            this.topicMetrics = topicMetrics;
        }

        public String getFirstPost() {
            return
                    this.topicMetrics.getFirstPost() != null ?
                            this.topicMetrics.getFirstPost().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME) :
                            null;
        }

        public String getLastPost() {
            return
                    this.topicMetrics.getLastPost() != null ?
                            this.topicMetrics.getLastPost().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME) :
                            null;
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

package com.merapar.assessment.model;

import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class OutputTest extends TestCase {

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
                {
                    new TopicMetrics(
                        null,
                        null,
                        0,
                        0,
                        0,
                        0,
                        0,
                        0
                    )
                },
                {
                    new TopicMetrics(
                        LocalDateTime.parse("2015-07-14T18:39:27.757").atZone(ZoneId.systemDefault()),
                        LocalDateTime.parse("2015-09-12T19:21:54.345").atZone(ZoneId.systemDefault()),
                        1,
                        0,
                        0,
                        0,
                        0,
                        0
                    )
                },
                {
                    new TopicMetrics(
                        LocalDateTime.parse("2015-07-14T18:39:27.757").atZone(ZoneId.systemDefault()),
                        LocalDateTime.parse("2015-09-12T19:21:54.345").atZone(ZoneId.systemDefault()),
                        123,
                        24,
                        342,
                        553,
                        68,
                        236
                    )
                }
        });
    }

    @Parameterized.Parameter
    public TopicMetrics topicMetrics;

    @Test
    public void outputValues() {
        String expectedAnalyseDate =
                topicMetrics.getAnalyseDate() != null ?
                        topicMetrics.getAnalyseDate().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME) :
                        null;
        String expectedFirstPost =
                topicMetrics.getFirstPost() != null ?
                        topicMetrics.getFirstPost().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME) :
                        null;

        String expectedLastPost =
                topicMetrics.getLastPost() != null ?
                        topicMetrics.getLastPost().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME) :
                        null;

        float expectedAvgScore = 0;
        float expectedAvgViewCount = 0;
        float expectedAvgAnswerCount = 0;
        float expectedAvgCommentCount = 0;
        int expectedTotalPosts = topicMetrics.getTotalPosts();
        int expectedTotalAcceptedPosts = topicMetrics.getTotalAcceptedPosts();

        if (topicMetrics.getTotalPosts() != 0) {
            expectedAvgScore = (float)topicMetrics.getTotalScore() / topicMetrics.getTotalPosts();
            expectedAvgViewCount = (float)topicMetrics.getTotalViewCount() / topicMetrics.getTotalPosts();
            expectedAvgAnswerCount = (float)topicMetrics.getTotalAnswerCount() / topicMetrics.getTotalPosts();
            expectedAvgCommentCount = (float)topicMetrics.getTotalCommentCount() / topicMetrics.getTotalPosts();
        }

        Output output = new Output(topicMetrics);

        assertEquals(expectedAnalyseDate, output.getAnalyseDate());
        assertEquals(expectedFirstPost, output.getDetails().getFirstPost());
        assertEquals(expectedLastPost, output.getDetails().getLastPost());
        assertEquals(expectedAvgScore, output.getDetails().getAvgScore());
        assertEquals(expectedAvgViewCount, output.getDetails().getAvgViewCount());
        assertEquals(expectedAvgAnswerCount, output.getDetails().getAvgAnswerCount());
        assertEquals(expectedAvgCommentCount, output.getDetails().getAvgCommentCount());
        assertEquals(expectedTotalAcceptedPosts, output.getDetails().getTotalAcceptedPosts());
        assertEquals(expectedTotalPosts, output.getDetails().getTotalPosts());
    }
}

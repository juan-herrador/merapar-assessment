package com.merapar.assessment.controller;

import com.merapar.assessment.model.TopicMetrics;
import com.merapar.assessment.service.XmlProcessorService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class APIControllerTest {

    @Mock
    private XmlProcessorService xmlProcessorService;

    @InjectMocks
    private APIController apiController;

    private MockMvc mockMvc;

    @Before
    public void setup() {

        // Process mock annotations
        MockitoAnnotations.initMocks(this);

        // Setup Spring test in standalone mode
        this.mockMvc = MockMvcBuilders.standaloneSetup(apiController).build();

    }

    @Test
    public void analyze() throws Exception {
        TopicMetrics topicMetrics =
                new TopicMetrics(
                        LocalDateTime.parse("2015-07-14T18:39:27.757").atZone(ZoneId.systemDefault()),
                        LocalDateTime.parse("2015-09-12T19:21:54.345").atZone(ZoneId.systemDefault()),
                        123,
                        24,
                        342,
                        553,
                        68,
                        236
                );

        doReturn(topicMetrics).when(this.xmlProcessorService).process(any());

        String expectedOutput = getExpectedOutput(topicMetrics);

        this.mockMvc.perform(post("/analyze")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"url\":\"https://anyurl.com/anyfile.xml\"}"))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedOutput));
    }

    @Test
    public void urlNotDefined() throws Exception {
        this.mockMvc.perform(post("/analyze")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"noUrl\":\"urlnotdefined\"}"))
                .andExpect(status().is4xxClientError());
    }

    private static String getExpectedOutput(TopicMetrics topicMetrics) {
        String analyseDate =
                topicMetrics.getAnalyseDate() != null ?
                        "\"" + topicMetrics.getAnalyseDate().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME) + "\"" :
                        null;
        String firstPost =
                topicMetrics.getFirstPost() != null ?
                        "\"" + topicMetrics.getFirstPost().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME) + "\"" :
                        null;

        String lastPost =
                topicMetrics.getLastPost() != null ?
                        "\"" + topicMetrics.getLastPost().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME) + "\"" :
                        null;

        float avgScore = 0;
        float avgViewCount = 0;
        float avgAnswerCount = 0;
        float avgCommentCount = 0;

        if (topicMetrics.getTotalPosts() != 0) {
            avgScore = (float)topicMetrics.getTotalScore() / topicMetrics.getTotalPosts();
            avgViewCount = (float)topicMetrics.getTotalViewCount() / topicMetrics.getTotalPosts();
            avgAnswerCount = (float)topicMetrics.getTotalAnswerCount() / topicMetrics.getTotalPosts();
            avgCommentCount = (float)topicMetrics.getTotalCommentCount() / topicMetrics.getTotalPosts();
        }

        return
            "{" +
                "\"analyseDate\":" + analyseDate + "," +
                "\"details\": {" +
                    "\"avgScore\": " + avgScore + "," +
                    "\"avgViewCount\": " + avgViewCount + "," +
                    "\"avgAnswerCount\": " + avgAnswerCount + "," +
                    "\"avgCommentCount\": " + avgCommentCount + "," +
                    "\"firstPost\":" + firstPost + "," +
                    "\"lastPost\":" + lastPost + "," +
                    "\"totalPosts\": " + topicMetrics.getTotalPosts() + "," +
                    "\"totalAcceptedPosts\": " + topicMetrics.getTotalAcceptedPosts() + "" +
                "}" +
            "}";
    }
}

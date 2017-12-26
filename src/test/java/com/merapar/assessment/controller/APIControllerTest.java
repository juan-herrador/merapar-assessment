package com.merapar.assessment.controller;

import com.merapar.assessment.model.Output;
import com.merapar.assessment.model.TopicMetrics;
import com.merapar.assessment.service.XmlProcessorService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Collection;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(Parameterized.class)
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

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
                {new TopicMetrics()},
                {
                    new TopicMetrics(
                            LocalDateTime.parse("2015-07-14T18:39:27.757").atZone(ZoneId.systemDefault()),
                            LocalDateTime.parse("2015-07-14T18:39:27.757").atZone(ZoneId.systemDefault()),
                            1,
                            1,
                            4,
                            123,
                            5,
                            4)
                },
                {
                    new TopicMetrics(
                            LocalDateTime.parse("2015-07-14T18:39:27.757").atZone(ZoneId.systemDefault()),
                            LocalDateTime.parse("2015-07-14T18:42:42.553").atZone(ZoneId.systemDefault()),
                            2,
                            1,
                            4,
                            123,
                            5,
                            4)
                },
                {
                    new TopicMetrics(
                            LocalDateTime.parse("2015-07-14T18:39:27.757").atZone(ZoneId.systemDefault()),
                            LocalDateTime.parse("2015-07-14T19:16:18.303").atZone(ZoneId.systemDefault()),
                            3,
                            1,
                            5,
                            123,
                            5,
                            6)
                },
        });
    }

    @Parameterized.Parameter(0)
    public TopicMetrics topicMetrics;

    @Test
    public void analyze() throws Exception {
        doReturn(topicMetrics).when(this.xmlProcessorService).process(any());

        String expectedOutput = getExpectedOutput(new Output(topicMetrics));

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

    private static String getExpectedOutput(Output output) {
        String analyseDate =
                output.getAnalyseDate() != null ?
                        "\"" + output.getAnalyseDate() + "\"" :
                        null;

        String firstPost =
                output.getDetails().getFirstPost() != null ?
                        "\"" + output.getDetails().getFirstPost() + "\"" :
                        null;

        String lastPost =
                output.getDetails().getLastPost() != null ?
                        "\"" + output.getDetails().getLastPost() + "\"" :
                        null;

        return
            "{" +
                "\"analyseDate\":" + analyseDate + "," +
                "\"details\": {" +
                    "\"avgScore\": " + output.getDetails().getAvgScore() + "," +
                    "\"avgViewCount\": " + output.getDetails().getAvgViewCount() + "," +
                    "\"avgAnswerCount\": " + output.getDetails().getAvgAnswerCount() + "," +
                    "\"avgCommentCount\": " + output.getDetails().getAvgCommentCount() + "," +
                    "\"firstPost\":" + firstPost + "," +
                    "\"lastPost\":" + lastPost + "," +
                    "\"totalPosts\": " + output.getDetails().getTotalPosts() + "," +
                    "\"totalAcceptedPosts\": " + output.getDetails().getTotalAcceptedPosts() + "" +
                "}" +
            "}";
    }
}

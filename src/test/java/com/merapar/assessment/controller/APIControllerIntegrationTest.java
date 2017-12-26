package com.merapar.assessment.controller;

import com.merapar.assessment.AssessmentApplication;
import com.merapar.assessment.model.Input;
import com.merapar.assessment.model.Output;
import com.merapar.assessment.model.TopicMetrics;
import junit.framework.TestCase;
import org.json.JSONObject;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.mockserver.client.server.MockServerClient;
import org.mockserver.integration.ClientAndServer;
import org.mockserver.matchers.Times;
import org.mockserver.model.Header;
import org.mockserver.model.HttpRequest;
import org.mockserver.model.HttpResponse;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.TestContextManager;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Collection;

import static org.mockserver.integration.ClientAndServer.startClientAndServer;

@RunWith(Parameterized.class)
@SpringBootTest(classes = AssessmentApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class APIControllerIntegrationTest  extends TestCase {

    @LocalServerPort
    private int testPort;

    private TestContextManager testContextManager;

    private TestRestTemplate restTemplate = new TestRestTemplate();

    private HttpHeaders headers = new HttpHeaders();

    private ClientAndServer mockServer;

    private static final int MOCK_SERVER_PORT = 5000;

    private static final String MOCK_SERVER_PATH = "/anypath/anyfile.xml";

    @Before
    public void startServer() throws Exception {
        this.testContextManager = new TestContextManager(getClass());
        this.testContextManager.prepareTestInstance(this);
        mockServer = startClientAndServer(MOCK_SERVER_PORT);
    }

    @After
    public void stopServer() {
        mockServer.stop();
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
                {getEmptyPosts(), new Output(new TopicMetrics())
                },
                {getOnePost(), new Output(
                        new TopicMetrics(
                                LocalDateTime.parse("2015-07-14T18:39:27.757").atZone(ZoneId.systemDefault()),
                                LocalDateTime.parse("2015-07-14T18:39:27.757").atZone(ZoneId.systemDefault()),
                                1,
                                1,
                                4,
                                123,
                                5,
                                4))
                },
                {getTwoPosts(), new Output(
                        new TopicMetrics(
                                LocalDateTime.parse("2015-07-14T18:39:27.757").atZone(ZoneId.systemDefault()),
                                LocalDateTime.parse("2015-07-14T18:42:42.553").atZone(ZoneId.systemDefault()),
                                2,
                                1,
                                4,
                                123,
                                5,
                                4))
                },
                {getThreePosts(), new Output(
                        new TopicMetrics(
                                LocalDateTime.parse("2015-07-14T18:39:27.757").atZone(ZoneId.systemDefault()),
                                LocalDateTime.parse("2015-07-14T19:16:18.303").atZone(ZoneId.systemDefault()),
                                3,
                                1,
                                5,
                                123,
                                5,
                                6))
                },
        });
    }

    @Parameterized.Parameter(0)
    public String xmlContent;

    @Parameterized.Parameter(1)
    public Output expectedOutput;

    @Test
    public void analyze() throws Exception {
        new MockServerClient("localhost", MOCK_SERVER_PORT)
                .when(
                        HttpRequest.request()
                                .withMethod("GET")
                                .withPath(MOCK_SERVER_PATH)
                                .withHeader("\"Content-type\", \"application/json\""),
                        Times.exactly(1))
                .respond(
                        HttpResponse.response()
                                .withStatusCode(200)
                                .withHeaders(
                                        new Header("Content-Type", "application/json; charset=utf-8"))
                                .withBody(xmlContent)
                );

        Input input = new Input("http://localhost:" + MOCK_SERVER_PORT + MOCK_SERVER_PATH);

        HttpEntity<Input> entity = new HttpEntity<>(input, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                "http://localhost:" + testPort + "/analyze",
                HttpMethod.POST, entity, String.class);

        assertTrue(response.getStatusCode().is2xxSuccessful());

        JSONObject bodyJson = new JSONObject(response.getBody());

        JSONAssert.assertEquals(
                getExpectedOutput(
                        bodyJson.getString("analyseDate"),
                        expectedOutput
                ),
                response.getBody(),
                JSONCompareMode.NON_EXTENSIBLE);
    }

    @Test
    public void urlNotDefined() {
        Input input = new Input();

        HttpEntity<Input> entity = new HttpEntity<Input>(input, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                "http://localhost:" + testPort + "/analyze",
                HttpMethod.POST, entity, String.class);

        assertTrue(response.getStatusCode().is4xxClientError());
    }

    private static String getEmptyPosts() {
        return "<posts></posts>";
    }

    private static String getOnePost() {
        return "<posts>" +
                "<row Id='1' PostTypeId='1' AcceptedAnswerId='5' CreationDate='2015-07-14T18:39:27.757' Score='4' ViewCount='123' AnswerCount='5' CommentCount='4'/>" +
                "</posts>";
    }

    private static String getTwoPosts() {
        return "<posts>" +
                "<row Id='1' PostTypeId='1' AcceptedAnswerId='5' CreationDate='2015-07-14T18:39:27.757' Score='4' ViewCount='123' AnswerCount='5' CommentCount='4'/>" +
                "<row Id='2' PostTypeId='2' ParentId='1' CreationDate='2015-07-14T18:42:42.553' Score='0' OwnerUserId='20' LastActivityDate='2015-07-14T18:42:42.553' CommentCount='0'/>" +
                "</posts>";
    }

    private static String getThreePosts() {
        return "<posts>" +
                "<row Id='1' PostTypeId='1' AcceptedAnswerId='5' CreationDate='2015-07-14T18:39:27.757' Score='4' ViewCount='123' AnswerCount='5' CommentCount='4'/>" +
                "<row Id='2' PostTypeId='2' ParentId='1' CreationDate='2015-07-14T18:42:42.553' Score='0' OwnerUserId='20' LastActivityDate='2015-07-14T18:42:42.553' CommentCount='0'/>" +
                "<row Id='3' PostTypeId='2' ParentId='1' CreationDate='2015-07-14T19:16:18.303' Score='1' OwnerUserId='5' LastActivityDate='2015-07-14T19:16:18.303' CommentCount='2'/>" +
                "</posts>";
    }

    private static String getExpectedOutput(String analyseDateStr, Output output) {

        String analyseDate =
                analyseDateStr != null ?
                        "\"" + analyseDateStr + "\"" :
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

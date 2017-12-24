package com.merapar.assessment.service.impl;

import com.merapar.assessment.model.TopicMetrics;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.StringReader;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class GitHubTopicXmlStreamReaderServiceTest extends TestCase {

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
                {getEmptyPosts(),
                        new TopicMetrics()
                },
                {getOnePost(),
                        new TopicMetrics(
                                LocalDateTime.parse("2015-07-14T18:39:27.757"),
                                LocalDateTime.parse("2015-07-14T18:39:27.757"),
                                1,
                                1,
                                4,
                                123,
                                5,
                                4)
                },
                {getTwoPosts(),
                        new TopicMetrics(
                                LocalDateTime.parse("2015-07-14T18:39:27.757"),
                                LocalDateTime.parse("2015-07-14T18:42:42.553"),
                                2,
                                1,
                                4,
                                123,
                                5,
                                4)
                },
                {getThreePosts(),
                        new TopicMetrics(
                                LocalDateTime.parse("2015-07-14T18:39:27.757"),
                                LocalDateTime.parse("2015-07-14T19:16:18.303"),
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
    public String xmlContent;

    @Parameterized.Parameter(1)
    public TopicMetrics expectedMetrics;

    @Test
    public void processEmptyXml() throws XMLStreamException {
        XMLInputFactory f = XMLInputFactory.newInstance();
        XMLStreamReader reader = f.createXMLStreamReader(new StringReader(this.xmlContent));

        GitHubTopicXmlStreamReaderService service = new GitHubTopicXmlStreamReaderService();
        assertEquals(this.expectedMetrics, service.readDocument(reader));
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
}
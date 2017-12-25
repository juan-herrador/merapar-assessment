package com.merapar.assessment.service.impl;

import com.merapar.assessment.model.PostData;
import com.merapar.assessment.model.TopicMetrics;
import com.merapar.assessment.service.XmlStreamReaderService;
import org.springframework.stereotype.Service;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Service
public class GitHubTopicXmlStreamReaderService implements XmlStreamReaderService {

    public TopicMetrics readDocument(XMLStreamReader reader) throws XMLStreamException {
        while (reader.hasNext()) {
            int eventType = reader.next();
            switch (eventType) {
                case XMLStreamReader.START_ELEMENT:
                    String elementName = reader.getLocalName();
                    if (elementName.equals("posts"))
                        return readPosts(reader);
                    break;
                case XMLStreamReader.END_ELEMENT:
                    break;
            }
        }
        throw new XMLStreamException("Premature end of file");
    }

    private TopicMetrics readPosts(XMLStreamReader reader) throws XMLStreamException {
        TopicMetrics metrics = new TopicMetrics();

        String elementName;
        while (reader.hasNext()) {
            int eventType = reader.next();
            switch (eventType) {
                case XMLStreamReader.START_ELEMENT:
                    elementName = reader.getLocalName();
                    if (elementName.equals("row"))
                        metrics.addPostData(readPost(reader));
                    break;
                case XMLStreamReader.END_ELEMENT:
                    elementName = reader.getLocalName();
                    if (elementName.equals("row"))
                        break;
                    return metrics;
            }
        }
        throw new XMLStreamException("Premature end of file");
    }

    private PostData readPost(XMLStreamReader reader) {
        PostData postData = new PostData();
        LocalDateTime creationDate = LocalDateTime.parse(reader.getAttributeValue(null, "CreationDate"), DateTimeFormatter.ISO_DATE_TIME);
        postData.setCreationDate(creationDate.atZone(ZoneId.systemDefault()));
        postData.setCommentCount(getIntegerAttributeValue(reader, "CommentCount"));
        postData.setScore(getIntegerAttributeValue(reader, "Score"));
        postData.setAcceptedAnswerId(getIntegerAttributeValue(reader, "AcceptedAnswerId"));
        postData.setAnswerCount(getIntegerAttributeValue(reader, "AnswerCount"));
        postData.setViewCount(getIntegerAttributeValue(reader, "ViewCount"));

        return postData;
    }

    private int getIntegerAttributeValue(XMLStreamReader reader, String localName) {
        String attributeValue = reader.getAttributeValue(null, localName);
        return attributeValue == null ? 0 : Integer.valueOf(attributeValue);
    }
}

package com.merapar.assessment.service;

import com.merapar.assessment.model.TopicMetrics;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

public interface XmlStreamReaderService {

    TopicMetrics readDocument(XMLStreamReader reader) throws XMLStreamException;
}

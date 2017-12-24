package com.merapar.assessment.service;

import com.merapar.assessment.model.Input;
import com.merapar.assessment.model.TopicMetrics;

import javax.xml.stream.XMLStreamException;

public interface XmlProcessorService {

    TopicMetrics process(Input input) throws XMLStreamException;
}

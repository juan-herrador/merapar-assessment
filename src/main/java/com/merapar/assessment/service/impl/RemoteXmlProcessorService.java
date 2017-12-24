package com.merapar.assessment.service.impl;

import com.merapar.assessment.model.Input;
import com.merapar.assessment.model.TopicMetrics;
import com.merapar.assessment.service.XmlProcessorService;
import com.merapar.assessment.service.XmlStreamReaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.IOException;
import java.net.URL;

@Service
public class RemoteXmlProcessorService implements XmlProcessorService {

    @Autowired
    private XmlStreamReaderService xmlStreamReaderService;

    @Override
    public TopicMetrics process(Input input) throws XMLStreamException {
        XMLStreamReader reader = null;
        try {
            URL url = new URL(input.getUrl());
            reader = XMLInputFactory.newInstance().createXMLStreamReader(url.openStream());
            return this.xmlStreamReaderService.readDocument(reader);
        } catch (IOException ex) {
            // handle exception

        } finally {
            if (reader != null) {
                reader.close();
            }
        }
        return null;
    }
}

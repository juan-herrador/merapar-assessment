package com.merapar.assessment.controller;

import com.merapar.assessment.model.Input;
import com.merapar.assessment.model.Output;
import com.merapar.assessment.model.TopicMetrics;
import com.merapar.assessment.service.XmlProcessorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.xml.stream.XMLStreamException;

@RestController
public class APIController {

    @Autowired
    private XmlProcessorService xmlProcessorService;

    @RequestMapping(value = "/analyze", method = RequestMethod.POST)
    public Output analyze(@RequestBody Input input) {
        TopicMetrics topicMetrics = null;
        try {
            topicMetrics = this.xmlProcessorService.process(input);
        } catch (XMLStreamException ex) {

        }

        return new Output(topicMetrics);
    }
}

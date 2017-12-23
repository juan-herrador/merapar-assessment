package com.merapar.assessment.service.impl;

import com.merapar.assessment.model.Input;
import com.merapar.assessment.model.Output;
import com.merapar.assessment.service.XmlProcessorService;
import org.springframework.stereotype.Service;

@Service
public class RemoteXmlProcessorService implements XmlProcessorService {

    @Override
    public Output process(Input input) {
        Output output = new Output();
        output.setUrl(input.getUrl());
        return output;
    }
}

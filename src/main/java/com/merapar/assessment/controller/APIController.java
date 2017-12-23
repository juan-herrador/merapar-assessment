package com.merapar.assessment.controller;

import com.merapar.assessment.model.Input;
import com.merapar.assessment.model.Output;
import com.merapar.assessment.service.XmlProcessorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class APIController {

    @Autowired
    private XmlProcessorService xmlProcessorService;

    @RequestMapping(value = "/analyze", method = RequestMethod.POST)
    public Output analyze(@RequestBody Input input){
        return this.xmlProcessorService.process(input);
    }
}

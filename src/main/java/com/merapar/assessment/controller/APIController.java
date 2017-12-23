package com.merapar.assessment.controller;

import com.merapar.assessment.model.Input;
import org.springframework.web.bind.annotation.*;

@RestController
public class APIController {

    @RequestMapping(value = "/analyze", method = RequestMethod.POST)
    public String analyze(@RequestBody Input input){
        return input.getUrl();
    }
}

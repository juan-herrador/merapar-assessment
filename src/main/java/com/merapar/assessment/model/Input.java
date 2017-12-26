package com.merapar.assessment.model;

import javax.validation.constraints.NotNull;

public class Input {

    @NotNull
    private String url;

    public Input() {
    }

    public Input(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }
}

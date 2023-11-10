package com.example.taskmanager.dto.ResultDTOPac;

public abstract class ResultDTO {
    protected String responseMessage = "OK";

    public ResultDTO() {
    }

    public ResultDTO(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }
}

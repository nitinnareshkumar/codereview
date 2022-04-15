package com.review.code.RapidDev.event;


import org.springframework.context.ApplicationEvent;

public class AfterItemsXMLCreationEvent extends ApplicationEvent {
    private Long jobid;
    private String typeCode;
    private String operation;

    public AfterItemsXMLCreationEvent(Object source, Long jobid , String typeCode, String operation) {
        super(source);
        this.jobid = jobid;
        this.typeCode = typeCode;
        this.operation = operation;
    }
    public Long getMessage() {
        return jobid;
    }

    public Long getJobid() {
        return jobid;
    }

    public void setJobid(Long jobid) {
        this.jobid = jobid;
    }

    public String getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }
}
package com.dtolabs.rundeck.plugin.notificationplugin;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "notification")
public class NotificationModel {

    private String trigger;
    private String status;
    private int executionId;
    private Executions executions;

    public String getTrigger() { return trigger; }

    @XmlAttribute
    public void setTrigger(String trigger) {
        this.trigger = trigger;
    }

    public String getStatus() { return status; }

    @XmlAttribute
    public void setStatus(String status) {
        this.status = status;
    }

    public int getExecutionId() { return executionId; }

    @XmlAttribute
    public void setExecutionId(int executionId) {
        this.executionId = executionId;
    }

    public Executions getExecutions() { return executions; }

    @XmlElement
    public void setExecutions (Executions executions) { this.executions = executions; }

}

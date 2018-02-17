package com.dtolabs.rundeck.plugin.notificationplugin;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Executions {
    private String count;
    private Execution execution;

    public String getCount() {
        return count;
    }

    @XmlAttribute
    public void setCount(String count) {
        this.count = count;
    }

    public Execution getExecution() {
        return execution;
    }

    @XmlElement
    public void setExecution(Execution execution) {
        this.execution = execution;
    }
}

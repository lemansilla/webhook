package com.dtolabs.rundeck.plugin.notificationplugin;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement
@XmlType(propOrder = {"name", "group", "project", "description"})
public class Job {

    private String id;
    private String href;
    private String name;
    private String group;
    private String project;
    private String description;

    public String getId() {
        return id;
    }

    @XmlAttribute
    public void setId(String id) {
        this.id = id;
    }

    public String getHref() {
        return href;
    }

    @XmlAttribute
    public void setHref(String href) { this.href = href; }

    public String getName() {
        return name;
    }

    @XmlElement
    public void setName(String name) {
        this.name = name;
    }

    public String getGroup() {
        return group;
    }

    @XmlElement
    public void setGroup(String group) {
        this.group = group;
    }

    public String getProject() {
        return project;
    }

    @XmlElement
    public void setProject(String project) {
        this.project = project;
    }

    public String getDescription() {
        return description;
    }

    @XmlElement
    public void setDescription(String description) {
        this.description = description;
    }

}

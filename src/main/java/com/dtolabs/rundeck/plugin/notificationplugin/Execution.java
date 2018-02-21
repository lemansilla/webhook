package com.dtolabs.rundeck.plugin.notificationplugin;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Execution model
 */
@XmlRootElement
@XmlType(propOrder = {"user", "datestarted", "job", "description"})
public class Execution {

    private String id;
    private String href;
    private String permalink;
    private String user;
    private DateStarted datestarted;
    private Job job;
    private String description;
    //private String argstring;

    public String getId() {
        return id;
    }

    @XmlAttribute
    public void setId(String id) { this.id = id; }

    public String getHref() {
        return href;
    }

    @XmlAttribute
    public void setHref(String href) {
        this.href = href;
    }

    public String getPermalink() {
        return permalink;
    }

    @XmlAttribute
    public void setPermalink(String permalink) {
        this.permalink = permalink;
    }

    public String getUser() {
        return user;
    }

    @XmlElement
    public void setUser(String user) {
        this.user = user;
    }

    public DateStarted getDatestarted() {
        return datestarted;
    }

    @XmlElement
    public void setDatestarted(DateStarted datestarted) {
        this.datestarted = datestarted;
    }

    public Job getJob() {
        return job;
    }

    @XmlElement
    public void setJob(Job job) {
        this.job = job;
    }

    public String getDescription() {
        return description;
    }

    @XmlElement
    public void setDescription(String description) {
        this.description = description;
    }

    /*public String getArgstring() {
        return argstring;
    }

    public void setArgstring(String argstring) {
        this.argstring = argstring;
    } */
}

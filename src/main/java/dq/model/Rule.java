package dq.model;

import java.time.Instant;
import java.util.UUID;

public class Rule {
    private String id;
    private String name;
    private String description;
    private String body;
    private Boolean active;
    private String author;
    private Instant knowledgeBeginTime;
    private Instant updateTime;
    private Instant knowledgeEndTime;

    public Rule() {
    }

    public Rule(String name, String description, String body, String author) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.description = description;
        this.body = body;
        this.author = author;
        this.active = true;
        this.knowledgeBeginTime = Instant.now();
    }

    public void setId(String id) {
        this.id = id;
    }

    public Instant getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Instant updateTime) {
        this.updateTime = updateTime;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public void setKnowledgeEndTime(Instant knowledgeEndTime) {
        this.knowledgeEndTime = knowledgeEndTime;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getBody() {
        return body;
    }

    public Boolean getActive() {
        return active;
    }

    public String getAuthor() {
        return author;
    }

    public Instant getKnowledgeBeginTime() {
        return knowledgeBeginTime;
    }

    public Instant getKnowledgeEndTime() {
        return knowledgeEndTime;
    }

    public String getDescription() {
        return description;
    }
}

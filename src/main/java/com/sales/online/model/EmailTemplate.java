package com.sales.online.model;

import java.util.Map;

public class EmailTemplate {
  private String from;
  private String to;
  private String subject;
  private String template;
  private Map<String, Object> model;

  public EmailTemplate(String to, String subject, String template, Map<String, Object> model) {
    this.to = to;
    this.subject = subject;
    this.template = template;
    this.model = model;
  }

  public String getFrom() {
    return from;
  }

  public void setFrom(String from) {
    this.from = from;
  }

  public String getTo() {
    return to;
  }

  public void setTo(String to) {
    this.to = to;
  }

  public String getSubject() {
    return subject;
  }

  public void setSubject(String subject) {
    this.subject = subject;
  }

  public String getTemplate() {
    return template;
  }

  public void setTemplate(String template) {
    this.template = template;
  }

  public Map<String, Object> getModel() {
    return model;
  }

  public void setModel(Map<String, Object> model) {
    this.model = model;
  }

  @Override
  public String toString() {
    return "EmailTemplate [from="
        + from
        + ", to="
        + to
        + ", subject="
        + subject
        + ", template="
        + template
        + ", model="
        + model
        + "]";
  }
}

package com.sales.online.service;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import com.sales.online.model.EmailTemplate;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Service
public class EmailService {

  private JavaMailSender emailSender;
  private Configuration freemarkerConfig;
  private String from;

  public EmailService(
      @Value("${spring.mail.username}") String from,
      JavaMailSender emailSender,
      Configuration freemarkerConfig) {
    this.from = from;
    this.emailSender = emailSender;
    this.freemarkerConfig = freemarkerConfig;
  }

  public void sendMessage(EmailTemplate emailTemplate)
      throws MessagingException, IOException, TemplateException {
    MimeMessage message = emailSender.createMimeMessage();
    MimeMessageHelper helper =
        new MimeMessageHelper(
            message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());

    freemarkerConfig.setClassForTemplateLoading(this.getClass(), "/mail-templates");
    freemarkerConfig.setNumberFormat("#");

    Template template = freemarkerConfig.getTemplate(emailTemplate.getTemplate());
    String html =
        FreeMarkerTemplateUtils.processTemplateIntoString(template, emailTemplate.getModel());

    helper.setTo(emailTemplate.getTo());
    helper.setText(html, true);
    helper.setSubject(emailTemplate.getSubject());
    helper.setFrom(from);

    emailSender.send(message);
  }
}

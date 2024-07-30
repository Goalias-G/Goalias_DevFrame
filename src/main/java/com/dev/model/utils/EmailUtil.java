package com.dev.model.utils;

import com.dev.model.context.BizException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;
import org.springframework.core.io.FileSystemResource;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.Objects;

@Component
public class EmailUtil {

    @Resource
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String from;

    /**
     * 发送简单文本邮件
     *
     * @param to      收件人
     * @param subject 邮件主题
     * @param text    邮件内容
     */
    public void sendSimpleMail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        mailSender.send(message);
    }

    /**
     * 发送带附件的邮件
     *
     * @param to       收件人
     * @param subject  邮件主题
     * @param text     邮件内容
     * @param filePath 附件路径
     * @throws MessagingException
     */
    public void sendMailWithAttachment(String to, String subject, String text, String filePath){//文件存储方式？
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setFrom(from);
            helper.setValidateAddresses(true);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text);

            FileSystemResource file = new FileSystemResource(new File(filePath));
            helper.addAttachment(Objects.requireNonNull(file.getFilename()), file);
        } catch (MessagingException e) {
            throw new BizException(0, "附件邮件发送失败");
        }

        mailSender.send(mimeMessage);
    }

    /**
     * 发送HTML格式邮件
     *
     * @param to      收件人
     * @param subject 邮件主题
     * @param html    HTML内容
     */
    public void sendHtmlMail(String to, String subject, String html) {
        javax.mail.internet.MimeMessage mimeMessage = mailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setFrom(from);
            helper.setValidateAddresses(true);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(html, true);
        } catch (MessagingException e) {
            throw new BizException(0, "html邮件发送失败");
        }
        mailSender.send(mimeMessage);
    }

    public String findPasswordTemplate(String accountName, String code, String mainUrl) {
        return "<body>" +
                "<div style='margin:0;'><span style='font-size: 20px;'><b>尊敬的用户：</b>" +
                "</span></div><div style='margin:0;'>&nbsp; &nbsp; &nbsp;您好！（登录账号：<b>" +
                accountName +
                "</b>）</div><div style='margin:0;'>您正在通过邮件重置密码，请点击" +
                "<a href='https://" + mainUrl + "/index?to=4&code=" + code
                + "' style='text-decoration:none;' taret='_blank'><b>这里</b></a>" +
                "跳转，" +
                "</div><div style='margin:0;'>或复制链接：<b>https://"
                + mainUrl + "/index?to=4&code=" + code
                + "</b></div><div style='margin:0;'><br></div><div style='margin:0;'>" +
                "如果您现在想起了您的密码：</div><div style='margin:0;'>" +
                "您可以忽略上述信息，可继续使用原来的密码登录，无需执行任何操作。" +
                "</div><div><span style='color: rgb(221, 64, 50);'><b>如果此邮件非您本人操作，您无需理会此邮件。" +
                "</b></span></div><div style='margin:0;'>这是一封系统自动发出的邮件，请不要直接回复。" +
                "</div><div style='margin:0;'><br></div><div style='margin:0;'><br></div></body>";
    }
}

package com.scot.iframework.mail;

import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.VelocityException;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.ui.velocity.VelocityEngineUtils;

import javax.annotation.Resource;
import javax.mail.Address;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Map;
import java.util.Properties;


/**
 * 邮件模板发送.
 * 不同模板配置多个本实例
 * 默认编码使用UTF-8
 * Created by shengke.
 */
public class VelocityMail {

    /**
     * 字符编码.
     * 默认UTF-8
     */
    private String charset = "UTF-8";

    /**
     * 邮件heads，非必填，xml配置.
     */
    private Properties mailHeaders = new Properties();

    /**
     * 收件人数组.
     */
    private ReceiveInfo[] receiveTo;

    /**
     * 抄送人数组.
     */
    private ReceiveInfo[] receiveCc;

    /**
     * 密送人数组.
     */
    private ReceiveInfo[] receiveBcc;

    /**
     * 邮件发送者.
     * 包括发送者姓名和地址，用于设置在邮件的from栏目中
     */
    private String from;

    /**
     * 邮件主题.
     */
    private String subject;

    /**
     * 邮件内容模板地址.
     */
    private String templateUrl;

    /**
     * velocity引擎.
     */
    @Resource(name = "velocityEngine")
    private VelocityEngine velocityEngine;

    /**
     * JavaMail发送器,通过Spring诸注入
     */
    @Resource(name = "mailSender")
    private JavaMailSender mailSender;

    public Properties getMailHeaders() {
        return mailHeaders;
    }

    public void setMailHeaders(Properties mailHeaders) {
        this.mailHeaders = mailHeaders;
    }

    public String getFrom() {
        return this.from;
    }

    public Address getFromAddress() throws AddressException {
        return new InternetAddress(this.from);
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getTemplateUrl() {
        return templateUrl;
    }

    public void setTemplateUrl(String templateUrl) {
        this.templateUrl = templateUrl;
    }

    public VelocityEngine getVelocityEngine() {
        return velocityEngine;
    }

    public void setVelocityEngine(VelocityEngine velocityEngine) {
        this.velocityEngine = velocityEngine;
    }

    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    public ReceiveInfo[] getReceiveTo() {
        return receiveTo;
    }

    public void setReceiveTo(ReceiveInfo[] receiveTo) {
        this.receiveTo = receiveTo;
    }

    public ReceiveInfo[] getReceiveCc() {
        return receiveCc;
    }

    public void setReceiveCc(ReceiveInfo[] receiveCc) {
        this.receiveCc = receiveCc;
    }

    public ReceiveInfo[] getReceiveBcc() {
        return receiveBcc;
    }

    public void setReceiveBcc(ReceiveInfo[] receiveBcc) {
        this.receiveBcc = receiveBcc;
    }

    public JavaMailSender getMailSender() {
        return mailSender;
    }

    public void setMailSender(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    /**
     *  发送入口.
     * @param model 模板填充数据
     * @throws Exception 异常信息
     */
    public void send(Map<String, Object> model) throws Exception {
        sendMime(mergeSimpleMessage(model));
    }

    /**
     * 转换为邮件数组.
     * @param receiveInfos  邮件地址对象数组
     * @return  邮件地址数组.
     */
    private String[] assembleReceive(ReceiveInfo[] receiveInfos) {
        if (null != receiveInfos && receiveInfos.length > 0) {
            String[] infos = new String[receiveInfos.length];
            for (int i = 0; i < receiveInfos.length; i++) {
                infos[i] = receiveInfos[i].toString();
            }
            return infos;
        } else {
            return null;
        }
    }


    /**
     * 邮件发送.
     * @param simpleMessage 邮件消息
     * @throws MailException    邮件异常
     */
    private void sendMime(SimpleMailMessage simpleMessage) throws MailException {
        mailSender.send(toMimeMessage(simpleMessage));
    }



    /**
     * 填充数据.
     * 转换模板，填充接收人
     * @param model 模板数据
     * @return  邮件消息SimpleMailMessage
     */
    private SimpleMailMessage mergeSimpleMessage(Map<String, Object> model) {
        String text = null;
        try {
            text = renderText(model);
        } catch (VelocityException e) {
            e.printStackTrace();
        }
        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject(getSubject());
        message.setFrom(getFrom());
        message.setTo(assembleReceive(receiveTo));
        if (null != receiveCc && receiveCc.length > 0) {
            message.setCc(assembleReceive(receiveCc));
        }
        if (null != receiveBcc && receiveBcc.length > 0) {
            message.setBcc(assembleReceive(receiveBcc));
        }
        message.setText(text);
        return message;
    }


    /**
     * 生成Mime邮件体.
     * @param simpleMailMessage 邮件消息信息.
     * @return  MimeMessagePreparator
     */
    private MimeMessagePreparator toMimeMessage(final SimpleMailMessage simpleMailMessage) {
        MimeMessagePreparator preparator = new MimeMessagePreparator() {

            public void prepare(MimeMessage mimeMessage) throws Exception {
                MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true, charset);
                message.setTo(simpleMailMessage.getTo());
                if (null != simpleMailMessage.getCc()) {
                    message.setCc(simpleMailMessage.getCc());
                }
                if (null != simpleMailMessage.getBcc()) {
                    message.setBcc(simpleMailMessage.getBcc());
                }
                message.setSubject(simpleMailMessage.getSubject());
                message.setText(simpleMailMessage.getText(), true);
                message.setFrom(simpleMailMessage.getFrom());
            }

        };
        return preparator;
    }


    /**
     * 模板载入.
     * 生成文本信息。
     * @param model 数据
     * @return  文本信息
     * @throws VelocityException    模板异常
     */
    public String renderText(Map model) throws VelocityException {
        return VelocityEngineUtils.mergeTemplateIntoString(getVelocityEngine(),
                getTemplateUrl(), charset, model);
    }

}

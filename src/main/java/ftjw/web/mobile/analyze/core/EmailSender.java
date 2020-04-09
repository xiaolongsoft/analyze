package ftjw.web.mobile.analyze.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.util.StringUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 殷晓龙
 * 2020/3/18 16:57
 */
@Component
public class EmailSender {

    @Value("${spring.mail.username}")
    private  String from;
    @Autowired
    JavaMailSender sender;
    public void  sendEmail(String emails,String title,String content) throws MessagingException {
        MimeMessage msg=sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(msg, true);
        helper.setFrom(from);
        helper.setTo(emails);
        helper.setSubject(title);
        helper.setText(content,true);

        try {
                sender.send(msg);
            System.out.println(emails);
        } catch (MailException e) {
            System.out.println("这个邮箱有问题:"+emails);
        }

    }
}

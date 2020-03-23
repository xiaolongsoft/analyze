package ftjw.web.mobile.analyze.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

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
    public void  sendEmail(List<String> emails,String title,String content){
        SimpleMailMessage simpleMailMessage=new SimpleMailMessage();
        simpleMailMessage.setFrom(from);
        simpleMailMessage.setTo( emails.stream().collect(Collectors.joining(",")));
        simpleMailMessage.setSubject(title);
        simpleMailMessage.setText(content);
        sender.send(simpleMailMessage);

    }
}

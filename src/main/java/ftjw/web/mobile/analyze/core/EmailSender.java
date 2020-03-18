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
    public void  sendEmail(List<String> emails){
        SimpleMailMessage simpleMailMessage=new SimpleMailMessage();
        simpleMailMessage.setFrom(from);
        simpleMailMessage.setTo( emails.stream().collect(Collectors.joining(",")));
        simpleMailMessage.setSubject("统计周报");
        simpleMailMessage.setText("春花秋月何时了，往事知多少");

        sender.send(simpleMailMessage);

    }
}

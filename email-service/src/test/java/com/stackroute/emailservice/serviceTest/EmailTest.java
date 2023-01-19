//package com.stackroute.emailservice.serviceTest;
//
//import com.icegreen.greenmail.junit5.GreenMailExtension;
//import com.icegreen.greenmail.util.GreenMail;
//import com.icegreen.greenmail.util.GreenMailUtil;
//import com.icegreen.greenmail.util.ServerSetup;
//import com.icegreen.greenmail.util.ServerSetupTest;
//import org.junit.After;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.junit.jupiter.api.extension.RegisterExtension;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.mail.SimpleMailMessage;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.mail.javamail.JavaMailSenderImpl;
//import javax.mail.Message;
//import javax.mail.MessagingException;
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertTrue;
//
//
//
//
//@ExtendWith(MockitoExtension.class)
//public class EmailTest {
//
//    @RegisterExtension
//    GreenMailExtension greenMail = new GreenMailExtension(ServerSetupTest.SMTP);
//    @Autowired
//    private JavaMailSender emailSender;
//
//    @Autowired
//    private GreenMail testSmtp;
//
//    @Before
//    public void setUp(){
////        ServerSetup setup = new ServerSetup(3025,"localhost","smtp");
////        testSmtp = new GreenMail(setup);
////        testSmtp.start();
////        emailSender.setHost("localhost");
////        emailSender.setPort(3025);
//    }
//    @Test
//    public void testEmail() throws InterruptedException, MessagingException {
//        SimpleMailMessage message = new SimpleMailMessage();
//        message.setFrom("test@sender.com");
//        message.setTo("test@receiver.com");
//        message.setSubject("test subject");
//        message.setText("test message");
//        emailSender.send(message);
//        assertTrue(testSmtp.waitForIncomingEmail(5000, 1));
//        Message[] messages = testSmtp.getReceivedMessages();
//        assertEquals(1, messages.length);
//        assertEquals("test subject", messages[0].getSubject());
//        String body = GreenMailUtil.getBody(messages[0]).replaceAll("=\r?\n", "");
//        assertEquals("test message", body);
//    }
//    @After
//    public void cleanup(){
//        testSmtp.stop();
//    }
//}

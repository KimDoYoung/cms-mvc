package kr.dcos.common.mail;

import java.io.UnsupportedEncodingException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.xml.internal.messaging.saaj.packaging.mime.MessagingException;

public class GoogleMailSenderTest {
	
	private static Logger logger = LoggerFactory
			.getLogger(GoogleMailSenderTest.class);
	

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testSendWithMailInfo() throws MessagingException, javax.mail.MessagingException, UnsupportedEncodingException {
		MailInfo mailInfo = new MailInfo();
		//Server,id,pw
//		mailInfo.setMailServer("smtp.gmail.com");
//		mailInfo.setPort(456);
		mailInfo.setMailServerUserId("kdy987");
		mailInfo.setMailServerUserPw("dbsgml987");
		
		mailInfo.setTo(new String[]{"kdy987@naver.com|dykim987@daum.net,김도영"});
		mailInfo.addTo("kdy987@gmail.com","김도영",null);
		mailInfo.setFrom("kdy987@google.com");
		mailInfo.setSubject("첨부파일명이 깨진다. we are the world 김도영 테스팅 123");
		mailInfo.setContent("김도영왜 파일이 깨지는 걸까? mail <b>123</b>");
		mailInfo.setHtml(true);
		
		mailInfo.addAttachFile("C:/Users/Administrator/Documents/1.sql");
		mailInfo.addAttachFile("C:/Users/Administrator/Documents/삼사라-poster.jpg");
		mailInfo.addAttachFile("C:/Users/Administrator/Documents/20130506_견적서.pdf");
		
		if(mailInfo.isValid()){
			GoogleMailSender.Send(mailInfo);
		}else {
			for (String error : mailInfo.errorMessages()) {
				logger.debug(error);
			} 
		}
	}

}

/*
 * Copyright 2002-2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package hu.gdf.balazsbole.email.imap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.apache.commons.mail.util.MimeMessageParser;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.FileSystemResource;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.mail.ImapIdleChannelAdapter;
import org.springframework.integration.mail.ImapMailReceiver;
import org.springframework.integration.mail.MailReceiver;
import org.springframework.integration.mail.support.DefaultMailHeaderMapper;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;

import javax.mail.internet.MimeMessage;

/**
 * @author Oleg Zhurakousky
 * @author Gary Russell
 *
 */
public class GmailInboundImapIdleAdapterTestApp {
	private static Log logger = LogFactory.getLog(GmailInboundImapIdleAdapterTestApp.class);



	public static void main (String[] args) throws Exception {
		@SuppressWarnings("resource")
		ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext(
				"/gmail-imap-idle-config.xml");

		ImapMailReceiver mailReceiver = new ImapMailReceiver("imaps://helpdesk.gdf:Tha0xi7k@imap.yandex.com:993/inbox");
		mailReceiver.setShouldMarkMessagesAsRead(false);
		mailReceiver.setShouldDeleteMessages(false);
		mailReceiver.setSimpleContent(false);
		mailReceiver.setHeaderMapper(new DefaultMailHeaderMapper());
		mailReceiver.setUserFlag("user-flag-1");
		ImapIdleChannelAdapter adapter = new ImapIdleChannelAdapter(mailReceiver);
	adapter.setAutoStartup(true);
		DirectChannel channel = new DirectChannel();
		adapter.setOutputChannel(channel);


		DirectChannel inputChannel = ac.getBean("receiveChannel", DirectChannel.class);
		inputChannel.subscribe(new MessageHandler() {
			@Override
			public void handleMessage(Message<?> message) throws MessagingException {
				try {
					MimeEnt
					(					(MimeMessage) message).writeTo();

					new MimeMessageParser(((MimeMessage) message.getPayload())).parse().hasHtmlContent();
					new MimeMessageParser(((MimeMessage) message.getPayload())).parse().getPlainContent();
					new MimeMessageParser(((MimeMessage) message.getPayload())).parse().getHtmlContent();
					new MimeMessageParser(((MimeMessage) message.getPayload())).parse().getAttachmentList()
				}catch (Exception e){}

				logger.info("Message: " + message);
			}
		});
	}

	public void sendMessage(){
		// of course you would use DI in any real-world cases
		JavaMailSenderImpl sender = new JavaMailSenderImpl();
		sender.setHost("mail.host.com");

		MimeMessage message = sender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);
		helper.setTo("test@host.com");
		helper.setText("Thank you for ordering!");

		sender.send(message);



		JavaMailSenderImpl sender = new JavaMailSenderImpl();
		sender.setHost("mail.host.com");

		MimeMessage message = sender.createMimeMessage();

// use the true flag to indicate you need a multipart message
		MimeMessageHelper helper = new MimeMessageHelper(message, true);
		helper.setTo("test@host.com");

		helper.setText("Check out this image!");

// let's attach the infamous windows Sample file (this time copied to c:/)
		FileSystemResource file = new FileSystemResource(new File("c:/Sample.jpg"));
		helper.addAttachment("CoolImage.jpg", file);

		sender.send(message);


		
		JavaMailSenderImpl sender = new JavaMailSenderImpl();
		sender.setHost("mail.host.com");

		MimeMessage message = sender.createMimeMessage();

// use the true flag to indicate you need a multipart message
		MimeMessageHelper helper = new MimeMessageHelper(message, true);
		helper.setTo("test@host.com");

// use the true flag to indicate the text included is HTML
		helper.setText("<html><body><img src='cid:identifier1234'></body></html>", true);

// let's include the infamous windows Sample file (this time copied to c:/)
		FileSystemResource res = new FileSystemResource(new File("c:/Sample.jpg"));
		helper.addInline("identifier1234", res);

		sender.send(message);




	}
}

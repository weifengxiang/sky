package org.sky.sys.utils;

import java.io.File;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
/**
 * 发送邮件攻击
 * @author Administrator
 *
 */
public class MailMessageUtils {
	/**
	 * 发送文本
	 */
	public static void sentText(){
		JavaMailSenderImpl senderImpl = new JavaMailSenderImpl();
		// 设定mail server
		senderImpl.setHost("smtp.163.com");
		// 建立邮件消息
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		// 设置收件人，寄件人 用数组发送多个邮件
		String[] array = new String[] {"1094590717@qq.com","87430997@qq.com","442695429@qq.com"};
		mailMessage.setTo(array);
		//mailMessage.setTo("442695429@qq.com ");
		mailMessage.setFrom("weifengxiang911@163.com");
		mailMessage.setSubject("老马真帅 ");
		mailMessage.setText("大拿真2");

		senderImpl.setUsername("weifengxiang911"); // 根据自己的情况,设置username
		senderImpl.setPassword("******"); // 根据自己的情况, 设置password

		Properties prop = new Properties();
		prop.put("mail.smtp.auth", "true"); // 将这个参数设为true，让服务器进行认证,认证用户名和密码是否正确
		prop.put("mail.smtp.timeout", "25000");
		senderImpl.setJavaMailProperties(prop);
		// 发送邮件
		senderImpl.send(mailMessage);
		System.out.println("邮件发送成功..");
	}
	public static void sendHtml() throws Exception{
		JavaMailSenderImpl senderImpl = new JavaMailSenderImpl();
		// 设定mail server
		senderImpl.setHost("smtp.163.com");
		// 建立邮件消息,发送简单邮件和html邮件的区别
		MimeMessage mailMessage = senderImpl.createMimeMessage();
		MimeMessageHelper messageHelper = new MimeMessageHelper(mailMessage);
		// 设置收件人，寄件人
		messageHelper.setTo("442695429@qq.com");
		messageHelper.setFrom("weifengxiang911@163.com");
		messageHelper.setSubject("测试HTML邮件！");
		// true 表示启动HTML格式的邮件
		messageHelper
				.setText(
						"<html><head></head><body><h1>hello!!spring html Mail</h1></body></html>",
						true);
		senderImpl.setUsername("weifengxiang911"); // 根据自己的情况,设置username
		senderImpl.setPassword("******"); // 根据自己的情况, 设置password
		Properties prop = new Properties();
		prop.put("mail.smtp.auth", "true"); // 将这个参数设为true，让服务器进行认证,认证用户名和密码是否正确
		prop.put("mail.smtp.timeout", "25000");
		senderImpl.setJavaMailProperties(prop);
		// 发送邮件
		senderImpl.send(mailMessage);

		System.out.println("邮件发送成功..");
	}
	public static void sendImage() throws Exception{
		JavaMailSenderImpl senderImpl = new JavaMailSenderImpl();

		// 设定mail server
		senderImpl.setHost("smtp.163.com");
		// 建立邮件消息,发送简单邮件和html邮件的区别
		MimeMessage mailMessage = senderImpl.createMimeMessage();
		// 注意这里的boolean,等于真的时候才能嵌套图片，在构建MimeMessageHelper时候，所给定的值是true表示启用，
		// multipart模式
		MimeMessageHelper messageHelper = new MimeMessageHelper(mailMessage,
				true);
		// 设置收件人，寄件人
		messageHelper.setTo("1094590717@qq.com");
		messageHelper.setFrom("weifengxiang911@163.com");
		messageHelper.setSubject("大拿真牛逼，少了个jar都能看出来");
		// true 表示启动HTML格式的邮件
		messageHelper.setText(
				"<html><head></head><body><h1>hello!!spring image html mail</h1>"
						+ "<img src=\"cid:aaa\"/></body></html>", true);

		FileSystemResource img = new FileSystemResource(new File("g:/20160422204734.png"));

		messageHelper.addInline("aaa", img);

		senderImpl.setUsername("weifengxiang911"); // 根据自己的情况,设置username
		senderImpl.setPassword("******"); // 根据自己的情况, 设置password
		Properties prop = new Properties();
		prop.put("mail.smtp.auth", "true"); // 将这个参数设为true，让服务器进行认证,认证用户名和密码是否正确
		prop.put("mail.smtp.timeout", "25000");
		senderImpl.setJavaMailProperties(prop);

		// 发送邮件
		senderImpl.send(mailMessage);

		System.out.println("邮件发送成功..");
	}
	public static void sendFile() throws Exception{
		JavaMailSenderImpl senderImpl = new JavaMailSenderImpl();
		// 设定mail server
		senderImpl.setHost("smtp.163.com");
		// 建立邮件消息,发送简单邮件和html邮件的区别
		MimeMessage mailMessage = senderImpl.createMimeMessage();
		// 注意这里的boolean,等于真的时候才能嵌套图片，在构建MimeMessageHelper时候，所给定的值是true表示启用，
		// multipart模式 为true时发送附件 可以设置html格式
		MimeMessageHelper messageHelper = new MimeMessageHelper(mailMessage,
				true, "utf-8");
		// 设置收件人，寄件人
		String[] array = new String[] {"1094590717@qq.com","87430997@qq.com","442695429@qq.com"};
		messageHelper.setTo(array);
		//messageHelper.setTo("toMail@sina.com");
		messageHelper.setFrom("weifengxiang911@163.com");
		messageHelper.setSubject("大拿真牛!");
		// true 表示启动HTML格式的邮件
		messageHelper.setText(
				"<html><head></head><body><h1>你好：附件中有学习资料！</h1></body></html>",
				true);
		FileSystemResource file = new FileSystemResource(
				new File("g:/nginx.zip"));
		// 这里的方法调用和插入图片是不同的。
		messageHelper.addAttachment("学习.rar", file);

		senderImpl.setUsername("weifengxiang911"); // 根据自己的情况,设置username
		senderImpl.setPassword("******"); // 根据自己的情况, 设置password
		Properties prop = new Properties();
		prop.put("mail.smtp.auth", "true"); // 将这个参数设为true，让服务器进行认证,认证用户名和密码是否正确
		prop.put("mail.smtp.timeout", "25000");
		senderImpl.setJavaMailProperties(prop);
		// 发送邮件
		senderImpl.send(mailMessage);
		System.out.println("邮件发送成功..");
	}
	public static void main(String args[]){
		try {
			sendHtml();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

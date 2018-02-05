package org.sky.sys.service;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.sky.sys.utils.NumberUtils;
import org.springframework.security.crypto.codec.Hex;
import org.springframework.stereotype.Service;
/**
 * 
* @Title: SecurityCodeService.java 
* @Package org.sky.sys.service 
* @Description: TODO(验证码Service) 
* @author 位峰祥   
* @date 2016-6-2 下午10:46:56 
* @version V1.0
 */
@Service
public class SecurityCodeService {
	private final static String[] ARR_CN0 = { "+", "-", "x" };
	private final static String[] ARR_CN1 = { "加", "减", "乘" };
	private final static String[] ARR_CN2 = { "加上", "减去", "乘以" };

	public byte[] createSecurityCode(HttpServletRequest request) {
		byte[] resbyte = null;
		Random random = new Random();
		// 定义数组存放加减乘除四个运算符

		// 生成100以内的随机整数num1
		int num1 = random.nextInt(10);
		// 生成一个0-4之间的随机整数operate
		int operate = random.nextInt(3);
		// 生成10以内的随机整数num1
		int num2 = random.nextInt(10);

		// 运算结果
		int result = 0;
		// 假定position值0/1/2分别代表"+","-","*"，计算前面操作数的运算结果
		switch (operate) {
		case 0:
			result = num1 + num2;
			break;
		case 1:
			result = num1 - num2;
			break;
		case 2:
			result = num1 * num2;
			break;
		default:
			result = num1 + num2;
		}
		// 将生成的验证码值(即运算结果的值)放到session中，以便于后台做验证。
		HttpSession session = request.getSession();
		/**
		   * 
		   * 
		   */
		// result=1;
		String resstr = result + "";
		MessageDigest md;
		try {
			md = MessageDigest.getInstance("MD5");
			byte[] thedigest = md.digest(resstr.getBytes());
			session.setAttribute("securitycode",
					new String(Hex.encode(thedigest)));
			session.setAttribute("sectime", System.currentTimeMillis());
		} catch (NoSuchAlgorithmException e1) {
			e1.printStackTrace();
		}
		int width = 120, height = 25;
		// 创建BufferedImage对象，设置图片的长度宽度和色彩。
		BufferedImage image = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		// 取得Graphics对象，用来绘制图片
		Graphics g = image.getGraphics();
		// 绘制图片背景和文字,释放Graphics对象所占用的资源。
		g.setColor(getRandColor(200, 250));

		// 设置内容生成的位置
		g.fillRect(0, 0, width, height);
		// 设置内容的字体和大小 PLAIN
		g.setFont(new Font("楷体", Font.BOLD,15));

		// 设置内容的颜色：主要为生成图片背景的线条
		g.setColor(getRandColor(160, 200));

		// 图片背景上随机生成155条线条，避免通过图片识别破解验证码
		for (int i = 0; i < 300; i++) {
			int x = random.nextInt(width);
			int y = random.nextInt(height);
			int xl = random.nextInt(12);
			int yl = random.nextInt(12);
			g.drawLine(x, y, x + xl, y + yl);
		}
		// 构造运算表达式
		String content = getContent(num1, operate, num2);
		// 设置写运算表达的颜色
		g.setColor(new Color(20 + random.nextInt(110),
				20 + random.nextInt(110), 20 + random.nextInt(110)));
		// 在指定位置绘制指定内容（即运算表达式）
		g.drawString(content, 5, 18);
		// 释放此图形的上下文以及它使用的所有系统资源，类似于关闭流
		g.dispose();
		// 通过ImageIO对象的write静态方法将图片输出。
		try {
			ImageIO.write(image, "JPEG", os);
			resbyte = os.toByteArray();
			os.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return resbyte;
	}

	/**
	 * 生成随机颜色
	 * 
	 * @param fc
	 * @param bc
	 * @return
	 */
	public Color getRandColor(int fc, int bc) {
		Random random = new Random();
		if (fc > 255)
			fc = 255;
		if (bc > 255)
			bc = 255;
		int r = fc + random.nextInt(bc - fc);
		int g = fc + random.nextInt(bc - fc);
		int b = fc + random.nextInt(bc - fc);
		return new Color(r, g, b);
	}

	public String getContent(int a, int opt, int b) {
		Random random = new Random();
		int res = random.nextInt(3);// 0,1,2随机数
		int index = random.nextInt(3);// 0,1,2
		String[] opttype = null;
		if (0 == index) {
			opttype = ARR_CN0;
		} else if (1 == index) {
			opttype = ARR_CN1;
		} else {
			opttype = ARR_CN2;
		}
		if (0 == res) {
			return a + "" + opttype[opt] + "" + b + "等于几";
		} else if (1 == res) {
			return NumberUtils.numToUpper1(a) + "" + opttype[opt] + ""
					+ NumberUtils.numToUpper1(b) + "等于几";
		} else {
			return NumberUtils.numToUpper2(a) + "" + opttype[opt] + ""
					+ NumberUtils.numToUpper2(b) + "等于几";
		}

	}
}

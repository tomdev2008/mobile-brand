package com.xiu.mobile.portal.controller;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/validateCode")
public class ImgVerifyCodeController extends BaseController {
	// 解决java.lang.NoClassDefFoundError: Could not initialize class sun.awt.X11GraphicsEnvironment
	static{
		// Headless模式虽然不是我们愿意见到的，但事实上我们却常常需要在该模式下工作，尤其是服务器端程序开发者。
		// 因为服务器（如提供Web服务的主机）往往可能缺少前述设备，但又需要使用他们提供的功能，生成相应的数据，以提供给客户端（如浏览器所在的配有相关的显示设备、键盘和鼠标的主机）。
        System.setProperty("java.awt.headless", "true");
    }

	@RequestMapping("/getValidateImg")
	public void getValidateImg(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		// 设置页面不缓存
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);

		// 在内存中创建图象
		int width = 210, height = 70;
		BufferedImage image = new BufferedImage(width, height,BufferedImage.TYPE_INT_RGB);

		// 获取图形上下文
		Graphics g = image.getGraphics();

		// 生成随机类
		Random random = new Random();

		// 设定背景色
		g.setColor(Color.getColor("#FFFFFF"));
		g.fillRect(0, 0, width, height);

		// 设定字体
		g.setFont(new Font("Times New Roman", Font.PLAIN, 59));

		// 画边框
		g.setColor(new Color(255, 255, 255));
		g.drawRect(0, 0, width - 1, height - 1);

		// 随机产生155条干扰线，使图象中的认证码不易被其它程序探测到
		g.setColor(getRandColor(160, 200));
		for (int i = 0; i < 155; i++) {
			int x = random.nextInt(width);
			int y = random.nextInt(height);
			int xl = random.nextInt(22);
			int yl = random.nextInt(22);
			g.drawLine(x, y, x + xl, y + yl);
		}

		// 取随机产生的认证码(4位数字)
		Random r = new Random();
		String sRand = "";
		//String strLetters = "abcdefghjkmnpqrstuvwxyzABCDEFGHJKMNPQRSTUVWXYZ23456789";
		String strLetters = "0123456789";
		for (int i = 0; i < 4; i++) {
			int number = r.nextInt(strLetters.length());
			String rand = strLetters.substring(number, number + 1);
			sRand += rand;
			// 将认证码显示到图象中
			g.setColor(new Color(20 + random.nextInt(79), 20 + random
					.nextInt(79), 20 + random.nextInt(79)));
			// 调用函数出来的颜色相同，可能是因为种子太接近，所以只能直接生成
			g.drawString(rand, 53 * i + 10, 55);
		}

		// 将认证码存入SESSION
		request.getSession().setAttribute("validateImage", sRand.toLowerCase());
		// 验证码过期为1200秒
		request.getSession().setMaxInactiveInterval(1200);

		// 图象生效
		g.dispose();

		// 输出图象到页面
		ImageIO.write(image, "JPEG", response.getOutputStream());
	}

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

}

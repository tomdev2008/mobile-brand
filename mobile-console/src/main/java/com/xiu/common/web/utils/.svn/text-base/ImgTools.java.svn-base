package com.xiu.common.web.utils;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.io.File;

import javax.imageio.ImageIO;

import com.xiu.common.web.view.PictureOfhead;


/**
 * @author huacailiang 2015-04-01 裁剪缩放图片
 *
 */
public class ImgTools {

	public static void main(String[] args) {
		String readFormats[] = ImageIO.getReaderFormatNames();
		String writeFormats[] = ImageIO.getWriterFormatNames();
		System.out.println("Readers: " + Arrays.asList(readFormats));
		System.out.println("Writers: " + Arrays.asList(writeFormats));

	//裁剪
	/*	ImgTools.drawImage(169, 106, 102, 62, 
				new File("C:/Users/hua/Pictures/r_xx2.jpg"), 
				new File("C:/Users/hua/Desktop/r_xx2.jpg"));*/
	//缩放	
		ImgTools.drawImage(200, 200,
				new File("C:/Users/hua/Desktop/r_xx2.jpg"), 
				new File("C:/Users/hua/Desktop/r_xx3.jpg"));
	}
	
	/**
	 * 按规格裁剪图片
	 * @param x	X轴
	 * @param y Y轴
	 * @param width 宽度
	 * @param height 高度
	 * @param imgIn 输入图片
	 * @param imgOut 输出图片
	 */
	public static void drawImage(int x, int y, int width, int height, File imgIn, File imgOut) {
		try {
			//读取图片
			BufferedImage bufferedImage = ImageIO.read(imgIn);
			// 初始化输出图片    
	        BufferedImage output = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);    
	        // 裁剪并绘图    
	        output.createGraphics().drawImage(bufferedImage.getSubimage(x, y, width, height), null, null);
			//输出图片
			ImageIO.write(output, "JPG", imgOut);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 获取图片原型
	 * @return
	 */
	public static PictureOfhead drawImageAntetype(File imgIn, File imgOut){
		PictureOfhead poh = new PictureOfhead();
		try{
			//读取图片
			BufferedImage bufferedImage = ImageIO.read(imgIn);
			int inHeigth = bufferedImage.getHeight();
			int inWidth = bufferedImage.getWidth();
			poh.setWidth(inWidth);
			poh.setHeight(inHeigth);
		}catch(Exception e){
			e.printStackTrace();
		}
		return poh;
	}
	/**
	 * 按上传图片比例缩放图片
	 * @param width
	 * @param height
	 * @param imgIn
	 * @param imgOut
	 */
	public static PictureOfhead drawImage(int width, int height, File imgIn, File imgOut) {
		PictureOfhead poh = new PictureOfhead();
		try {
			//读取图片
			BufferedImage bufferedImage = ImageIO.read(imgIn);
			int inHeigth = bufferedImage.getHeight();
			int inWidth = bufferedImage.getWidth();
			BufferedImage output = null;
			
			if(inHeigth > height || inWidth > width){
				if(inHeigth >= inWidth){
					int outWidth =  (inWidth * height) / inHeigth;
					// 初始化输出图片    
					output = new BufferedImage(outWidth, height, BufferedImage.TYPE_INT_RGB);
					poh.setWidth(outWidth);
					poh.setHeight(height);
				}else{
					int outHeigth = (inHeigth * width) / inWidth;
					// 初始化输出图片    
					output = new BufferedImage(width, outHeigth, BufferedImage.TYPE_INT_RGB);
					poh.setWidth(width);
					poh.setHeight(outHeigth);
				}
			}else{
				output = new BufferedImage(inWidth, inHeigth, BufferedImage.TYPE_INT_RGB);
				poh.setWidth(inWidth);
				poh.setHeight(inHeigth);
			}
			
	        // 重新绘图    
	        Image image = bufferedImage.getScaledInstance(output.getWidth(), output.getHeight(), output.getType());    
	        output.createGraphics().drawImage(image, null, null);   
			//输出图片
			ImageIO.write(output, "JPG", imgOut);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return poh;
	}

}

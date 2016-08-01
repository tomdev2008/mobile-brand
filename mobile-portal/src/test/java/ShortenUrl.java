

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import net.sf.json.JSONObject;

import com.xiu.mobile.portal.common.util.HttpUtils;

public class ShortenUrl {

	private static final boolean DEBUG = true;

	public static void main(String[] args) {
		try{
			File file = new File("F:/ten/888.jpg");
			 BufferedImage buff = ImageIO.read(file); 
			 //图片的宽
			 System.out.println("宽："+buff.getWidth()*1L);
			 //图片高
			 System.out.println("高："+buff.getHeight()*1L);
			 //大小
			 System.out.println("大小："+file.length());
		}catch(Exception e){
			
		}
		
		
//		String dd=getBaiduApiShortURL("http://m.xiu.com/payforothers/index.html?order=asdlfjkw2egojwelghqwlegjqlwkegjnlqwejlgjwlkgjlaskjdglak");
//	    System.out.println(dd+"|asdfasdf");
	}
	  
	  public static String getBaiduApiShortURL(String URL) {
	        String shortURL = null;
	        String requestUrl = "http://dwz.cn/create.php";
	        Map params=new HashMap();
	        params.put("url", URL);
	        try {
				String str = HttpUtils.postMethod(requestUrl,params,"utf-8");
				JSONObject jsonObject=JSONObject.fromObject(str);
				Object tinyurl=jsonObject.get("tinyurl");
				if(tinyurl!=null){
					shortURL=tinyurl.toString();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
	 
	        return shortURL;
	  }
	  
	  

}

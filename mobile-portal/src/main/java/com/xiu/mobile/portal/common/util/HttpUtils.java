package com.xiu.mobile.portal.common.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import net.sf.json.JSONArray;
import net.sf.json.JSONNull;
import net.sf.json.JSONObject;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.SimpleHttpConnectionManager;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

import com.xiu.mobile.portal.model.PayReqVO;
import com.xiu.mobile.portal.service.impl.OrderServiceImpl;


/**
 * @author MeiTian
 * @date：Dec 6, 2010
 */
public abstract class HttpUtils {
    private static int TIME_OUT_CONNECTION = 30000; // 30秒 设置连接超时时间(单位毫秒)
    private static int TIME_OUT_SOTIMEOUT = 120000; // 120秒 设置读数据超时时间(单位毫秒)

    public static String postMethod(String url, NameValuePair[] params, String charset) {
        String result = "";
        HttpClient httpClient = new HttpClient();

        httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(TIME_OUT_CONNECTION);
        httpClient.getHttpConnectionManager().getParams().setSoTimeout(TIME_OUT_SOTIMEOUT);

        PostMethod post = new PostMethod(url);
        try {
            post.setRequestHeader("Content-Type", "text/xml; charset=" + charset);
            post.addParameters(params);
            httpClient.executeMethod(post);
            if (post.getStatusCode() != HttpStatus.SC_OK) {
                post.releaseConnection();
                return result;
            }
            InputStream resStream = post.getResponseBodyAsStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(resStream, charset));
            StringBuilder resBuffer = new StringBuilder();
            String resTemp = "";
            while ((resTemp = br.readLine()) != null) {
                resBuffer.append(resTemp);
            }
            result = resBuffer.toString();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (post != null) {
                post.releaseConnection();
                ((SimpleHttpConnectionManager) httpClient.getHttpConnectionManager()).shutdown();
            }
        }
        return result;
    }

    @SuppressWarnings("deprecation")
    public static String postMethod(String url, String xml) throws IOException {
        String result = "";
        HttpClient httpClient = new HttpClient();
        httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(TIME_OUT_CONNECTION);
        httpClient.getHttpConnectionManager().getParams().setSoTimeout(TIME_OUT_SOTIMEOUT);

        PostMethod postMethod = new PostMethod(url);
        try {
            postMethod.setRequestHeader("Content-Type", "text/xml; charset=utf-8");
            postMethod.setRequestBody(xml);
            httpClient.executeMethod(postMethod);
            if (postMethod.getStatusCode() != HttpStatus.SC_OK) {
                postMethod.releaseConnection();
                return result;
            }
            InputStream resStream = postMethod.getResponseBodyAsStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(resStream, "utf-8"));
            StringBuilder resBuffer = new StringBuilder();
            String resTemp = "";
            while ((resTemp = br.readLine()) != null) {
                resBuffer.append(resTemp);
            }
            result = resBuffer.toString();
        } catch (HttpException e) {
            throw e;
        } catch (IOException e) {
            throw e;
        } finally {
            if (postMethod != null) {
                postMethod.releaseConnection();
                ((SimpleHttpConnectionManager) httpClient.getHttpConnectionManager()).shutdown();
            }
        }
        return result;
    }

    public static String postMethod(String url, Map<String, String> paramMap) throws IOException {
        PostMethod postMethod = new PostMethod(url);
        for (Map.Entry<String, String> entry : paramMap.entrySet()) {
            postMethod.setParameter(entry.getKey(), entry.getValue());
        }
        HttpClient httpClient = new HttpClient();
        httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(TIME_OUT_CONNECTION);
        httpClient.getHttpConnectionManager().getParams().setSoTimeout(TIME_OUT_SOTIMEOUT);

        try {
            httpClient.executeMethod(postMethod);
            httpClient.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "gbk");
            return postMethod.getResponseBodyAsString();
        } catch (HttpException e) {
            throw e;
        } catch (IOException e) {
            throw e;
        } finally {
            if (postMethod != null) {
                postMethod.releaseConnection();
                ((SimpleHttpConnectionManager) httpClient.getHttpConnectionManager()).shutdown();
            }
        }
    }

    public static String getMethod(String url) throws IOException {
        GetMethod getMethod = new GetMethod(url);
        
        HttpClient httpClient = new HttpClient();
        httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(TIME_OUT_CONNECTION);
        httpClient.getHttpConnectionManager().getParams().setSoTimeout(TIME_OUT_SOTIMEOUT);

        try {
            httpClient.executeMethod(getMethod);
            httpClient.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "gbk");
            return getMethod.getResponseBodyAsString();
        } catch (HttpException e) {
            throw e;
        } catch (IOException e) {
            throw e;
        } finally {
            if (getMethod != null) {
            	getMethod.releaseConnection();
                ((SimpleHttpConnectionManager) httpClient.getHttpConnectionManager()).shutdown();
            }
        }
    }
    
    public static String getMethod(String url, int connectTime, int readDataTime) throws IOException {
        GetMethod getMethod = new GetMethod(url);
        
        HttpClient httpClient = new HttpClient();
        httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(connectTime);
        httpClient.getHttpConnectionManager().getParams().setSoTimeout(readDataTime);

        try {
            httpClient.executeMethod(getMethod);
            httpClient.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "gbk");
            return getMethod.getResponseBodyAsString();
        } catch (HttpException e) {
            throw e;
        } catch (IOException e) {
            throw e;
        } finally {
            if (getMethod != null) {
            	getMethod.releaseConnection();
                ((SimpleHttpConnectionManager) httpClient.getHttpConnectionManager()).shutdown();
            }
        }
    }
    
    public static String getMethod(String url, String charset) throws IOException {
        GetMethod getMethod = new GetMethod(url);
        
        HttpClient httpClient = new HttpClient();
        httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(TIME_OUT_CONNECTION);
        httpClient.getHttpConnectionManager().getParams().setSoTimeout(TIME_OUT_SOTIMEOUT);

        try {
            httpClient.executeMethod(getMethod);
            httpClient.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, charset);
            return getMethod.getResponseBodyAsString();
        } catch (HttpException e) {
            throw e;
        } catch (IOException e) {
            throw e;
        } finally {
            if (getMethod != null) {
            	getMethod.releaseConnection();
                ((SimpleHttpConnectionManager) httpClient.getHttpConnectionManager()).shutdown();
            }
        }
    }
    
    public static String postMethod(String url, Map<String, String> paramMap, String charset) throws IOException {
        PostMethod postMethod = new PostMethod(url);
        for (Map.Entry<String, String> entry : paramMap.entrySet()) {
            postMethod.setParameter(entry.getKey(), entry.getValue());
        }
        HttpClient httpClient = new HttpClient();
        httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(TIME_OUT_CONNECTION);
        httpClient.getHttpConnectionManager().getParams().setSoTimeout(TIME_OUT_SOTIMEOUT);

        try {
            httpClient.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, charset);
            httpClient.executeMethod(postMethod);
            String str = postMethod.getResponseBodyAsString();
            return str;
        } catch (HttpException e) {
            throw e;
        } catch (IOException e) {
            throw e;
        } finally {
            if (postMethod != null) {
                postMethod.releaseConnection();
                ((SimpleHttpConnectionManager) httpClient.getHttpConnectionManager()).shutdown();
            }
        }
    }

    public static String postMethod(String strUrl) throws Exception {
        SSLContext ctx = SSLContext.getInstance("TLS");
        ctx.init(null, trustAllCerts, null);
        SSLSocketFactory factory = ctx.getSocketFactory();

        URL url = new URL(strUrl);
        HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
        conn.setSSLSocketFactory(factory);
        conn.setRequestMethod("POST");
        conn.setDoInput(true);
        conn.setDoOutput(true);
        conn.setConnectTimeout(TIME_OUT_CONNECTION);
        conn.setReadTimeout(TIME_OUT_SOTIMEOUT);

        OutputStream os = conn.getOutputStream();
        os.close();

        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String line;
        String xmlResult = "";
        while ((line = br.readLine()) != null) {
            xmlResult += line;
        }
        br.close();
        return xmlResult;
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
			if(tinyurl!=null&&tinyurl.toString().indexOf("dwz")>0){
				shortURL=tinyurl.toString();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
 
        return shortURL;
  }
    
    

    /**
     * 关键在这信任所有证书
     */
    private static TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }

        @Override
        public void checkClientTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
            return;

        }

        @Override
        public void checkServerTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
            return;

        }
    } };
    
    
    
    public static void main(String[] args) {
    	String resultStr="{'head':{'code':'0','desc':'SUCCESS'},'data':[{'goodsId':'42746150','goodsSn':'3Q015752','goodName':'925银镀浅金 西瓜红色小雏菊锆石珐琅项链','xiuPrice':522.0,'mkPrice':580.0,'finalPrice':522.0,'disCount':null,'img':'/upload/goods20160301/3Q015752/3Q0157520001','brandNameEn':'JUJU Palais Royal','brandNameCn':'sdfasdf','onSale':1},{'goodsId':'42746174','goodsSn':'3Q015764','goodName':'925银镀浅金 白色小雏菊锆石珐琅项链','xiuPrice':522.0,'mkPrice':580.0,'finalPrice':522.0,'disCount':null,'img':'/upload/goods20160301/3Q015764/3Q0157640001','brandNameEn':'JUJU Palais Royal','brandNameCn':null,'onSale':1},{'goodsId':'42746061','goodsSn':'3Q015753','goodName':'925银镀白金 浅蓝色小雏菊锆石珐琅项链','xiuPrice':522.0,'mkPrice':580.0,'finalPrice':522.0,'disCount':null,'img':'/upload/goods20160301/3Q015753/3Q0157530001','brandNameEn':'JUJU Palais Royal','brandNameCn':null,'onSale':1},{'goodsId':'42746073','goodsSn':'3Q015768','goodName':'925银镀玫瑰金 白色小雏菊锆石珐琅项链','xiuPrice':522.0,'mkPrice':580.0,'finalPrice':522.0,'disCount':null,'img':'/upload/goods20160301/3Q015768/3Q0157680001','brandNameEn':'JUJU Palais Royal','brandNameCn':null,'onSale':1},{'goodsId':'42746069','goodsSn':'3Q015756','goodName':'925银镀白金 白色玫瑰花水钻珐琅项链','xiuPrice':544.0,'mkPrice':680.0,'finalPrice':544.0,'disCount':null,'img':'/upload/goods20160301/3Q015756/3Q0157560001','brandNameEn':'JUJU Palais Royal','brandNameCn':null,'onSale':1},{'goodsId':'42746032','goodsSn':'3Q015750','goodName':'925银镀浅金 白色小玫瑰锆石珐琅耳钉','xiuPrice':342.0,'mkPrice':380.0,'finalPrice':342.0,'disCount':null,'img':'/upload/goods20160301/3Q015750/3Q0157500001','brandNameEn':'JUJU Palais Royal','brandNameCn':'','onSale':1},{'goodsId':'42746159','goodsSn':'3Q015755','goodName':'925银镀玫瑰金 白色小玫瑰锆石珐琅耳钉','xiuPrice':342.0,'mkPrice':380.0,'finalPrice':342.0,'disCount':null,'img':'/upload/goods20160301/3Q015755/3Q0157550001','brandNameEn':'JUJU Palais Royal','brandNameCn':'','onSale':1},{'goodsId':'42746094','goodsSn':'3Q015754','goodName':'925银镀白金 黑色小玫瑰锆石珐琅耳钉','xiuPrice':342.0,'mkPrice':380.0,'finalPrice':342.0,'disCount':null,'img':'/upload/goods20160301/3Q015754/3Q0157540001','brandNameEn':'JUJU Palais Royal','brandNameCn':'','onSale':1},{'goodsId':'42746105','goodsSn':'3Q015745','goodName':'925银镀玫瑰金 白色一瓣丁香花锆石珐琅耳钉','xiuPrice':342.0,'mkPrice':380.0,'finalPrice':342.0,'disCount':null,'img':'/upload/goods20160301/3Q015745/3Q0157450001','brandNameEn':'JUJU Palais Royal','brandNameCn':'','onSale':1},{'goodsId':'42746067','goodsSn':'3Q015741','goodName':'925银镀浅金 白色一瓣丁香花锆石珐琅耳钉','xiuPrice':342.0,'mkPrice':380.0,'finalPrice':342.0,'disCount':null,'img':'/upload/goods20160301/3Q015741/3Q0157410001','brandNameEn':'JUJU Palais Royal','brandNameCn':'','onSale':1},{'goodsId':'42746172','goodsSn':'3Q015767','goodName':'925银镀白金 黑色一瓣丁香花锆石珐琅耳钉','xiuPrice':342.0,'mkPrice':380.0,'finalPrice':342.0,'disCount':null,'img':'/upload/goods20160301/3Q015767/3Q0157670001','brandNameEn':'JUJU Palais Royal','brandNameCn':'','onSale':1},{'goodsId':'42746071','goodsSn':'3Q015762','goodName':'925银镀金 白色两瓣丁香花锆石珐琅耳钉','xiuPrice':522.0,'mkPrice':580.0,'finalPrice':522.0,'disCount':null,'img':'/upload/goods20160301/3Q015762/3Q0157620001','brandNameEn':'JUJU Palais Royal','brandNameCn':'','onSale':1},{'goodsId':'42746181','goodsSn':'3Q015749','goodName':'925银镀玫瑰金 白色两瓣丁香花锆石珐琅耳钉','xiuPrice':522.0,'mkPrice':580.0,'finalPrice':522.0,'disCount':null,'img':'/upload/goods20160301/3Q015749/3Q0157490001','brandNameEn':'JUJU Palais Royal','brandNameCn':'','onSale':1},{'goodsId':'42746030','goodsSn':'3Q015744','goodName':'925银镀白金 黑色两瓣丁香花锆石珐琅耳钉','xiuPrice':522.0,'mkPrice':580.0,'finalPrice':522.0,'disCount':null,'img':'/upload/goods20160301/3Q015744/3Q0157440001','brandNameEn':'JUJU Palais Royal','brandNameCn':'','onSale':1},{'goodsId':'42746098','goodsSn':'3Q015763','goodName':'925银镀金 红色两瓣丁香花锆石珐琅耳钉','xiuPrice':522.0,'mkPrice':580.0,'finalPrice':522.0,'disCount':null,'img':'/upload/goods20160301/3Q015763/3Q0157630001','brandNameEn':'JUJU Palais Royal','brandNameCn':'','onSale':1},{'goodsId':'42746184','goodsSn':'3Q015743','goodName':'925银镀白金 黑色灵蛇水钻珐琅耳环','xiuPrice':544.0,'mkPrice':680.0,'finalPrice':544.0,'disCount':null,'img':'/upload/goods20160301/3Q015743/3Q0157430001','brandNameEn':'JUJU Palais Royal','brandNameCn':'','onSale':1},{'goodsId':'42746059','goodsSn':'3Q015747','goodName':'925银镀白金 白色灵蛇水钻珐琅耳环','xiuPrice':544.0,'mkPrice':680.0,'finalPrice':544.0,'disCount':null,'img':'/upload/goods20160301/3Q015747/3Q0157470001','brandNameEn':'JUJU Palais Royal','brandNameCn':'','onSale':1},{'goodsId':'42746053','goodsSn':'3Q015765','goodName':'925银镀白金 银彩色灵蛇水钻珐琅耳环','xiuPrice':544.0,'mkPrice':680.0,'finalPrice':544.0,'disCount':null,'img':'/upload/goods20160301/3Q015765/3Q0157650001','brandNameEn':'JUJU Palais Royal','brandNameCn':'','onSale':1},{'goodsId':'42746086','goodsSn':'3Q015760','goodName':'925银镀金 金彩色灵蛇水钻珐琅耳环','xiuPrice':544.0,'mkPrice':680.0,'finalPrice':544.0,'disCount':null,'img':'/upload/goods20160301/3Q015760/3Q0157600001','brandNameEn':'JUJU Palais Royal','brandNameCn':'','onSale':1},{'goodsId':'42746162','goodsSn':'3Q015746','goodName':'925银镀白金 白色三瓣丁香花锆石珐琅耳环','xiuPrice':882.0,'mkPrice':980.0,'finalPrice':882.0,'disCount':null,'img':'/upload/goods20160301/3Q015746/3Q0157460001','brandNameEn':'JUJU Palais Royal','brandNameCn':'','onSale':1},{'goodsId':'42746179','goodsSn':'3Q015761','goodName':'925银镀玫瑰金 白色三瓣丁香花锆石珐琅耳环','xiuPrice':882.0,'mkPrice':980.0,'finalPrice':882.0,'disCount':null,'img':'/upload/goods20160301/3Q015761/3Q0157610001','brandNameEn':'JUJU Palais Royal','brandNameCn':'','onSale':1},{'goodsId':'42746101','goodsSn':'3Q015766','goodName':'925银镀金 红白拼色三瓣丁香花锆石珐琅耳环','xiuPrice':882.0,'mkPrice':980.0,'finalPrice':882.0,'disCount':null,'img':'/upload/goods20160301/3Q015766/3Q0157660001','brandNameEn':'JUJU Palais Royal','brandNameCn':'','onSale':1},{'goodsId':'42746107','goodsSn':'3Q015757','goodName':'925银镀浅金 西瓜红色小雏菊锆石珐琅开口戒指','xiuPrice':522.0,'mkPrice':580.0,'finalPrice':522.0,'disCount':null,'img':'/upload/goods20160301/3Q015757/3Q0157570001','brandNameEn':'JUJU Palais Royal','brandNameCn':'','onSale':1},{'goodsId':'42746154','goodsSn':'3Q015758','goodName':'925银镀白金 白色灵蛇水钻珐琅开口戒指','xiuPrice':544.0,'mkPrice':680.0,'finalPrice':544.0,'disCount':null,'img':'/upload/goods20160301/3Q015758/3Q0157580001','brandNameEn':'JUJU Palais Royal','brandNameCn':'','onSale':1},{'goodsId':'42746103','goodsSn':'3Q015751','goodName':'925银镀白金 银彩色灵蛇水钻珐琅开口戒指','xiuPrice':544.0,'mkPrice':680.0,'finalPrice':544.0,'disCount':null,'img':'/upload/goods20160301/3Q015751/3Q0157510001','brandNameEn':'JUJU Palais Royal','brandNameCn':'','onSale':1},{'goodsId':'42746063','goodsSn':'3Q015759','goodName':'925银镀金 金彩色灵蛇水钻珐琅开口戒指','xiuPrice':544.0,'mkPrice':680.0,'finalPrice':544.0,'disCount':null,'img':'/upload/goods20160301/3Q015759/3Q0157590001','brandNameEn':'JUJU Palais Royal','brandNameCn':'','onSale':1},{'goodsId':'42746090','goodsSn':'3Q015748','goodName':'925银镀金 红白拼色丁香花锆石珐琅开口戒指','xiuPrice':882.0,'mkPrice':980.0,'finalPrice':882.0,'disCount':null,'img':'/upload/goods20160301/3Q015748/3Q0157480001','brandNameEn':'JUJU Palais Royal','brandNameCn':'','onSale':1},{'goodsId':'42746092','goodsSn':'3Q015742','goodName':'925银镀白金 黑白拼色丁香花锆石珐琅开口戒指','xiuPrice':882.0,'mkPrice':980.0,'finalPrice':882.0,'disCount':null,'img':'/upload/goods20160301/3Q015742/3Q0157420001','brandNameEn':'JUJU Palais Royal','brandNameCn':'','onSale':1}]}";
    	
    	JSONObject json = JSONObject.fromObject(resultStr);
		JSONObject head = (JSONObject)json.get("head");
		String code = (String)head.get("code");
		if("0".equals(code)){
			JSONArray resultJsons = (JSONArray) json.get("data");
			int size = resultJsons.size();
			for (int i = 0; i < size; i++) {
				JSONObject jsonobj = (JSONObject) resultJsons.get(i);
				String goodsId = (String) jsonobj.get("goodsId");
				String goodsSn = (String) jsonobj.get("goodsSn");
				String goodName = (String) jsonobj.get("goodName");
				String img = (String) jsonobj.get("img");
				String brandNameEn = "";
				if(jsonobj.containsKey("brandNameEn")&&!(jsonobj.get("brandNameEn") instanceof JSONNull)){
					brandNameEn = (String) jsonobj.get("brandNameEn");
				}
				String brandNameCn = "";
				if(jsonobj.containsKey("brandNameCn")&&!(jsonobj.get("brandNameCn") instanceof JSONNull)){
					 brandNameCn = (String) jsonobj.get("brandNameCn");
				}
				System.out.println("brandNameCn："+brandNameCn);
			}
			System.out.println("2323");
		}
		
	}

}

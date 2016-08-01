package com.xiu.mobile.core.common.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.SimpleHttpConnectionManager;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;


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

}

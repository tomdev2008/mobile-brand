package com.xiu.mobile.wechat.web.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XppDriver;
import com.xiu.mobile.wechat.web.dto.Article;
import com.xiu.mobile.wechat.web.dto.NewsMessage;
import com.xiu.mobile.wechat.web.dto.TextMessage;

/**
 * 
 * 微信消息工具类
 * 
 * @author wangzhenjiang
 * 
 */
public class MessageUtil {

	/**
	 * 解析微信发来的请求（XML）
	 * 
	 * @param request
	 * @return Map<String, String>
	 * @throws IOException
	 * @throws DocumentException
	 */
	public static Map<String, String> parseXml(InputStream inputStream) throws IOException, DocumentException {
		// 将解析结果存储在HashMap中
		Map<String, String> map = new HashMap<String, String>();
		// 读取输入流
		SAXReader reader = new SAXReader();
		Document document = reader.read(inputStream);
		// 得到xml根元素
		Element root = document.getRootElement();
		// 将元素添加到Map中
		addToMap(root, map);
		// 释放资源
		inputStream.close();
		inputStream = null;

		return map;
	}

	@SuppressWarnings("unchecked")
	private static void addToMap(Element e, Map<String, String> map) {
		List<Element> childEles = e.elements();
		if (CollectionUtils.isEmpty(childEles)) {
			if (map.containsKey(e.getName())) {
				String text = map.get(e.getName());
				if (StringUtils.isNotBlank(e.getText())) {
					text = text + "," + e.getText();
				}
				map.put(e.getName(), text);
			} else {
				map.put(e.getName(), e.getText());
			}
			return;
		}

		for (Element element : childEles) {
			addToMap(element, map);
		}
	}

	/**
	 * 文本消息对象转换成xml
	 * 
	 * @param textMessage
	 *            文本消息对象
	 * @return xml
	 */
	public static String messageToXml(TextMessage textMessage) {
		xstream.alias("xml", textMessage.getClass());
		return xstream.toXML(textMessage);
	}

	/**
	 * 图文消息对象转换成xml
	 * 
	 * @param NewsMessage
	 *            图文消息对象
	 * @return xml
	 */
	public static String messageToXml(NewsMessage out) {
		xstream.alias("xml", out.getClass());
		xstream.alias("item", new Article().getClass());
		return xstream.toXML(out);
	}

	/**
	 * 扩展xstream，使其支持CDATA块
	 * 
	 */
	private static XStream xstream = new XStream(new XppDriver() {
		public HierarchicalStreamWriter createWriter(Writer out) {
			return new PrettyPrintWriter(out) {
				protected void writeText(QuickWriter writer, String text) {
					// 对所有xml节点的转换都增加CDATA标记
					writer.write("<![CDATA[" + text + "]]>");
				}
			};
		}
	});

}

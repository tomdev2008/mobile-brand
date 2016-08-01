package com.xiu.common.web.utils;

import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableCellFeatures;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import org.apache.commons.lang.StringUtils;
import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;

/**
 *************************************************************** 
 * <p>
 * 
 * @DESCRIPTION : Excel导出帮助类
 * @AUTHOR : haijun.chen@xiu.com
 * @DATE :2013-07-09 下午3:28:19
 *       </p>
 **************************************************************** 
 */
public class ExcelUtil {

	// 日志
	private static final XLogger LOGGER = XLoggerFactory
			.getXLogger(ExcelUtil.class);

	/**
	 * 获取excel 输入流 列名格式：“列名:Column_Child”
	 * 
	 * @param listCollection
	 *            集合
	 * @param columnGroup
	 *            列名
	 * @param params
	 *            excel的名称
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static boolean downloadStatements(List listCollection,
			String[] columnGroup, OutputStream os, String sheetName, List<String> angerList)
			throws Exception {
		// 导出Excel成功与否的标记
		boolean result = false;
		try {
			// 创建一个workbook对象
			WritableWorkbook workbook;
			workbook = Workbook.createWorkbook(os);
			// 工作单元的名字
			WritableSheet sheet = workbook.createSheet(sheetName, 0);
			// 截取表头名和每列字段
			String[] headNames = new String[columnGroup.length];
			String[] fieldNames = new String[columnGroup.length];
			// 循环取出
			for (int i = 0; i < columnGroup.length; i++) {
				String column = columnGroup[i];
				if(column.indexOf(":") > -1){
					headNames[i] = column.substring(0, column.indexOf(":"));
					fieldNames[i] = column.substring(column.indexOf(":") + 1);
				}
			}
			// 将表头名写入表格
			writeRow(sheet, 0, headNames, null);
			// 向表格内写数据
			if(listCollection != null && listCollection.size() > 0){
				for (int i = 0; i < listCollection.size(); i++) {
					String[] columnValue = new String[columnGroup.length];
					for (int j = 0; j < fieldNames.length; j++) {
						Object value = "";
						if (fieldNames[j] != null && !"".equals(fieldNames[j])) {
							if (listCollection.get(i) instanceof Map<?, ?>) {
								// 解析Map对象
								value = ((Map<String, Object>) listCollection.get(i)).get(fieldNames[j]);
							} else {
								// 解析实体对象
								value = invokeCascadeMethod(listCollection.get(i), fieldNames[j]);// 调用反射方法获取实体值
							}
						}
						columnValue[j] = convertToString(value);
					}
					writeRow(sheet, i + 1, columnValue, angerList);// 向Excel中写入表格内容
				}
			}
			workbook.write();
			workbook.close();
			// 返回成功
			result = true;
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("导出Excel数据异常", e);
		} finally {
			// 关闭输出流
			try {
				if (os != null) {
					os.close();
					os = null;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	/**
	 * 导出多个sheet 的excel
	 * 
	 * @param listCollection
	 *            集合
	 * @param columnGroup
	 *            列名
	 * @param params
	 *            excel的名称
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static boolean downloadStatementsMoreSheet(List<List> listCollections,
			List<String[]> columnGroups, OutputStream os, List<String> sheetNames, List<String> angerList)
			throws Exception {
		// 导出Excel成功与否的标记
		boolean result = false;
		try {
			// 创建一个workbook对象
			WritableWorkbook workbook;
			workbook = Workbook.createWorkbook(os);
			
			for (int i = 0; i < sheetNames.size(); i++) {
				// 工作单元的名字
				WritableSheet sheet = workbook.createSheet(sheetNames.get(i), i);
				// 截取表头名和每列字段
				String[] columnGroup = columnGroups.get(i);
				String[] headNames = new String[columnGroup.length];
				String[] fieldNames = new String[columnGroup.length];
				// 循环取出
				for (int j = 0; j < columnGroup.length; j++) {
					String column = columnGroup[j];
					if(column.indexOf(":") > -1){
						headNames[j] = column.substring(0, column.indexOf(":"));
						fieldNames[j] = column.substring(column.indexOf(":") + 1);
					}
				}
				// 将表头名写入表格
				writeRow(sheet, 0, headNames, null);
				// 向表格内写数据
				if(listCollections != null && listCollections.size() > 0){
					List listCollection = listCollections.get(i);
					if(listCollection == null || listCollection.size() <= 0){
						continue;
					}
					for (int j = 0; j < listCollection.size(); j++) {
						String[] columnValue = new String[columnGroup.length];
						for (int m = 0; m < fieldNames.length; m++) {
							Object value = "";
							if (fieldNames[m] != null && !"".equals(fieldNames[m])) {
								if (listCollection.get(j) instanceof Map<?, ?>) {
									// 解析Map对象
									value = ((Map<String, Object>) listCollection.get(j)).get(fieldNames[m]);
								} else {
									// 解析实体对象
									value = invokeCascadeMethod(listCollection.get(j), fieldNames[m]);// 调用反射方法获取实体值
								}
							}
							columnValue[m] = convertToString(value);
						}
						writeRow(sheet, j + 1, columnValue, angerList);// 向Excel中写入表格内容
					}
				}
			}
			
			workbook.write();
			workbook.close();
			// 返回成功
			result = true;
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("导出Excel数据异常", e);
		} finally {
			// 关闭输出流
			try {
				if (os != null) {
					os.close();
					os = null;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	/**
	 * 导入Excel
	 * @param inputStream
	 * @return
	 */
	public static List<Map<Object, Object>> importStatements(
			InputStream inputStream) {
		
		List<Map<Object, Object>> excellist = new ArrayList<Map<Object, Object>>();
		try {
			// 打开文件
			Workbook workbook = Workbook.getWorkbook(inputStream);
			// 取得第一个sheet
			Sheet sheet = workbook.getSheet(0);
			// 取得行数
			int rows = sheet.getRows();

			for (int i = 1; i < rows; i++) {
				Cell[] cell = sheet.getRow(i);
				Map<Object, Object> map = new HashMap<Object, Object>();
				
				boolean isEmptyRow = true; // 判断是否是空行，是则skip
				for (int j = 0; j < cell.length; j++) {
					String contents = sheet.getCell(j, i).getContents();
					
					if(isEmptyRow && StringUtils.isNotEmpty(contents.trim())) {
						isEmptyRow = false;
					}
					
					map.put(j, contents.trim());
				}
				
				if(isEmptyRow) {
					continue;
				}
				excellist.add(map);
			}
			workbook.close();
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("导入Excel数据异常", e);
		}
		return excellist;
	}
	
	/**
	 * 导入Excel
	 * @param inputStream
	 * @return
	 */
//	public static void importStatements(
//			InputStream inputStream, Map<String, Object> resultMap) {
//		
//		List<Map<Object, Object>> excellist = new ArrayList<Map<Object, Object>>();
//		try {
//			// 打开文件
//			Workbook workbook = Workbook.getWorkbook(inputStream);
//			// 取得第一个sheet
//			Sheet sheet = workbook.getSheet(0);
//			// 取得行数
//			int rows = sheet.getRows();
//
//			for (int i = 1; i < rows; i++) {
//				Cell[] cell = sheet.getRow(i);
//				Map<Object, Object> map = new HashMap<Object, Object>();
//				
//				boolean isEmptyRow = true; // 判断是否是空行，是则skip
//				for (int j = 0; j < cell.length; j++) {
//					String contents = sheet.getCell(j, i).getContents();
//					
//					if(isEmptyRow && StringUtils.isNotEmpty(contents.trim())) {
//						isEmptyRow = false;
//					}
//					
//					map.put(j, contents.trim());
//				}
//				
//				if(isEmptyRow) {
//					continue;
//				}
//				excellist.add(map);
//			}
//			resultMap.put(sheet.getName(), excellist);
//			workbook.close();
//		} catch (Exception e) {
//			e.printStackTrace();
//			LOGGER.error("导入Excel数据异常", e);
//		}
//	}
	
	/**
	 * 导入Exceld，包含多个sheet
	 * @param inputStream
	 * @return
	 */
	public static Map<String, Object> importMoreSheetStatements(
			InputStream inputStream) {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		try {
			// 打开文件
			Workbook workbook = Workbook.getWorkbook(inputStream);
			Sheet[] sheets = workbook.getSheets();
			
			for (int m = 0; m < sheets.length; m++) {
				List<Map<Object, Object>> sheetExcelList = new ArrayList<Map<Object, Object>>();
				// 取得第一个sheet
				Sheet sheet = sheets[m];
				// 取得行数
				int rows = sheet.getRows();
				
				for (int i = 1; i < rows; i++) {
					Cell[] cell = sheet.getRow(i);
					Map<Object, Object> map = new HashMap<Object, Object>();
					
					boolean isEmptyRow = true; // 判断是否是空行，是则skip
					for (int j = 0; j < cell.length; j++) {
						String contents = sheet.getCell(j, i).getContents();
						
						if(isEmptyRow && StringUtils.isNotEmpty(contents.trim())) {
							isEmptyRow = false;
						}
						
						map.put(j, contents.trim());
					}
					
					if(isEmptyRow) {
						continue;
					}
					sheetExcelList.add(map);
				}
				returnMap.put(sheet.getName(), sheetExcelList);
			}
			workbook.close();
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("导入包含多个sheet的Excel数据异常", e);
		}
		return returnMap;
	}


	/**
	 * 转换Object为字符串
	 * 
	 * @param obj
	 * @return
	 */
	public static String convertToString(Object obj) {
		if (obj == null) {
			return "";
		} else if (obj instanceof Date) {
			DateFormat longDateFormat = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");
			return longDateFormat.format(obj);
		} else {
			return obj.toString();
		}
	}

	/**
	 * 递归N层get方法
	 * 
	 * @param owner
	 * @param methodName
	 * @return
	 * @throws Exception
	 */
	public static Object invokeCascadeMethod(Object owner, String methodName)
			throws Exception {
		Object result = null;
		if (methodName.indexOf("_") < 0) {
			result = invokeMethod(owner, "get" + methodName, new Object[0]);
		} else {
			result = invokeCascadeMethod(
					invokeMethod(owner, "get" + methodName.split("_")[0],
							new Object[0]), methodName.substring(methodName
							.indexOf("_") + 1));
		}
		return result;
	}

	/**
	 * 调用方法
	 * 
	 * @param owner
	 * @param methodName
	 * @param args
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static Object invokeMethod(Object owner, String methodName,
			Object[] args) throws Exception {
		Class ownerClass = owner.getClass();
		Class[] argsClass = new Class[args.length];
		for (int i = 0, j = args.length; i < j; i++) {
			argsClass[i] = args[i].getClass();
		}
		Method method = ownerClass.getMethod(methodName, argsClass);
		return method.invoke(owner, args);
	}

	/**
	 * 向单元格里面写入内容
	 * 
	 * @param sheet
	 * @param row
	 * @param value
	 * @throws RowsExceededException
	 * @throws WriteException
	 */
	private static void writeRow(WritableSheet sheet, int row, String[] value, List<String> angerList)
			throws RowsExceededException, WriteException {
		Label label;
		for (int i = 0; i < value.length; i++) {
			label = new Label(i, row, value[i]);
			if(i == 4 
					&& angerList != null && angerList.size() > 0){
				WritableCellFeatures wcf = new WritableCellFeatures();
				wcf.setDataValidationList(angerList);  
				label.setCellFeatures(wcf);  
			}
			sheet.addCell(label);
		}
	}
}

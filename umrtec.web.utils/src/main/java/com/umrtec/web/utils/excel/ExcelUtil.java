package com.umrtec.web.utils.excel;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.format.UnderlineStyle;
import jxl.format.VerticalAlignment;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

/**
 * 导出Excel文件
 */
public class ExcelUtil {
	
	private final static int pageSize = 65000;
	
	/**
	 * 
	 * 导出Excel文件并由用户选择保存
	 * 
	 * @param <T>
	 * 
	 * @param models
	 *            封装需要的数据，与bean结合
	 * @param classVo
	 *            导成Excel的实体BEAN包名.类名
	 * @param tempPath
	 *            生成Excel存放的临时路径
	 * @param excelName
	 *            生成的Excel名
	 *   @param      ignoreProperties : 不需要导出的属性名或方法名的集合，区分大小写
	 * @throws Exception 
	 */
	public static <T> void createAndOuputExcel(List<T> models, Class<T> classVo, HttpServletResponse response, String excelName,List<String> ignoreProperties) throws Exception{
		
		response.reset();
	    response.setContentType("application/msexcel");
	    response.setHeader("Content-disposition","attachment;filename=" + excelName + ".xls");//定义文件名
		try {
			createExcel(models, classVo, response.getOutputStream(), excelName,ignoreProperties);
			response.getOutputStream().flush();
		} catch (Exception e) {
			throw e;
		} finally {
			response.getOutputStream().close();
		}
	}

	/**
	 * 生成Excel
	 * @param <T>
	 * @param models
	 * @param classVo
	 * @param os
	 * @param excelName
	 * @throws Exception
	 */
	private static <T> void createExcel(List<T> models, Class<T> classVo, OutputStream os, String excelName,List<String> ignoreProperties) throws Exception {

		WritableWorkbook workbook = getWorkbook(models, classVo, excelName, os,ignoreProperties);

		workbook.write();
		workbook.close();
	}

	/**
	 * 生成工作簿
	 * @param <T>
	 * @param models
	 * @param clasVo
	 * @param excelName
	 * @param os
	 * @return
	 * @throws IOException
	 * @throws WriteException
	 * @throws RowsExceededException
	 * @throws NoSuchMethodException
	 */
	@SuppressWarnings("unchecked")
	private static <T> WritableWorkbook getWorkbook(List<T> models,
			Class<T> clasVo, String excelName, OutputStream os,List<String> ignoreProperties)
			throws IOException, WriteException, RowsExceededException,
			NoSuchMethodException {
		WritableWorkbook workbook = Workbook.createWorkbook(os);

		//生成的列内容
		Object[] objects = getAnotationObjects(clasVo,ignoreProperties);
		
		// 按照注解order排序Excel列
		Arrays.sort(objects, new ExcelColumnComparator());
		
		//内容格式
		WritableCellFormat wcf_center = createCenterFormat();
		
		// 写入第几行 第一行为列头 数据从第二行开始写
		int rowId = 1;
		int sheetId = 0;
		
		WritableSheet sheet = createSheet(excelName, workbook, objects, sheetId);
		
		for (Object ssTopModel : models) {
			// 写入第几列 第一列为自动计算的行号 数据从第二列开始写
			int columnId = 0;
			
			// 获取该类 并获取自身方法
			Class<T> clazz = (Class<T>) ssTopModel.getClass();
			for (int i = 0; i < objects.length; i++) {
				Object object = objects[i];
				String methodName = getMethodName(object);	
				Method method = clazz.getMethod(methodName);
				try {
					String value = method
							.invoke(ssTopModel) == null ? "" : method
							.invoke(ssTopModel).toString();
					sheet.addCell(new Label(columnId, rowId, value, wcf_center));
				} catch (Exception e) {
					e.printStackTrace();
				}
				columnId++;
			}
			rowId++;
			if((rowId % pageSize) == 0) {
				//workbook.write();
				sheetId ++;
				rowId = 1;
				sheet = createSheet(excelName, workbook, objects, sheetId);;
			}
		}
		return workbook;
	}
	
	
	
	/**
	 * 获取对应方法名称
	 * @param object Field/Method
	 * @return
	 */
	private static String getMethodName(Object object) {
		String methodName = null;
		if(object instanceof Field) {
			Field field = (Field)object;
			methodName = "get"
				+ field.getName().substring(0, 1).toUpperCase()
				+ field.getName().substring(1);
		} else if(object instanceof Method) {
			Method method = (Method)object;
			methodName = method.getName();
		}
		return methodName;
	}

	/**
	 * 创建页签
	 * @param excelName
	 * @param workbook
	 * @param fields
	 * @param wcf_center
	 * @param sheetId
	 * @return
	 * @throws WriteException
	 * @throws RowsExceededException
	 */
	private static WritableSheet createSheet(String excelName,
			WritableWorkbook workbook, Object[] objects, int sheetId) throws WriteException,
			RowsExceededException {
		WritableSheet sheet = workbook.createSheet(excelName + "-" + sheetId, sheetId);
		
		//标题格式
		WritableCellFormat wcf_title = createTitleFormat();
		
		//添加第一行，即列名
		for (int i = 0; i < objects.length; i++) {
			Object object = objects[i];
			ExcelColumn anno = getAnnotaionObject(object);			
			if(anno != null) {
				sheet.setColumnView(i, anno.width());
				sheet.addCell(new Label(i, 0, anno.name(), wcf_title));
			}
		}
		return sheet;
	}

	/**
	 * 获取注解
	 * @param object
	 * @return
	 */
	private static ExcelColumn getAnnotaionObject(Object object) {
		ExcelColumn anno = null;
		if(object instanceof Field) {
			anno = ((Field)object).getAnnotation(ExcelColumn.class);
		} else if(object instanceof Method) {
			anno = ((Method)object).getAnnotation(ExcelColumn.class);
		}
		return anno;
	}

	/**
	 * 获取需要生成Excel的注解对象（Field/Method）
	 * @param classVo
	 * @return
	 */
	private static <T> Object[] getAnotationObjects(Class<T> classVo,List<String> ignoreProperties) {
		
		List<Object> objects = new ArrayList<Object>();
		
		// 获取所有属性
		Field[] fields = classVo.getDeclaredFields();
		for (Field field : fields) {
			if (field.isAnnotationPresent(ExcelColumn.class) ) {
				if(ignoreProperties == null || !ignoreProperties.contains(field.getName())){ //过滤忽略的属性
					objects.add(field);
				}
			}
		}

		// 获取所有方法
		Method[] methods = classVo.getMethods();
		for (Method method : methods) {
			if(method.isAnnotationPresent(ExcelColumn.class)) {
				if(ignoreProperties == null || !ignoreProperties.contains(method.getName())){//过滤忽略的方法
					objects.add(method);
				}
			}
		}

		return objects.toArray(new Object[objects.size()]);
	}

	/**
	 * 生成内容格式
	 * @return
	 * @throws WriteException
	 */
	private static WritableCellFormat createCenterFormat()
			throws WriteException {
		// 用于正文
		WritableFont normalFont = new WritableFont(WritableFont.TAHOMA, 11);
		WritableCellFormat wcf_center = new WritableCellFormat(normalFont);
		wcf_center.setBorder(Border.ALL, BorderLineStyle.DOUBLE, Colour.GRAY_25);
		wcf_center.setVerticalAlignment(VerticalAlignment.CENTRE); // 垂直对齐
		wcf_center.setAlignment(Alignment.CENTRE);
		wcf_center.setWrap(true); // 是否换行
		return wcf_center;
	}

	/**
	 * 生成标题格式
	 * @return
	 * @throws WriteException
	 */
	private static WritableCellFormat createTitleFormat() throws WriteException {
		// 用于标题
		
		WritableFont titleFont = new WritableFont(WritableFont.TAHOMA, 11, WritableFont.BOLD,
													false, UnderlineStyle.NO_UNDERLINE,
													jxl.format.Colour.BLACK);
		WritableCellFormat wcf_title = new WritableCellFormat(titleFont);
		wcf_title.setBorder(Border.ALL, BorderLineStyle.DOUBLE, Colour.GRAY_25);
		wcf_title.setVerticalAlignment(VerticalAlignment.CENTRE); // 垂直对齐
		wcf_title.setAlignment(Alignment.CENTRE);
		return wcf_title;
	}
}
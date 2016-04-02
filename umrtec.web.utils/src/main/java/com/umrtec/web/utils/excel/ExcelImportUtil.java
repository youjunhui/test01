package com.umrtec.web.utils.excel;

import java.io.FileInputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import org.apache.poi.ss.usermodel.Row;

import com.umrtec.web.utils.tools.DateUtil;

/**
 * Excel导入工具类
 */
public class ExcelImportUtil {
	
	/**
	 * 导入Excel
	 * @param <T>
	 * @param classVo
	 * @param inputStream
	 * @param columns
	 * @param callBack
	 * @return
	 * @throws Exception
	 */
	public static <T> List<T> importFromExcel(Class<T> classVo, FileInputStream inputStream, List<Integer> columns, ExcelImportCallBack callBack) throws Exception {
		List<T> list = new ArrayList<T>();
		Field[] fields = getField(classVo);
		
		try {
			Workbook workbook = WorkbookFactory.create(inputStream);
			int numberOfSheets = workbook.getNumberOfSheets();
			for (int indexOfSheet = 0; indexOfSheet < numberOfSheets; indexOfSheet++) {
				Sheet sheet = workbook.getSheetAt(indexOfSheet);
				// 第一行忽略
				int physicalNumberOfRows = sheet.getPhysicalNumberOfRows();
				for (int row = 1; row < physicalNumberOfRows; row++) {
					T obj = classVo.newInstance();
					Row sheetRow = sheet.getRow(row);
					if(isRowNull(sheetRow)) {
						continue;
					}
					
					for (int i = 0; i < fields.length; i++) {
						Field field = fields[i];
						ExcelImportColumn annotation = field.getAnnotation(ExcelImportColumn.class);
						int column = annotation.column();
						Cell cell = sheetRow.getCell(column);
						String methodName = "set"
							+ field.getName().substring(0, 1).toUpperCase()
							+ field.getName().substring(1);
						Class<?> fieldType = field.getType();
						Method method = classVo.getMethod(methodName, fieldType);
						Object cellValue = getCellValue(cell, fieldType);
						if(annotation.notNull() && cellValue == null) {
							String positionInfo = getPositionInfo(indexOfSheet, row, i);
							throw new RuntimeException(positionInfo + "不能为空！");
						}
						method.invoke(obj, cellValue);
						if(CollectionUtils.isNotEmpty(columns) && columns.contains(column)) {
							boolean isSuccess = callBack.dealCurrentCellValue(column, cellValue, obj);
							if(!isSuccess) {
								String positionInfo = getPositionInfo(indexOfSheet, row, column);
								throw new RuntimeException(positionInfo + "数据填写不符合要求！");
							}
						}
					}
					list.add(obj);
				}
			}
		} catch(Exception e){
			throw e;
		}
		return list;
	}

	/**
	 * 判断是否为空行
	 * @param sheetRow
	 * @return
	 */
	private static boolean isRowNull(Row sheetRow) {
		if(sheetRow == null) {
			return true;
		}
		// 循环每个cell
		Iterator<Cell> cellIterator = sheetRow.iterator();
		while (cellIterator.hasNext()) {
			Cell cell = (Cell) cellIterator.next();
			if(cell != null && !(cell.getCellType() == Cell.CELL_TYPE_BLANK)) {
				return false;
			}
		}
		return true;
	}

	private static String getPositionInfo(int indexOfSheet, int row, int i) {
		String errorInfo = "导入失败:第"
			+ (indexOfSheet + 1) + "个标签第"
			+ (row + 1) + "行第"
			+ (i + 1) + "列";
		return errorInfo;
	}


	private static Object getCellValue(Cell cell, Class<?> fieldType) {
		if(cell == null) {
			return null;
		}
		int cellType = cell.getCellType();
		if(fieldType == String.class) {
			if(cellType == Cell.CELL_TYPE_STRING) {
				return cell.getStringCellValue();
			} else if(cellType == Cell.CELL_TYPE_NUMERIC) {
				return String.valueOf(cell.getNumericCellValue());
			}
		} 
		if(fieldType == Date.class) {
			if(cellType == Cell.CELL_TYPE_STRING) {
				return DateUtil.parse(cell.getStringCellValue());
			}
		}
		
		double value = 0;
		if(cellType == Cell.CELL_TYPE_STRING) {
			value = Double.parseDouble(cell.getStringCellValue());
		} else {
			value = cell.getNumericCellValue();
		}
		
		if(fieldType == int.class || fieldType == Integer.class) {
			return (int)value;
		}
		if(fieldType == long.class || fieldType == Long.class) {
			return (int)value;
		}
		if(fieldType == float.class || fieldType == Float.class) {
			return (float)value;
		}
		if(fieldType == double.class || fieldType == Double.class) {
			return (double)value;
		}
		return null;
	}


	/**
	 * 获取需要生成Excel的属性
	 * @param classVo
	 * @return
	 */
	private static <T> Field[] getField(Class<T> classVo) {
		// 获取所有属性
		Field[] eclaredFields = classVo.getDeclaredFields();

		List<Field> fieldList = new ArrayList<Field>();
		for (Field field : eclaredFields) {
			if (field.isAnnotationPresent(ExcelImportColumn.class)) {
				fieldList.add(field);
			}
		}

		Field[] fields = fieldList.toArray(new Field[fieldList.size()]);
		return fields;
	}

}

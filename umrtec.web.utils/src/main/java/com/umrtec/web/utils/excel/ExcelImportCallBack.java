package com.umrtec.web.utils.excel;


/**
 * excelImport 导入回调
 */
public interface ExcelImportCallBack {

	public <T> boolean dealCurrentCellValue(int column, Object value, T obj);
}

package com.umrtec.web.utils.excel;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Comparator;


/**
 *	比较Excel两个列的顺序
 * 
 */

@SuppressWarnings("rawtypes")
public class ExcelColumnComparator implements Comparator {
	public int compare(Object arg0, Object arg1) {
		ExcelColumn annoOne = null;
		ExcelColumn annoTwo = null;
		if(arg0 instanceof Field) {
			annoOne = ((Field)arg0).getAnnotation(ExcelColumn.class);
		}
		if(arg0 instanceof Method) {
			annoOne = ((Method)arg0).getAnnotation(ExcelColumn.class);
		}
		
		if(arg1 instanceof Field) {
			annoTwo = ((Field)arg1).getAnnotation(ExcelColumn.class);
		}
		if(arg1 instanceof Method) {
			annoTwo = ((Method)arg1).getAnnotation(ExcelColumn.class);
		}

		if(annoOne == null && annoTwo == null) {
			return 0;
		}

		if(annoOne == null) {
			return -1;
		}
		
		if(annoTwo == null) {
			return 1;
		}
		return annoOne.order() - annoTwo.order();
	}
}

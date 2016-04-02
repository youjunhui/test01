/*
 * 文 件 名  :  StringEscapeEditor.java
 * 描    述    :  <描述>
 * 创建人    :  
 * 创建时间:  下午1:38:36
 */
package com.umrtec.web.utils.tools;

import java.beans.PropertyEditorSupport;

import org.apache.commons.lang.StringEscapeUtils;

/**
 * 处理XSS,SQL注入攻击
 * 
 * @author 
 * @version [版本号, 2013-7-1]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class StringEscapeEditor extends PropertyEditorSupport {
    private boolean escapeHTML;
    
    private boolean escapeJavaScript;
    
    private boolean escapeSQL;
    
    public StringEscapeEditor() {
        super();
    }
    
    public StringEscapeEditor(boolean escapeHTML, boolean escapeJavaScript, boolean escapeSQL) {
        super();
        this.escapeHTML = escapeHTML;
        this.escapeJavaScript = escapeJavaScript;
        this.escapeSQL = escapeSQL;
    }
    
    @Override
    public void setAsText(String text) {
        if (text == null) {
            setValue(null);
        }
        else {
            String value = text;
            if (escapeHTML) {
                value = StringEscapeUtils.escapeHtml(value);
            }
            if (escapeJavaScript) {
                value = StringEscapeUtils.escapeJavaScript(value);
            }
            if (escapeSQL) {
                value = StringEscapeUtils.escapeSql(value);
            }
            setValue(value);
        }
    }
    
    @Override
    public String getAsText() {
        Object value = getValue();
        return value != null ? value.toString() : "";
    }
}

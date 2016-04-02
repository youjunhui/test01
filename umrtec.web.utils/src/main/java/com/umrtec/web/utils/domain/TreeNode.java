/*
 * 文 件 名  :  TreeNode.java
 * 描    述    :  <描述>
 * 创建人    :  
 * 创建时间:  下午3:57:24
 */
package com.umrtec.web.utils.domain;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@SuppressWarnings("rawtypes")
public class TreeNode implements Serializable {
    
    private static final long serialVersionUID = -4519280966898673097L;
    
    // 节点编号
    private String id;
    
    // 节点名
    private String text;
    
    // 节点图标对应css 若为空，则以标准树图标显示
    private String iconCls;
    
    // 是否选中
    private boolean checked = false;
    
    // 若同步加载树，对于根节点，若state为open 则展开子节点；若state为closed 这折叠子节点。对于叶子节点，state必须为open
    // 若异步加载树，对于根节点，默认state为closed。若为open，则无法加载其子节点。对于叶子节点，state必须为open，若为closed则会以根节点显示
    // 节点状态 只能有此两种选项 open/closed
    private String state = "closed";
    
    // 封装节点的其他属性，以map形式表示，如 url、pid等
    private Map attributes;
    
    // 若同步加载，此属性必须存在。若异步加载，无需此属性
    private List<?> children;
    
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public String getText() {
        return text;
    }
    
    public void setText(String text) {
        this.text = text;
    }
    
    public String getIconCls() {
        return iconCls;
    }
    
    public void setIconCls(String iconCls) {
        this.iconCls = iconCls;
    }
    
    public boolean isChecked() {
        return checked;
    }
    
    public void setChecked(boolean checked) {
        this.checked = checked;
    }
    
    public String getState() {
        return state;
    }
    
    public void setState(String state) {
        this.state = state;
    }
    
    public Map getAttributes() {
        return attributes;
    }
    
    public void setAttributes(Map attributes) {
        this.attributes = attributes;
    }

	public List<?> getChildren() {
		return children;
	}

	public void setChildren(List<?> children) {
		this.children = children;
	}
}

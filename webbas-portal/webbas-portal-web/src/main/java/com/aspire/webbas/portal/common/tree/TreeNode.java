package com.aspire.webbas.portal.common.tree;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class TreeNode {
	
	private String id;
	
	private String parentId;

	//节点项展示的文字    
    private String text;
    
    private boolean expanded = false;

    //是否叶子节点，也就是此菜单项不包含任何下级菜单，默认为非叶子节点。
    //若children节点非空，则leaf属性=true，否则leaf=false
    private boolean leaf = true;
    
	private Set<TreeNode> children = new LinkedHashSet<TreeNode>();
    
	public TreeNode(){
		
	}
	
	public TreeNode(String id, String parentId, String text){
		this.id       = id;
		this.parentId = parentId;
		this.text     = text;
	}
	
    public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
	public boolean isExpanded() {
		return expanded;
	}

	public void setExpanded(boolean expanded) {
		this.expanded = expanded;
	}

	public boolean isLeaf() {
		return leaf;
	}

	public void setLeaf(boolean leaf) {
		this.leaf = leaf;
	}

	public Set<TreeNode> getChildren() {
		return children;
	}

	public void setChildren(Set<TreeNode> children) {
		if(isLeaf()){
			setLeaf(false);
		}
		this.children = children;
	}

	public void addChild(TreeNode node){
		if(isLeaf()){
			setLeaf(false);
		}
		children.add(node);
	}
	
	public void removeChild(TreeNode node){
		
		children.remove(node);
		
		if(children.size() == 0){
			setLeaf(true);
		}
	}

	public static TreeNode buildTree(List<TreeNode> list){
		Map<String, TreeNode> map = new HashMap<String, TreeNode>();
		
		TreeNode top = null;
		for(TreeNode node : list){
			map.put(node.getId(), node);
			if(node.getParentId() == null || "".equals(node.getParentId())){
				top = node;
			}
		}
		
		for(TreeNode node : list){
			if(node.getParentId() != null && !"".equals(node.getParentId())){
				TreeNode tmpNode = map.get(node.getParentId());
				tmpNode.addChild(node);
			}
		}
		
		return top;
	}
}

package com.hichlink.easyweb.portal.common.tree;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class TreeBuilder {

	private Map<String, TreeNode> treeNodes = new LinkedHashMap<String, TreeNode>();
	
	public void addNode(TreeNode node){
		if(treeNodes.containsKey(node.getId())){
			throw new RuntimeException("存在相同id["+node.getId()+"]的树节点");
		}
		
		treeNodes.put(node.getId(), node);
	}
	
	public void markChecked(String id){
		if(treeNodes.containsKey(id)){
			TreeNode node = treeNodes.get(id);
			if(node instanceof CheckTreeNode){
				((CheckTreeNode)node).setChecked(true);
				
				expandNode(node);
			}
		}
	}
	private void createTree(TreeNode treeNode){
		for(TreeNode node : treeNodes.values()){
			if(node.getParentId() != null && !"".equals(node.getParentId())){
				if(treeNode.getId().equals(node.getParentId())){
					treeNode.addChild(node);
				}
			}
		}
		Set<TreeNode> childrens = treeNode.getChildren();
		if (null != childrens && childrens.size() > 0){
			Iterator<TreeNode> it = childrens.iterator();  
			while (it.hasNext()) {  
				createTree(it.next());  
			}
		}
	}
	public List<TreeNode> buildTree(){
		
		List<TreeNode> rootList = new ArrayList<TreeNode>();
		
		for(TreeNode node : treeNodes.values()){
			if(node.getParentId() == null || "".equals(node.getParentId())){
				rootList.add(node);
				createTree(node);
			}
		}
		return rootList;
	}
	
	/**
	 * 递归展开树节点
	 * @param node
	 */
	private void expandNode(TreeNode node){
		
		if(treeNodes.containsKey(node.getParentId())){
			expandNode(treeNodes.get(node.getParentId()));
		}
		
		node.setExpanded(true);
	}
	
	public List<TreeNode> buildTreeWithNoEmptyNode(){
		
		List<TreeNode> tempList = new ArrayList<TreeNode>();
		// 建立父子关系
		for(TreeNode node : treeNodes.values()){
			if(node.getParentId() != null && !"".equals(node.getParentId())){
				if(treeNodes.containsKey(node.getParentId())){
					treeNodes.get(node.getParentId()).addChild(node);
				}
			}
			
			tempList.add(node);
		}
		
		// 删除没有菜单节点的树节点 
		for(TreeNode node : tempList){
			if(node.isLeaf() && !(node instanceof MenuTreeNode)){
				removeNode(node);
			}
		}
		
		return buildTree();
	}
	
	/**
	 * 递归删除空节点
	 */
	private void removeNode(TreeNode node){
		treeNodes.remove(node.getId());
		
		if(treeNodes.containsKey(node.getParentId())){
			TreeNode parent = treeNodes.get(node.getParentId());
			parent.removeChild(node);
			// 父节点删除child之后如果没有children了，继续删除父节点
			if(parent.isLeaf()){
				removeNode(parent);
			}
		}
		
			
//		if(child.getParentId() == null){
//			treeNodes.remove(child);
//		}else{
//			if(treeNodes.containsKey(parentId)){
//				treeNodes.get(parentId).removeChild(child);
//				treeNodes.remove(child);
//			}
//		}
		
		
	}
}

package com.aspire.webbas.portal.modules.authapi;

import java.io.Serializable;

public class IfmStyle implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3240244812398914237L;
	private Integer winW;
	private Integer winH;
	private Integer ifmW;
	private Integer ifmH;
	private Integer ifmLeft;
	private Integer ifmTop;
	private Integer scrollTop;
	private Integer scrollLeft;
	public Integer getWinW() {
		return winW;
	}
	public void setWinW(Integer winW) {
		this.winW = winW;
	}
	public Integer getWinH() {
		return winH;
	}
	public void setWinH(Integer winH) {
		this.winH = winH;
	}
	public Integer getIfmW() {
		return ifmW;
	}
	public void setIfmW(Integer ifmW) {
		this.ifmW = ifmW;
	}
	public Integer getIfmH() {
		return ifmH;
	}
	public void setIfmH(Integer ifmH) {
		this.ifmH = ifmH;
	}
	public Integer getIfmLeft() {
		return ifmLeft;
	}
	public void setIfmLeft(Integer ifmLeft) {
		this.ifmLeft = ifmLeft;
	}
	public Integer getIfmTop() {
		return ifmTop;
	}
	public void setIfmTop(Integer ifmTop) {
		this.ifmTop = ifmTop;
	}
	public Integer getScrollTop() {
		return scrollTop;
	}
	public void setScrollTop(Integer scrollTop) {
		this.scrollTop = scrollTop;
	}
	public Integer getScrollLeft() {
		return scrollLeft;
	}
	public void setScrollLeft(Integer scrollLeft) {
		this.scrollLeft = scrollLeft;
	}
	
}

package com.welltech.waterAffair.domain.vo;

import java.util.List;

/**
 * 前端页面对象
 * 
 * @author zhoupei
 *
 */
public class PageVo<T> {

	/** 当前页 */
	private int currentPage;
	/** 当前记录数 */
	private int currentSize;
	/** 总页数 */
	private int countPage;
	/** 总记录数 */
	private int countSize;
	/** 具体记录 */
	private List<T> entity;

	public PageVo(int currentPage, int countSize, int currentSize,
			List<T> entity) {
		this.currentPage = currentPage;
		this.countSize = countSize;
		this.currentSize = currentSize;
		this.entity = entity;
		if (currentSize == 0) {
			this.countPage = 0;
		} else {
			int temp = countSize % currentSize > 0 ? 1 : 0;
			this.countPage = countSize / currentSize + temp;
		}
	}

	/*
	public static void main(String[] args) {
		System.out.println(5 % 2);
	}
	*/
	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getCountSize() {
		return countSize;
	}

	public void setCountSize(int countSize) {
		this.countSize = countSize;
	}

	public List<T> getEntity() {
		return entity;
	}

	public void setEntity(List<T> entity) {
		this.entity = entity;
	}

	public int getCurrentSize() {
		return currentSize;
	}

	public void setCurrentSize(int currentSize) {
		this.currentSize = currentSize;
	}

	public int getCountPage() {
		return countPage;
	}

	public void setCountPage(int countPage) {
		this.countPage = countPage;
	}

}

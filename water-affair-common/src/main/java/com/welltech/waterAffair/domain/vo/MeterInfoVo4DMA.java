package com.welltech.waterAffair.domain.vo;

public class MeterInfoVo4DMA {
	private  Integer id;
	private String text;
	private Integer mid;
	private boolean selected;
	private boolean selected2;
	
	
	public MeterInfoVo4DMA(Integer id,String text, Integer mid, boolean selected, boolean selected2) {
		super();
		this.id=id;
		this.text = text;
		this.mid = mid;
		this.selected = selected;
		this.selected2 = selected2;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Integer getMid() {
		return mid;
	}

	public void setMid(Integer mid) {
		this.mid = mid;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public boolean isSelected2() {
		return selected2;
	}

	public void setSelected2(boolean selected2) {
		this.selected2 = selected2;
	}
}

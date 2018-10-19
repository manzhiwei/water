package com.welltech.waterAffair.domain.vo;

public class MachineInfoVo4JS {
	private String text;
	private Double longitude;
	private Double latitude;
	private Float mintotal;
	private Float maxtotal;
	private Integer mid;
	private String icon;
	public MachineInfoVo4JS(Integer mid,String text, Double longitude, Double latitude,
                            Float mintotal, Float maxtotal) {
		super();
		this.text = text;
		this.mid=mid;
		this.longitude = longitude;
		this.latitude = latitude;
		this.mintotal = mintotal;
		this.maxtotal = maxtotal;
	}
	public String gettext() {
		return text;
	}
	public void settext(String text) {
		this.text = text;
	}
	public Double getLongitude() {
		return longitude;
	}
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}
	public Double getLatitude() {
		return latitude;
	}
	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}
	public Float getMintotal() {
		return mintotal;
	}
	public void setMintotal(Float mintotal) {
		this.mintotal = mintotal;
	}
	public Float getMaxtotal() {
		return maxtotal;
	}
	public void setMaxtotal(Float maxtotal) {
		this.maxtotal = maxtotal;
	}

	public Integer getMid() {
		return mid;
	}

	public void setMid(Integer mid) {
		this.mid = mid;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}
}

package com.welltech.waterAffair.domain.vo.before;

public class MachineInfoVo {
	private String subUserName;
	private Double longitude;
	private Double latitude;
	private Float mintotal;
	private Float maxtotal;
	private String detail;
	private  int status;

	public MachineInfoVo(String subUserName, Double longitude, Double latitude,
                         Float mintotal, Float maxtotal) {
		super();
		this.subUserName = subUserName;
		this.longitude = longitude;
		this.latitude = latitude;
		this.mintotal = mintotal;
		this.maxtotal = maxtotal;
	}
	public String getSubUserName() {
		return subUserName;
	}
	public void setSubUserName(String subUserName) {
		this.subUserName = subUserName;
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

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
}

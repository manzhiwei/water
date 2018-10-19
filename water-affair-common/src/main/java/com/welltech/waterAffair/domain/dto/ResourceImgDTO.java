package com.welltech.waterAffair.domain.dto;

import java.io.Serializable;
import java.util.Date;


/**
 * The persistent class for the resource_img database table.
 * 
 */
public class ResourceImgDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;
	
	private Integer num;

	private Date createTime;

	private Integer createUser;

	private String fileName;

	private String fileType;

	private Date updateTime;

	private Integer updateUser;

	private String uri;
	
	private Integer archivesRecordId;
	public ResourceImgDTO() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getCreateUser() {
		return this.createUser;
	}

	public void setCreateUser(Integer createUser) {
		this.createUser = createUser;
	}

	public String getFileName() {
		return this.fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileType() {
		return this.fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Integer getUpdateUser() {
		return this.updateUser;
	}

	public void setUpdateUser(Integer updateUser) {
		this.updateUser = updateUser;
	}

	public String getUri() {
		return this.uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public Integer getArchivesRecordId() {
		return archivesRecordId;
	}

	public void setArchivesRecordId(Integer archivesRecordId) {
		this.archivesRecordId = archivesRecordId;
	}

}
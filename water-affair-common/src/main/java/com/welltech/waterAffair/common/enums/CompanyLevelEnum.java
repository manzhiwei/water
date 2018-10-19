package com.welltech.waterAffair.common.enums;

/**
 * 公司等级枚举
 * @author wangxin
 *
 */
public enum CompanyLevelEnum {
	PARENT("1","总公司")
	,BRANCH("2","分公司")
	,AREA("3","区域");
	
	private String key;
	
	private String value;
	
	private CompanyLevelEnum(String key, String value){
		this.key = key;
		this.value = value;
	}
	
	public String getKey(){
		return key;
	}
	
	public String getValue(){
		return value;
	}
	
	public CompanyLevelEnum getByKey(String key){
		for(CompanyLevelEnum levelEnum : CompanyLevelEnum.values()){
			if(levelEnum.key.equals(key)){
				return levelEnum;
			}
		}
		return null;
	}
}

package com.welltech.waterAffair.common.constant;

/**
 * 公共常用静态变量
 * @author zhoupei
 *
 */
public final class Constants {
	/**删除标示：0否*/
	public static int DELETE_FLAG_0=0;
	/**删除标示：1是*/
	public static int DELETE_FLAG_1=1;
	/**登陆*/
	public static String OPERATE_STRING_LOGIN="登录";
	public static String OPERATE_STRING_LOGIN_V="1";
	/**设置参数*/
	public static String OPERATE_STRING_SET="设置参数";
	public static String OPERATE_STRING_SET_V="2";
	/**查看*/
	public static String OPERATE_STRING_QUERY="QUERY";
	public static String OPERATE_STRING_QUERY_V="2";
	
	public static String RESOURCE_IMG_INSTALL="1";
	public static String RESOURCE_IMG_RUN="2";

	public static String ARCHIVES_1="安装图";
	public static String ARCHIVES_2="走向图";

	public static String ARCHIVES_3="检查";
	public static String ARCHIVES_4="检定";
	public static String ARCHIVES_5="更换电池";
	public static String ARCHIVES_6="更换仪表";
	
	public static Integer METERTYPE_1=1;//WTS4200电流水表

	public static Integer METERTYPE_2=2;//WTS4200电磁水表
	
	public static String RETURN_CODE_000="000";
	public static String RETURN_CODE_001="001";
	
	/**
	 * true 是电流表，false是电磁表
	 * @param type
	 * @return
	 */
	public static boolean isElectricity(Integer type){
		if(METERTYPE_1==type){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 判断上传文件类型
	 * @param fileValue
	 * @return
	 */
	public static String changeValue(Integer fileValue){
		String returnVal="";
		switch(fileValue){
		case 1:
			returnVal=ARCHIVES_1;
			break;
		case 2:
			returnVal=ARCHIVES_2;
			break;
		case 3:
			returnVal=ARCHIVES_3;
			break;
		case 4:
			returnVal=ARCHIVES_4;
			break;
		case 5:
			returnVal=ARCHIVES_5;
			break;
		case 6:
			returnVal=ARCHIVES_6;
			break;
		}
		return returnVal;
		
	}
}

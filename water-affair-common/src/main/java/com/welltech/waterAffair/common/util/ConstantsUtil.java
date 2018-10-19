package com.welltech.waterAffair.common.util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.welltech.waterAffair.common.base.Properties;
import com.welltech.waterAffair.domain.dto.ResourceImgDTO;

@Component
public class ConstantsUtil {
	@Resource
	private Properties properties;
	public static Map<Integer,String> sbkjDic=new HashMap<Integer,String>();
	static{
		//初始化水表口径的字典
        sbkjDic.put(0,"DN25");
        sbkjDic.put(1,"DN32");
        sbkjDic.put(2,"DN40");
        sbkjDic.put(3,"DN50");
        sbkjDic.put(4,"DN65");
        sbkjDic.put(5,"DN80");
        sbkjDic.put(6,"DN100");
        sbkjDic.put(7,"DN125");
        sbkjDic.put(8,"DN150");
        sbkjDic.put(9,"DN200");
        sbkjDic.put(10,"DN250");
        sbkjDic.put(11,"DN300");
        sbkjDic.put(12,"DN350");
        sbkjDic.put(13,"DN400");
        sbkjDic.put(14,"DN450");
        sbkjDic.put(15,"DN500");
        sbkjDic.put(16,"DN600");
        sbkjDic.put(17,"DN700");
        sbkjDic.put(18,"DN800");
        sbkjDic.put(19,"DN900");
        sbkjDic.put(20,"DN1000");
        sbkjDic.put(21,"DN1200");
	}

    public static String getRealPath(HttpServletRequest request){
    	String realPath=request.getSession().getServletContext().getRealPath("/");;
    	if(realPath.indexOf("\\",1)>=0){
        	realPath=realPath.substring(0,realPath.indexOf("\\",1));
    	}else{
        	realPath=realPath.substring(0,realPath.indexOf("/",0));
    	}
    	return realPath;
    }
    
    /**
	 * 
	 * 上传文件并封装数据
	 * @param request
	 * @param file	文件对象
	 * @param type	文件类型
	 * @param num	水表号
	 * @return
	 * @throws Exception
	 */
	public ResourceImgDTO formateFile(HttpServletRequest request,MultipartFile file,String type,Integer num) throws Exception {
		String filename = file.getOriginalFilename();
		String realPath=ConstantsUtil.getRealPath(request)+properties.path+File.separator+num+File.separator+type;
		if (!file.isEmpty()) {
			try {
				byte[] bytes = file.getBytes();
				File dir=new File(realPath);
				if(!dir.exists()){
					dir.mkdirs();
				}
				BufferedOutputStream stream = new BufferedOutputStream(
						new FileOutputStream(new File(realPath+File.separator+filename)));
				stream.write(bytes);
				stream.close();
			} catch (Exception e) {
				throw e;
			}
		} else {
			return null;
		}
		ResourceImgDTO img = new ResourceImgDTO();
		img.setFileName(filename);
		img.setFileType(type);
		img.setUri(properties.path+File.separator+num+File.separator+type);
		return img;
	}
	
	/**
	 * 
	 * 上传文件并封装数据
	 * @param request
	 * @param file	文件对象
	 * @param type	文件类型
	 * @param num	水表号
	 * @return
	 * @throws Exception
	 */
	public ResourceImgDTO formateFile(HttpServletRequest request,MultipartFile file,String type,Integer num,Integer archivesId) throws Exception {
		String filename = file.getOriginalFilename();
		String realPath=ConstantsUtil.getRealPath(request)+properties.path+File.separator+num+File.separator+type+File.separator+archivesId;
		if (!file.isEmpty()) {
			try {
				byte[] bytes = file.getBytes();
				File dir=new File(realPath);
				if(!dir.exists()){
					dir.mkdirs();
				}
				BufferedOutputStream stream = new BufferedOutputStream(
						new FileOutputStream(new File(realPath+File.separator+filename)));
				stream.write(bytes);
				stream.close();
			} catch (Exception e) {
				throw e;
			}
		} else {
			return null;
		}
		ResourceImgDTO img = new ResourceImgDTO();
		img.setFileName(filename);
		img.setFileType(type);
		img.setUri(properties.path+File.separator+num+File.separator+type+File.separator+archivesId);
		return img;
	}
	
	public static String formateNumber(Object f){
		if(f==null){
			return "0.0";
		}else if(f instanceof Float||f instanceof Double||f instanceof BigDecimal){
			DecimalFormat df = new DecimalFormat("######0.00");   
			return df.format(f);
		}else{
			return f+"";
		}
	}
	
	public static Float formateNumber(Float f){
		if(f==null||f.isNaN()){
			return 0.00f;
		}
		DecimalFormat df = new DecimalFormat("######0.00");   
		return Float.valueOf(df.format(f));
	}
	
	public static Double formateNumber(Double f){
		if(f==null||f.isNaN()){
			return 0.00d;
		}
		DecimalFormat df = new DecimalFormat("######0.00");   
		return Double.valueOf(df.format(f));
	}
}

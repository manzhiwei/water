package com.welltech;

import javax.annotation.Resource;

import com.welltech.waterAffair.domain.entity.MachineInfo;
import com.welltech.waterAffair.service.MeterService;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.welltech.waterAffair.domain.entity.Ndata;
import com.welltech.waterAffair.repository.GprsDataMapper;
import com.welltech.waterAffair.service.MobileService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ServiceTest {
	@Resource
	private MobileService mobileService;

	@Resource
	private MeterService meterService;
	
	@Resource
	private GprsDataMapper gprsDataMapper;
	
	@Test
	@Ignore
	public void test() throws ParseException {

		String date = "2017-03-11";
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		List<Ndata> result=mobileService.findMeterHourDataByDate(120,dateFormat.parse(date+" 00:00:00"),dateFormat.parse(date+" 24:00:00"));


		System.out.println("-------"+result.size());
		//mobileService.findUserMeterListInCompany(null, 1);
		/*Ndata ndata = gprsDataMapper.findOneNdataByNum(1);

		System.out.println(ndata);*/

		//System.out.println(meterService.findMeterLastestHourInfo(1));
	}
}

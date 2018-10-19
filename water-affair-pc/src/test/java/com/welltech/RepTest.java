package com.welltech;

import javax.annotation.Resource;

import com.welltech.waterAffair.domain.entity.MachineInfo;
import com.welltech.waterAffair.repository.MachineInfoMapper;
import com.welltech.waterAffair.service.MeterService;
import org.apache.commons.lang.math.FloatRange;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RepTest {
	
	/*@Resource
	private MachineInfoMapper machineInfoMapper;
	
	@Resource
	private NdataMapper ndataMapper;
	
	@Resource
	private NdataSsMapper ndataSsMapper;*/

	@Resource
	private MachineInfoMapper machineInfoMapper;

	@Resource
	private MeterService meterService;

	@Test
	@Ignore
	public void test(){
		//MachineInfo info = machineInfoMapper.findOneByNum(110);
		//System.out.println(info);
		
		//Ndata ndata = ndataMapper.findOneByNum(110);
		//System.out.println(ndata);
		
		//NdataSs ndataSs = ndataSsMapper.findOneByNum(112);
		//System.out.println(ndataSs);

		List<String> stations = new ArrayList<>();
		stations.add("云南中水");
		stations.add("山东");
		
		List<MachineInfo> infos = machineInfoMapper.findBySubname(stations);
		
		System.out.println(
				infos
		);
	}

	@Test
	@Ignore
	public void testFlow(){
		MachineInfo info = machineInfoMapper.findOneByNum(210);
		Float f = meterService.getDayTotalFlowDiff(info);
		System.out.println(f);
	}
	
}

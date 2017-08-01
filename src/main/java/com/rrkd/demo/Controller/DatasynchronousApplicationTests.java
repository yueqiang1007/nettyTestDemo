package com.rrkd.demo.Controller;

import com.rrkd.demo.Entity.EntityText;
import com.rrkd.demo.Server.DataServer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
public class DatasynchronousApplicationTests {

	@Autowired(required=true)
	private DataServer dataServer;
	 EntityText entityText = null;

	int count = 0;

	//插入
	@Scheduled(cron="5/10 * * * * ?")
	public void contextLoads() {
			entityText = new EntityText();
			entityText.setCityname("西昌"+count);
			entityText.setPollutantcode("no"+count);
			entityText.setPollutantnum(50+count);
			entityText.setStationnum(60+count);
			dataServer.contextLoads(entityText);
			count +=1;
	}

	//存储过程
	@Scheduled(cron="5/10 * * * * ?")
	public void testController(){
			entityText = new EntityText();
			entityText.setCityname("西昌"+count);
			entityText.setPollutantcode("no"+count);
			entityText.setPollutantnum(50+count);
			entityText.setStationnum(60+count);
			dataServer.testMapper(entityText);
			count +=1;
	}
	//查询
/*	@Scheduled(cron="0/30 * * * * ?")
	public void findtextController(){
		dataServer.findtext();
	}*/
}

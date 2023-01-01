package com.ebchinatech.itop.web.punch.controller;
import com.alibaba.fastjson.JSON;
import com.ebchinatech.itop.api.utils.CommonService;
import com.ebchinatech.itop.web.punch.domain.PositionPunch;
import com.ebchinatech.itop.web.punch.domain.PunchCalendars;
import com.ebchinatech.itop.web.punch.mapper.PositionPunchMapper;
import com.ebchinatech.itop.web.punch.mapper.PunchCalendarsMapper;
import com.ebchinatech.kylin.web.controller.common.BaseController;
import com.ebchinatech.kylin.web.domain.AjaxResult;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/kylinApi/calendars")
@Transactional
@Slf4j
public class AttendPersonController extends BaseController
{
	@Value("${PUNCH.PREV}")
	private String prevUrl;

	@Value("${PUNCH.COOKIE}")
	private String cookieUrl;
	@Autowired
	private CommonService commonService;
	@Autowired
    private PunchCalendarsMapper punchCalendarsMapper;


	@GetMapping(value = "/list")
    public Map list(String startDate,String endDate) throws ParseException {

		Map resultMap=new HashMap<>();
        resultMap.put("status", 1);
        resultMap.put("message", "success");

        //考勤日历
		List<PunchCalendars> list=this.punchCalendarsMapper.getBetweenList(startDate, endDate);

		String []arrs= {
				"K4-001王一博,K4-001王一博,20220301",
				"K4002-刘亦菲,K4002-刘亦菲,20220316",

				"K4-001,K4-001,20220318",
				"K4-002,K4-002,20220318",
				"K4-003,K4-003,20220318",
				"K4-004,K4-004,20220318",
				"K4-005,K4-005,20220318",
				"K4-006,K4-006,20220318",
				"K4-007,K4-007,20220318",
				"K4-008,K4-008,20220318",
				"K4-009,K4-009,20220318",
				"K4-010,K4-010,20220318",

				"K4-100,K4-100,20220407",
				"K4-102,K4-102,20220407",
				"K4-103,K4-103,20220407",
				"K4-104,K4-104,20220407",
				"K4-105,K4-105,20220407",
				"K4-106,K4-106,20220407",
				"K4-107,K4-107,20220407",
				"K4-108,K4-108,20220407",
				"K4-109,K4-109,20220407",
				"K4-110,K4-110,20220407",
		};


		List<Map> listResult=new ArrayList<>();
		for(String s:arrs) {
			listResult.addAll(dealPeople(list,s.split(",")[0],s.split(",")[1],s.split(",")[2]));
		}


		resultMap.put("data",listResult);
		return resultMap;
	}

	public List<Map> dealPeople(List<PunchCalendars> list,String jobName,
			String jobNumber,String admissionDate){

		List<Map> resultList=new ArrayList<Map>();

		if(list!=null && list.size()>0) {
			resultList=JSON.parseArray(JSON.toJSONString(list), Map.class);
			for(Map ma:resultList) {
				dealMap(ma,jobName,jobNumber);
			}
			//过滤数据 比如大于等于 入场时间
			resultList=resultList.stream().filter(
					 item-> item.get("workDate").toString().compareTo(admissionDate)>=0
					).collect(Collectors.toList());

		}

		return resultList;
	}

	public void dealMap(Map ma,String jobName,String jobNumber) {
		ma.put("workshiftcode", "D001");
		ma.put("actualworkinghours", 8);
		ma.put("overtimehours", 0);
		ma.put("TX", 0);
		ma.put("workshift", "日常行政班");


		String workDate=ma.get("workDate")+"";

		try {
			//考勤日期
			ma.put("attendancedate", new SimpleDateFormat("yyyyMMdd").parse(workDate).getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}

		ma.put("isabsent", "否");
		ma.put("absenthours", 0);


		ma.put("XJ", 0);
		ma.put("cominglate", 0);
		ma.put("jobnumber", jobNumber);
		ma.put("belongdateweek", "未知");
		ma.put("leavingearly", 0);
		ma.put("work_time", 480);
		ma.put("GC", 0);

		ma.put("worktime", 8.00);
		ma.put("kg_work_time", 480);
		ma.put("jobname",jobName);

		/*if(workDate.equals("20220304")){
			ma.put("GC", 8);
			ma.put("actualworkinghours", 0);
		}*/

		/*if(workDate.equals("20220804") ) {
			ma.put("actualworkinghours", 0);
			ma.put("isabsent", "是");
			ma.put("absenthours", 8);
			ma.put("work_time", 0);
			ma.put("worktime", 0);
			ma.put("kg_work_time", 0);
		}*/
	}

	public static void main(String[] args) {
		System.out.println("2022-03-05".substring(0,7));

		  String t1= "20220302" ;
		   String t2= "20220301" ;
		   int  result = t1.compareTo(t2);
		   System.out.println(result);

		   double a=23/22;
		   System.out.println(a);
		   System.out.println(0.8);
	}

	@GetMapping(value = "/downLoadFile")
	public void downLoadFile( String url, HttpServletResponse response)
	{
		ResponseEntity<byte[]> forEntity = commonService.getForEntity(this.prevUrl + this.cookieUrl, this.prevUrl+"/" + url);

		String[] split = url.split("/");
		String fileName=split[split.length-1];
		try {
			if(forEntity!=null){
				response.setCharacterEncoding("UTF-8");
				response.setContentType("content-type:octet-stream");
				response.setHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode(fileName, "UTF-8"));
				response.getOutputStream().write(forEntity.getBody());

			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}


}

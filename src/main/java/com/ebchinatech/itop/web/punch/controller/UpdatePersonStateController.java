package com.ebchinatech.itop.web.punch.controller;

import com.alibaba.fastjson.JSON;
import com.ebchinatech.itop.api.utils.CommonService;
import com.ebchinatech.itop.web.punch.domain.PersonInfoPunch;
import com.ebchinatech.itop.web.punch.domain.PunchCalendars;
import com.ebchinatech.itop.web.punch.mapper.PersonInfoPunchMapper;
import com.ebchinatech.itop.web.punch.mapper.PunchCalendarsMapper;
import com.ebchinatech.itop.web.punch.service.IPersonInfoPunchService;
import com.ebchinatech.kylin.web.controller.common.BaseController;
import com.ebchinatech.kylin.web.domain.AjaxResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/*** 方法描述
 * @title:
 * @createAuthor: hjw
 * @createDate: 2022/11/23 10:44
 * @description: 重置打卡状态
 * @param null
 * @return:
 */
@RestController
@RequestMapping("/kylinApi/changePerson")
@Transactional
@Slf4j
public class UpdatePersonStateController extends BaseController
{
	@Value("${PUNCH.PREV}")
	private String prevUrl;

	@Autowired
    private PersonInfoPunchMapper personInfoPunchMapper;

	@Autowired
	private IPersonInfoPunchService personInfoPunchService;


	@GetMapping(value = "/updateByPhone")
    public String updatePerson(String phone,String type) throws ParseException {
		String msg="<span style='color:red;'>"+phone+" 操作失败"+"</span>";
      if(StringUtils.isNotEmpty(phone)){
      	 this.personInfoPunchMapper.changePunchStatueByPhone(phone);
		  PersonInfoPunch personInfo = new PersonInfoPunch();
		  personInfo.setPhone(phone);
		  List<PersonInfoPunch> list = personInfoPunchService.selectPersonInfoPunchList(personInfo);
		  if(list!=null && list.size()>0){
			  PersonInfoPunch personInfoPunch = list.get(0);
			  msg=dealMsg(personInfoPunch);
		  }
		  return msg;

	  }
      return msg;
	}

	public String dealMsg(PersonInfoPunch personInfoPunch){
		String msg="姓名:"+personInfoPunch.getUserName()+" 手机号:"+personInfoPunch.getPhone()+" 打卡任务";
		if("1".equals(personInfoPunch.getIsPunch())){
			//开启
			msg+="已"+"<span style='color:green;'>"+"开启"+"</span>";
		}else{
			//关闭
			msg+="已"+"<span style='color:red;'>"+"关闭"+"</span>";
		}

		return msg;
	}


}

package com.ebchinatech.itop.web.punch.controller;

import com.ebchinatech.itop.web.punch.domain.PositionPunch;
import com.ebchinatech.itop.web.punch.mapper.PositionPunchMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Map;

/**
 * @className: com.ebchinatech.itop.web.punch.controller-> DemoController
 * @description: 测试数据
 * @author: hjw
 * @createDate: 2022-10-13 9:10
 * @version: 1.0
 * @todo:
 */
@RestController
@RequestMapping("/kylinApi/demo")
@Transactional
@Slf4j
public class DemoController {

    public  String txt2String(String path, String encoding){
        StringBuilder result = new StringBuilder();
        try{

            File file = new File(path);
            FileInputStream fis = new FileInputStream(file);
            InputStreamReader isr = new InputStreamReader(fis,encoding);
            BufferedReader br = new BufferedReader(isr);
            //BufferedReader br = new BufferedReader(new FileReader(file),"UTF-8");//构造一个BufferedReader类来读取文件
            String s = null;
            int x=0;
            while((s = br.readLine())!=null){//使用readLine方法，一次读一行
                result.append(System.lineSeparator()+s);
            }
            br.close();

        }catch(Exception e){
            e.printStackTrace();
        }
        return result.toString();

    }
}

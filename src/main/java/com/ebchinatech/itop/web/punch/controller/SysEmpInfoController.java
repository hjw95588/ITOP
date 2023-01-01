package com.ebchinatech.itop.web.punch.controller;

import java.io.*;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import cn.afterturn.easypoi.excel.entity.result.ExcelImportResult;
import com.alibaba.fastjson.JSON;
import com.ebchinatech.itop.api.utils.CommonService;
import com.ebchinatech.itop.web.punch.domain.PersonInfoGivelike;
import com.ebchinatech.itop.web.punch.domain.SysEmpInfoExport;
import com.ebchinatech.itop.web.punch.domain.TerminationPersonImport;
import com.ebchinatech.itop.web.punch.mapper.SysEmpInfoMapper;
import com.ebchinatech.itop.web.punch.util.MyExcelExportStyle;
import com.ebchinatech.kylin.web.controller.common.BaseController;
import com.ebchinatech.kylin.web.domain.SysDictData;
import com.ebchinatech.kylinflow.utils.DateUtils;
import com.ebchinatech.kylinflow.utils.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ebchinatech.kylin.framework.aspectj.lang.annotation.Log;
import com.ebchinatech.kylin.framework.aspectj.lang.enums.BusinessType;
import com.ebchinatech.itop.web.punch.domain.SysEmpInfo;
import com.ebchinatech.itop.web.punch.service.ISysEmpInfoService;
import com.ebchinatech.kylin.web.domain.AjaxResult;
import com.ebchinatech.kylin.framework.utils.poi.ExcelUtil;
import com.ebchinatech.kylin.web.domain.page.TableDataInfo;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

/**
 * 人员信息Controller
 *
 * @author hjw
 * @date 2022-09-11
 */
@RestController
@RequestMapping("/sysEmpInfo/empInfo")
public class SysEmpInfoController extends BaseController
{
    @Value("${PUNCH.FILE_PATH}")
    private String filePath;
    @Autowired
    private ISysEmpInfoService sysEmpInfoService;

    @Autowired
    private CommonService commonService;

    @Value("${PUNCH.PREV}")
    private String prevUrl;

    @Value("${PUNCH.COOKIE}")
    private String cookieUrl;

    @Autowired
    private SysEmpInfoMapper sysEmpInfoMapper;

    /**
     * 查询人员信息列表
     */
    @PreAuthorize("@ss.hasPermi('sysEmpInfo:empInfo:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysEmpInfo sysEmpInfo)
    {
        startPage();
        List<SysEmpInfo> list = sysEmpInfoService.selectSysEmpInfoList(sysEmpInfo);
        return getDataTable(list);
    }

    /**
     * 导出人员信息列表
     */
    @PreAuthorize("@ss.hasPermi('sysEmpInfo:empInfo:export')")
    @Log(title = "人员信息", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(SysEmpInfo sysEmpInfo)
    {
        List<SysEmpInfo> list = sysEmpInfoService.selectSysEmpInfoList(sysEmpInfo);
        ExcelUtil<SysEmpInfo> util = new ExcelUtil<SysEmpInfo>(SysEmpInfo.class);
        return util.exportExcel(list, "empInfo");
    }

    /**
     * 获取人员信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('sysEmpInfo:empInfo:query')")
    @GetMapping(value = "/{empid}")
    public AjaxResult getInfo(@PathVariable("empid") Long empid)
    {
        return AjaxResult.success(sysEmpInfoService.selectSysEmpInfoById(empid));
    }

    /**
     * 新增人员信息
     */
    @PreAuthorize("@ss.hasPermi('sysEmpInfo:empInfo:add')")
    @Log(title = "人员信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SysEmpInfo sysEmpInfo)
    {
        return toAjax(sysEmpInfoService.insertSysEmpInfo(sysEmpInfo));
    }

    /**
     * 修改人员信息
     */
    @PreAuthorize("@ss.hasPermi('sysEmpInfo:empInfo:edit')")
    @Log(title = "人员信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SysEmpInfo sysEmpInfo)
    {
        return toAjax(sysEmpInfoService.updateSysEmpInfo(sysEmpInfo));
    }

    /**
     * 删除人员信息
     */
    @PreAuthorize("@ss.hasPermi('sysEmpInfo:empInfo:remove')")
    @Log(title = "人员信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{empids}")
    public AjaxResult remove(@PathVariable Long[] empids)
    {
        return toAjax(sysEmpInfoService.deleteSysEmpInfoByIds(empids));
    }

    @GetMapping(value = "/imageFile")
    public void imageFile(SysEmpInfo info , HttpServletResponse response)  {

        File file = new File(this.filePath);
        if(!file.exists()){
            file.mkdirs();
        }

        if(StringUtils.isNotEmpty(info.getPhotoUrl())){
            //判断图片是否存在。不存在的话，就保存
            String[] split = info.getPhotoUrl().split("/");
            String fileName=info.getEmpName()+"_"+info.getWorkno()+"_"+info.getEmpid()+"_"+split[split.length-1];
            if(!new File(this.filePath+fileName).exists()){
                //不存在
                saveFile(info,fileName,response);
            }else{
                //存在，
                ResponseEntity(fileName,response);
            }
        }
    }

    public void ResponseEntity(String fileName,HttpServletResponse response){
        FileInputStream ios=null;
        try{
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ios = new FileInputStream(this.filePath + fileName);
            int len = 0;
            byte[] b = new byte[1024];
            while ((len = ios.read(b, 0, b.length)) != -1) {
                baos.write(b, 0, len);
            }
            byte[] buffer =baos.toByteArray();
            response.getOutputStream().write(buffer);
        }catch (Exception e){

        }
        finally{
            try{
                if(ios!=null){
                    ios.close();
                }
            }catch (Exception e){

            }
        }

    }

    public void saveFile(SysEmpInfo info,String fileName,HttpServletResponse response){
        ResponseEntity<byte[]> forEntity = commonService.getForEntity(this.prevUrl + this.cookieUrl, this.prevUrl+"/" + info.getPhotoUrl());
        FileOutputStream fileOutputStream=null;
        try {
            if(forEntity!=null){
                fileOutputStream = new FileOutputStream(this.filePath+fileName);
                fileOutputStream.write(forEntity.getBody());
                response.getOutputStream().write(forEntity.getBody());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            try{
                if(fileOutputStream!=null){
                    fileOutputStream.close();
                }
            }catch (Exception e){

            }
        }
    }

    @GetMapping(value = "/downLoadFile")
    public void downLoadFile(SysEmpInfo info , HttpServletResponse response)
    {
        String url=info.getPhotoUrl();
        Long empId=info.getEmpid();
        String workno=info.getWorkno();
        String empName=info.getEmpName();

        ResponseEntity<byte[]> forEntity = commonService.getForEntity(this.prevUrl + this.cookieUrl, this.prevUrl+"/" + url);

        String[] split = info.getPhotoUrl().split("/");
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

    @GetMapping("/downLoadTemplateExcel")
    public void downLoadTemplateExcel(HttpServletResponse response) {


        List<SysEmpInfo> list = sysEmpInfoService.selectSysEmpInfoList(new SysEmpInfo());
        List<SysEmpInfoExport> admissionList =
                JSON.parseArray(JSON.toJSONString(list), SysEmpInfoExport.class);


        String fileName="人员总览列表.xlsx";
        ExportParams exportParams = new ExportParams();
        exportParams.setSheetName("供应商人员总览");
        exportParams.setStyle(MyExcelExportStyle.class);
        exportParams.setType(ExcelType.XSSF);
        try {
            Workbook workbook = ExcelExportUtil.exportExcel(exportParams, SysEmpInfoExport.class, admissionList);
            response.setCharacterEncoding("UTF-8");
            response.setContentType("content-type:octet-stream");
            response.setHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode(fileName, "UTF-8"));
            workbook.write(response.getOutputStream());
        } catch (Exception e) {

        }

    }


    @PostMapping("/terminationPerson/import")
    public AjaxResult personImport(MultipartFile files) throws Exception {

        ImportParams importParams = new ImportParams();
        importParams.setTitleRows(0);
        importParams.setHeadRows(1);
        importParams.setSheetNum(1);

        importParams.setNeedVerfiy(true);//设置验证支持

        try{
            ExcelImportResult<TerminationPersonImport> object =
                    ExcelImportUtil.importExcelMore(files.getInputStream(),
                            TerminationPersonImport.class, importParams);

            List<TerminationPersonImport> list = object.getList();

            if(list!=null && list.size()>0){
                List<SysEmpInfo> sysEmpInfos = JSON.parseArray(JSON.toJSONString(list), SysEmpInfo.class);
                sysEmpInfoMapper.updateTerminationDate(sysEmpInfos);
                return AjaxResult.success(list.size());
            }



        }catch (Exception e){
            throw e;
        }


        return toAjax(0);
    }

    //升级人员导入
    @PostMapping("/updateGradePerson/import")
    public AjaxResult updateGradePerson(MultipartFile files) throws Exception{
        List<Map> result=new ArrayList<>();
        String nowDateStr= DateUtils.dateTimeNow("yyyyMMdd");
        String year=nowDateStr.substring(0,4);
        try{
          String content=txt2String("UTF-8",files.getInputStream());
          result=JSON.parseArray(content,Map.class);

          if(result!=null && result.size()>0){
              for(Map ma:result){
                  ma.put("year",year);
              }
              this.sysEmpInfoMapper.updateSysEmpUpgrade(result);
              this.sysEmpInfoMapper.insertEmpUpgrade(result);
          }

        }catch (Exception e){
            throw e;
        }
        return AjaxResult.success(result.size());
    }

    public  String txt2String(String encoding,InputStream fis){
        StringBuilder result = new StringBuilder();
        try{

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

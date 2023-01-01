package com.ebchinatech.itop.common.utils;


import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import com.ebchinatech.kylin.common.exception.CustomException;
import com.ebchinatech.kylin.framework.utils.poi.ExcelUtil;
import com.ebchinatech.kylin.web.domain.AjaxResult;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

public class ExcelExtUtils {
    /**
     * easypoi导出
     */
    public static String export(ExportParams exportParams, Class<?> clazz, List<?> list,String sheetName){
        String fileName= ExcelUtil.encodingFilename(sheetName);
        exportParams.setType(ExcelType.XSSF);
        Workbook workbook = ExcelExportUtil.exportExcel(exportParams,clazz,list);
        //保存数据
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(ExcelUtil.getAbsoluteFile(fileName));
            workbook.write(fos);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return fileName;
    }

    /**
     * Workbook 导出
     * @param workbook
     * @param fileName
     * @return
     */
    public static AjaxResult export(Workbook workbook, String fileName) {
        OutputStream out = null;
        try {
            fileName = ExcelUtil.encodingFilename(fileName);
            out = new FileOutputStream(ExcelUtil.getAbsoluteFile(fileName));
            workbook.write(out);
            return AjaxResult.success(fileName);
        } catch (Exception e) {
            throw new CustomException("导出Excel失败，请联系网站管理员！");
        } finally {
            if (workbook != null) {
                try {
                    workbook.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }
}

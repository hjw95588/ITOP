package com.ebchinatech.itop.web.punch.util;

import cn.afterturn.easypoi.excel.export.styler.AbstractExcelExportStyler;
import cn.afterturn.easypoi.excel.export.styler.IExcelExportStyler;
import org.apache.poi.ss.usermodel.*;

public class MyExcelExportStyle extends AbstractExcelExportStyler implements IExcelExportStyler {

    public MyExcelExportStyle(Workbook workbook) {
        super.createStyles(workbook);
    }

    @Override
    public CellStyle getHeaderStyle(short color) {
        CellStyle titleStyle = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setFontHeightInPoints((short) 12);
        titleStyle.setFont(font);
        titleStyle.setAlignment(HorizontalAlignment.CENTER);
        titleStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        return titleStyle;
    }

    /**
     * 设置标题样式
     * @param color
     * @return
     */
    @Override
    public CellStyle getTitleStyle(short color) {
        CellStyle titleStyle = this.workbook.createCellStyle();
        Font font = this.workbook.createFont();
        //字体加粗
        font.setBold(true);
        titleStyle.setFont(font);
        titleStyle.setAlignment(HorizontalAlignment.CENTER);//居中
        titleStyle.setVerticalAlignment(VerticalAlignment.CENTER);//垂直居中
        titleStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());//设置颜色
        titleStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        titleStyle.setBorderRight(BorderStyle.THIN);
        titleStyle.setWrapText(true);
        return titleStyle;
    }

    /**
     * 对excel内容设置样式
     * @param workbook
     * @param isWarp
     * @return
     */
    @Override
    public CellStyle stringSeptailStyle(Workbook workbook, boolean isWarp) {
        CellStyle style = workbook.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setDataFormat(STRING_FORMAT);
        if (isWarp) {
            style.setWrapText(true);
        }
        return style;
    }

    /**
     * 对excel内容设置样式
     * @param workbook
     * @param isWarp
     * @return
     */
    @Override
    public CellStyle stringNoneStyle(Workbook workbook, boolean isWarp) {
        CellStyle style = workbook.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setDataFormat(STRING_FORMAT);
        if (isWarp) {
            style.setWrapText(true);
        }
        return style;
    }

}

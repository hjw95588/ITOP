package com.ebchinatech.itop.web.punch.domain;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.ebchinatech.kylinflow.utils.DateUtils;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 人员对象
 *
 * @author hjw
 * @date 2022-09-14
 */
@ToString(callSuper = true)
@Accessors(chain = true)
public class TerminationPersonImport
{

    Calendar cal = Calendar.getInstance();
    SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");

    private static final long serialVersionUID = 1L;
    @Excel(name = "工号")
    private String  workno;

    @Excel(name = "离职时间")
    private Long leaveDate;

    private String terminationDate;

    public String getWorkno() {
        return workno;
    }

    public void setWorkno(String workno) {
        this.workno = workno;
    }

    public Long getLeaveDate() {
        return leaveDate;
    }

    public void setLeaveDate(Long leaveDate) {
        this.leaveDate = leaveDate;
    }

    public String getTerminationDate() {
        return simpleDateFormat.format(new Date(this.getLeaveDate()* 1000L));
    }

}

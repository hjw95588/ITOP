package com.ebchinatech.itop.api.domain;

import java.util.Date;

public class AttendanceExternal {
    /** 工号 */
    private String jobNumber;

    /** 员工姓名 */
    private String jobName;

    /** 考勤日期 */
    private Date attendanceDate;

    /** 星期 */
    private String belongDateWeek;

    /** 班次 */
    private String workShiftCode;

    public String getWorkShiftCode() {
        if("D002".equals(workShiftCode)){
            this.workShiftCode="D004";
        }
        return workShiftCode;
    }

    /** 迟到分钟数 */
    private String comingLate;

    /** 早退分钟数 */
    private String leavingEarly;

    /** 是否旷工 */
    private String isAbsent;

    /** 旷工小时数 */
    private String absentHours;

    /** 加班小时数*/
    private String overtimeHours;

    /** 请假小时数  */
    private String XJ;

    /** 请假小时数  */
    private String QJD;

    /** 调休小时数 */
    private String TX;

    /** 公出 */
    private String GC;

    /** 当天是否打卡 */
    private String isClock;

    public String getIsClock() {
        //“日常行政班”并且“实际工作时长+公出”不等于0,说明当天打过卡
        if("D001".equals(workShiftCode) && (Double.valueOf(actualWorkingHours == null ? "0": actualWorkingHours)+Double.valueOf(GC == null ? "0": GC)!=0)){
            this.isClock = "1";
        }else {
            this.isClock = "0";
        }
        return isClock;
    }

    /** 实际工作时长 */
    private String actualWorkingHours;
    public String getQJD() {
        return QJD;
    }

    public void setQJD(String QJD) {
        this.QJD = QJD;
    }
    public String getComingLate() {
        return comingLate;
    }

    public void setComingLate(String comingLate) {
        this.comingLate = comingLate;
    }

    public String getLeavingEarly() {
        return leavingEarly;
    }

    public void setLeavingEarly(String leavingEarly) {
        this.leavingEarly = leavingEarly;
    }

    public String getAbsentHours() {
        return absentHours;
    }

    public void setAbsentHours(String absentHours) {
        this.absentHours = absentHours;
    }

    public String getOvertimeHours() {
        return overtimeHours;
    }

    public void setOvertimeHours(String overtimeHours) {
        this.overtimeHours = overtimeHours;
    }

    public String getXJ() {
        return Double.valueOf(XJ == null ? "0": XJ)+Double.valueOf(QJD == null ? "0": QJD)+"";
    }

    public void setXJ(String XJ) {
        this.XJ = XJ;
    }

    public String getTX() {
        return TX;
    }

    public void setTX(String TX) {
        this.TX = TX;
    }

    public String getGC() {
        return GC;
    }

    public void setGC(String GC) {
        this.GC = GC;
    }

    public void setActualWorkingHours(String actualWorkingHours) {
        this.actualWorkingHours = actualWorkingHours;
    }

    public String getIsAbsent() {
        return isAbsent;
    }

    public void setIsAbsent(String isAbsent) {
        this.isAbsent = isAbsent;
    }

    public String getJobNumber() {
        return jobNumber;
    }

    public void setJobNumber(String jobNumber) {
        this.jobNumber = jobNumber;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public Date getAttendanceDate() {
        return attendanceDate;
    }

    public void setAttendanceDate(Date attendanceDate) {
        this.attendanceDate = attendanceDate;
    }

    public String getBelongDateWeek() {
        return belongDateWeek;
    }

    public void setBelongDateWeek(String belongDateWeek) {
        this.belongDateWeek = belongDateWeek;
    }


    public void setWorkShiftCode(String workShiftCode) {
        this.workShiftCode = workShiftCode;
    }

    public String getActualWorkingHours() {
        return actualWorkingHours;
    }
}

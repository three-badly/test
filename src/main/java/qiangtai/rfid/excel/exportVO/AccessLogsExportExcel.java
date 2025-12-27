package qiangtai.rfid.excel.exportVO;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class AccessLogsExportExcel {

    @ExcelProperty("手机号")
    private String phoneNumber;

    @ExcelProperty("员工姓名")
    private String employeeName;

    @ExcelProperty("部门")
    private String deptName;

    @ExcelProperty("公司")
    private String companyName;

    @ExcelProperty("进出方向")
    private String direction;

    @ExcelProperty("刷卡时间")
    private String timestamp;

    @ExcelProperty("设备地址")
    private String location;
}
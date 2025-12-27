package qiangtai.rfid.excel.exportVO;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * 员工 Excel 导入模板
 */
@Data
public class EmployeesImportExcel implements Serializable {

    @NotBlank(message = "手机号不能为空")
    @ExcelProperty("手机号")
    private String phoneNumber;

    @NotBlank(message = "姓名不能为空")
    @ExcelProperty("姓名")
    private String name;

    @ExcelProperty("部门ID")
    private Integer departmentId;

    @ExcelProperty("部门名字")
    private String departmentName;

    @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}", message = "入职日期格式必须为 yyyy-MM-dd")
    @ExcelProperty("入职日期")
    private String hireDateStr;

    @ExcelProperty("用工类型")
    private String employeeType;

    @ExcelProperty("状态")
    private String status;
}
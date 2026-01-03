package qiangtai.rfid.excel.exportVO;

import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * 员工 Excel 导入模板
 * @author FEI
 */
@Data
public class EmployeesImportExcel implements Serializable {
    @NotBlank(message = "姓名不能为空")
    @ExcelProperty("姓名")
    private String name;

    @ExcelProperty("性别")
    private String sex;

    @ExcelProperty("年龄")
    private String age;

    @ExcelProperty("部门")
    private String departmentName;

    @ExcelProperty("工号")
    private String empNo;

    @ExcelProperty("职位")
    private String jobTitle;

    @NotBlank(message = "手机号不能为空")
    @ExcelProperty("手机号")
    private String phoneNumber;

    @ExcelProperty("EPC号")
    private String epc;

    @ExcelProperty("状态")
    private String status;

    @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}", message = "入职日期格式必须为 yyyy-MM-dd")
    @ExcelProperty("入职日期")
    private String hireDate;

    @ExcelProperty("用工类型")
    private String employeeType;

}
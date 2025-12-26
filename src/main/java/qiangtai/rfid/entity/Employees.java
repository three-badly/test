package qiangtai.rfid.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 员工信息表
 * @TableName employees
 */
@TableName(value ="employees")
@Data
public class Employees {
    /**
     * 员工id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 手机号 (机器读取的ID)
     */
    private String phoneNumber;

    /**
     * 员工姓名
     */
    private String name;

    /**
     * 所属部门ID
     */
    private Integer departmentId;

    /**
     * 所属公司ID
     */
    private Integer companyId;

    /**
     * 入职日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date hireDate;

    /**
     * 用工类型
     */
    private String employeeType;

    /**
     * 状态
     */
    private String status;

    /**
     * 录入时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    /**
     * 部门名字
     */
    private String departmentName;
    /**
     * 公司名字
     */
    private String companyName;


}
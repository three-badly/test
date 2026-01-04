package qiangtai.rfid.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 部门信息表
 * @author FEI
 * &#064;TableName  departments
 */
@TableName(value ="departments")
@Data
public class Departments {
    /**
     * 部门id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 部门名称
     */
    private String departmentName;

    /**
     * 所属公司ID
     */
    private Integer companyId;

    /**
     * 部门编码
     */
    private String deptCode;

    /**
     * 负责人
     */
    private String deptLeaderName;
}
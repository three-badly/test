package qiangtai.rfid.dto.req;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class DepartmentQuery extends PageRequest {
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
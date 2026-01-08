package qiangtai.rfid.dto.req;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author FEI
 */
@Data
public class EmployeesQuery extends PageRequest {


    /**
     * 搜索员工姓名/工号/手机号
     */
    @Schema(description = "搜索员工姓名/工号/手机号")
    private String name;

    /**
     * 所属部门ID
     */
    @Schema(description = "所属部门ID")
    private Integer departmentId;

    /**
     * 所属公司ID
     */
    @Schema(description = "所属公司ID")
    private Integer companyId;



    /**
     * 状态
     */
    @Schema(description = "状态")
    private String status;

}

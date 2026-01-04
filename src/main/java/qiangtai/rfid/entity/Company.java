package qiangtai.rfid.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * 公司信息实体类
 * @author FEI
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("companies")
public class Company {
    
    /**
     * 公司编号
     */
    @Schema(description = "公司编号")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    
    /**
     * 公司名称
     */
    @Schema(description = "公司名称")
    private String companyName;
    
    /**
     * 办公地址
     */
    @Schema(description = "办公地址")
    private String address;
    
    /**
     * 联系电话
     */
    @Schema(description = "联系电话")
    private String phone;
    
    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    private LocalDateTime createTime;
}

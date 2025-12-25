package qiangtai.rfid.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * 公司信息实体类
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("companies")
public class Company {
    
    /**
     * 公司编号
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    
    /**
     * 公司名称
     */
    private String companyName;
    
    /**
     * 办公地址
     */
    private String address;
    
    /**
     * 联系电话
     */
    private String phone;
    
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
}

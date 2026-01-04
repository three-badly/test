package qiangtai.rfid.dto.req;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author FEI
 */
@EqualsAndHashCode(callSuper = false)
@Data
public class DevicesQueryVO extends PageRequest {
    //待查询的参数
}

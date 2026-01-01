package qiangtai.rfid.dto.req;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

/**
 * @author FEI
 */
@Data
public class PageRequest {

    /**
     * 当前页码
     */
    private Integer current = 1;

    /**
     * 每页条数
     */
    @Min(value = 1, message = "每页条数不能小于1")
    @Max(value = 20, message = "每页条数不能大于20")
    private Integer size = 10;
}
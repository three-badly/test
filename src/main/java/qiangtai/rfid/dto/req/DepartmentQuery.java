package qiangtai.rfid.dto.req;

import lombok.Data;

@Data
public class DepartmentQuery extends PageRequst {
    /**
     * 公司名称模糊查询
     */
    private String departmentName;
}
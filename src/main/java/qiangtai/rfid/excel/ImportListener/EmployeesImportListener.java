package qiangtai.rfid.excel.ImportListener;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import lombok.Getter;
import qiangtai.rfid.context.UserContext;
import qiangtai.rfid.entity.Employees;
import qiangtai.rfid.excel.exportVO.EmployeesImportExcel;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.*;

/**
 * 逐行读取并校验，最后一次性批量插入
 */
public class EmployeesImportListener extends AnalysisEventListener<EmployeesImportExcel> {

    private static final Validator VALIDATOR = Validation.buildDefaultValidatorFactory().getValidator();

    /* 成功数据 */
    @Getter
    private final List<Employees> successList = new ArrayList<>();
    /* 错误信息 行号:错误描述 */
    @Getter
    private final Map<Integer, String> errorMap = new LinkedHashMap<>();


    @Override
    public void invoke(EmployeesImportExcel data, AnalysisContext context) {
        int row = context.readRowHolder().getRowIndex() + 1;

        /* 1. JSR380 校验 */
        Set<ConstraintViolation<EmployeesImportExcel>> violations = VALIDATOR.validate(data);
        if (!violations.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            violations.forEach(v -> sb.append(v.getMessage()).append(";"));
            errorMap.put(row, sb.toString());
            return;
        }

        successList.add(BeanUtil.copyProperties(data, Employees.class));
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        // 空实现，批量保存交给 service
    }

}
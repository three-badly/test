package qiangtai.rfid.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import qiangtai.rfid.context.UserContext;
import qiangtai.rfid.dto.req.DevicesQueryVO;
import qiangtai.rfid.dto.result.DevicesSaveVO;
import qiangtai.rfid.entity.Devices;
import qiangtai.rfid.handler.exception.BusinessException;
import qiangtai.rfid.service.DevicesService;
import qiangtai.rfid.mapper.DevicesMapper;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
* @author FEI
* @description 针对表【devices(门禁设备表)】的数据库操作Service实现
* @createDate 2025-12-24 12:44:25
*/
@Service
public class DevicesServiceImpl extends ServiceImpl<DevicesMapper, Devices>
    implements DevicesService{


    private final DevicesMapper devicesMapper;

    public DevicesServiceImpl(DevicesMapper devicesMapper) {
        this.devicesMapper = devicesMapper;
    }

    @Override
    public List<Devices> listDevice() {
        Integer companyId = UserContext.get().getCompanyId();
        return devicesMapper.selectList(Wrappers.<Devices>lambdaQuery().eq(Devices::getCompanyId, companyId));
    }

    @Override
    public Page<Devices> pageDevice(DevicesQueryVO devicesQueryVO) {
        Integer companyId = UserContext.get().getCompanyId();

        Page<Devices> page = new Page<>(devicesQueryVO.getCurrent(), devicesQueryVO.getSize());
        //平台查全部
        if (companyId == -1){
            return this.page(page, Wrappers.<Devices>emptyWrapper());
        }
        LambdaQueryWrapper<Devices> eq = Wrappers.<Devices>lambdaQuery().eq(Devices::getCompanyId, companyId);
        return devicesMapper.selectPage(page, eq);
    }

    @Override
    public Boolean add(DevicesSaveVO devicesSaveVO) {
        Devices devices = BeanUtil.copyProperties(devicesSaveVO, Devices.class);
        devices.setCompanyId(UserContext.get().getCompanyId());
        int insert = devicesMapper.insert(devices);
        return insert > 0;
    }

    @Override
    public Boolean deleteDevice(Integer id) {
        Integer companyId = UserContext.get().getCompanyId();

        //限定权限
        int delete = devicesMapper.delete(Wrappers.<Devices>lambdaQuery()
                .eq(Devices::getId, id)
                .eq(Devices::getCompanyId, companyId)
        );
        if (delete <= 0){
            throw new BusinessException(10023, "不能删除非公司的设备");
        }
        return true;
    }
}





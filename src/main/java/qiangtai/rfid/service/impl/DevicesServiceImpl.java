package qiangtai.rfid.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import qiangtai.rfid.dto.req.DevicesQueryVO;
import qiangtai.rfid.dto.result.DevicesSaveVO;
import qiangtai.rfid.entity.Devices;
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
        //暂时筛选公司id为1
        List<Devices> devices = devicesMapper.selectList(Wrappers.<Devices>lambdaQuery().eq(Devices::getCompanyId, 1));
        return devices;
    }

    @Override
    public Page<Devices> pageDevice(DevicesQueryVO devicesQueryVO) {
        //暂时筛选公司id为1
        LambdaQueryWrapper<Devices> eq = Wrappers.<Devices>lambdaQuery().eq(Devices::getCompanyId, 1);
        Page<Devices> page = new Page<>(devicesQueryVO.getCurrent(), devicesQueryVO.getSize());
        return devicesMapper.selectPage(page, eq);
    }

    @Override
    public Boolean add(DevicesSaveVO devicesSaveVO) {
        Devices devices = BeanUtil.copyProperties(devicesSaveVO, Devices.class);
        //todo 上下文添加公司id
        int insert = devicesMapper.insert(devices);
        return insert > 0;
    }

    @Override
    public Boolean deleteDevice(Integer id) {
        //todo 删除权限控制
        int delete = devicesMapper.deleteById(id);
        return delete > 0;
    }
}





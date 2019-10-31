package com.fh.shop.area.biz;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fh.shop.area.mapper.IAreaMapper;
import com.fh.shop.area.po.Area;
import com.fh.shop.common.ServerResponse;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service("areaService")
public class IAreaServiceImpl implements IAreaService {
    @Resource
    private IAreaMapper areaMapper;

    @Override
    public ServerResponse queryAreaList() {
        List<Area> areas = areaMapper.selectList(null);
        return ServerResponse.success(areas);
    }

    @Override
    public ServerResponse add(Area area) {
        areaMapper.insert(area);
        return ServerResponse.success();
    }

    @Override
    public ServerResponse del(Long id) {
        areaMapper.deleteById(id);
        return ServerResponse.success();
    }

    @Override
    public ServerResponse queryById(Long id) {
        areaMapper.selectById(id);
        return ServerResponse.success();
    }

    @Override
    public ServerResponse update(Area area) {
        areaMapper.updateById(area);
        return ServerResponse.success();
    }

    @Override
    public ServerResponse delBatch(String ids) {
        String[] split = ids.split(",");
        List<Long> list = new ArrayList<>();
        for (String s : split) {
            list.add(Long.parseLong(s));
        }
        areaMapper.deleteBatchIds(list);
        return ServerResponse.success();
    }

    @Override
    public ServerResponse queryArea(Long id) {
        QueryWrapper<Area> areaQueryWrapper = new QueryWrapper<>();
        areaQueryWrapper.eq("pid", id);
        List<Area> areaList = areaMapper.selectList(areaQueryWrapper);
        return ServerResponse.success(areaList);
    }
}

package com.fh.shop.area.biz;

import com.fh.shop.area.po.Area;
import com.fh.shop.common.ServerResponse;

public interface IAreaService {
    ServerResponse queryAreaList();

    ServerResponse add(Area area);

    ServerResponse del(Long id);

    ServerResponse queryById(Long id);

    ServerResponse update(Area area);

    ServerResponse delBatch(String ids);

    ServerResponse queryArea(Long id);
}

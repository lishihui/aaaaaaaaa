package com.fh.shop.classify.biz;

import com.fh.shop.classify.mapper.IClassifyMapper;
import com.fh.shop.classify.po.Classify;
import com.fh.shop.common.ServerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("classifyService")
public class IClassifyServiceImpl implements IClassifyService {
    @Autowired
    private IClassifyMapper classifyMapper;

    @Override
    public ServerResponse queryClassifyList() {
        List<Classify> classifyList = classifyMapper.selectList(null);
        return ServerResponse.success(classifyList);
    }
}

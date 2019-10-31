package com.fh.shop.token.biz;


import com.fh.shop.common.ServerResponse;
import com.fh.shop.utils.JedisUtils;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service("tokenService")
public class ITokenServiceImpl implements ITokenService {

    @Override
    public ServerResponse createToken(Long vipVoId) {
        String token = UUID.randomUUID().toString();
        JedisUtils.set(token, token);
        return ServerResponse.success(token);
    }
}

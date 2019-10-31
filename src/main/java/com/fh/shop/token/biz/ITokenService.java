package com.fh.shop.token.biz;

import com.fh.shop.common.ServerResponse;

public interface ITokenService {
    ServerResponse createToken(Long vipVoId);

}

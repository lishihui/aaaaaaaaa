package com.fh.shop.vip.biz;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fh.shop.common.ResponseEnum;
import com.fh.shop.common.ServerResponse;
import com.fh.shop.common.SystemConstant;
import com.fh.shop.utils.JedisUtils;
import com.fh.shop.utils.KeyUtil;
import com.fh.shop.utils.Md5Util;
import com.fh.shop.utils.SMSUtil;
import com.fh.shop.vip.mapper.IVipMapper;
import com.fh.shop.vip.po.Member;
import com.fh.shop.vip.po.Vip;
import com.fh.shop.vip.po.VipVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Base64;
import java.util.UUID;

@Service("vipService")
public class IVipServiceImpl implements IVipService {
    @Resource
    private IVipMapper vipMapper;

    @Override
    //发送短信
    public ServerResponse smsCode(String phone) {
        if (StringUtils.isEmpty(phone)) {
            return ServerResponse.error(ResponseEnum.PHONE_IS_NULL);
        }
        // java、正则验证手机号格式是否正确
        /*Pattern pattern = Pattern.compile("^[1]\\d{10}$");*/
        //根据手机号发送验证码
        String result = SMSUtil.sentSMS(phone);
        Member member = JSONObject.parseObject(result, Member.class);
        int code = member.getCode();
        if (code != 200) {
            return ServerResponse.error(ResponseEnum.CODE_IS_ERROR);
        }
        String memberObj = member.getObj();
        JedisUtils.setSeconds(KeyUtil.buildCodeKey(phone), SystemConstant.CODE_EXPIRE, memberObj);
        return ServerResponse.success();
    }

    @Override
    public ServerResponse add(Vip vip) {
        String email = vip.getEmail();
        String phone = vip.getPhone();
        String realName = vip.getRealName();
        String smsCode = vip.getSmsCode();
        //为空判断
        if (StringUtils.isEmpty(realName)) {
            return ServerResponse.error(ResponseEnum.REALNAME_IS_NULL);
        }
        if (StringUtils.isEmpty(email)) {
            return ServerResponse.error(ResponseEnum.EMAIL_IS_NULL);
        }
        if (StringUtils.isEmpty(phone)) {
            return ServerResponse.error(ResponseEnum.PHONE_IS_NULL);
        }
        //取出数据，判断验证码是否一致
        String result = JedisUtils.get(KeyUtil.buildCodeKey(phone));
        if (StringUtils.isEmpty(result)) {
            return ServerResponse.error(ResponseEnum.CODE_GUOQI);
        }
        if (!smsCode.equals(result)) {
            return ServerResponse.error(ResponseEnum.CODES_IS_ERROR);
        }

        QueryWrapper<Vip> vipQueryWrapperEmail = new QueryWrapper<>();
        vipQueryWrapperEmail.eq("email", email);
        Vip vipEmail = vipMapper.selectOne(vipQueryWrapperEmail);
        if (vipEmail != null) {
            return ServerResponse.error(ResponseEnum.EMAIL_EXRST);
        }

        QueryWrapper<Vip> vipQueryWrapperPhone = new QueryWrapper<>();
        vipQueryWrapperPhone.eq("phone", phone);
        Vip vipPhone = vipMapper.selectOne(vipQueryWrapperPhone);
        if (vipPhone != null) {
            return ServerResponse.error(ResponseEnum.PHONE_EXRST);
        }

        QueryWrapper<Vip> vipQueryWrapperRealName = new QueryWrapper<>();
        vipQueryWrapperRealName.eq("realName", realName);
        Vip vipRealName = vipMapper.selectOne(vipQueryWrapperRealName);
        if (vipRealName != null) {
            return ServerResponse.error(ResponseEnum.REALNAME_EXRST);
        }
        vipMapper.insert(vip);
        return ServerResponse.success();
    }

    @Override
    public ServerResponse login(Vip vip) {
        String userName = vip.getUserName();
        String password = vip.getPassword();
        //非空判断
        if (StringUtils.isEmpty(userName)) {
            return ServerResponse.error(ResponseEnum.USERNAME_IS_NULL);
        }
        if (StringUtils.isEmpty(password)) {
            return ServerResponse.error(ResponseEnum.PASSWORD_IS_NULL);
        }
        //验证唯一性
        QueryWrapper<Vip> vipQueryWrapperUserName = new QueryWrapper<>();
        vipQueryWrapperUserName.eq("userName", userName);
        Vip vipUserName = vipMapper.selectOne(vipQueryWrapperUserName);
        if (vipUserName == null) {
            return ServerResponse.error(ResponseEnum.USERNAME_ERROR);
        }
        if (!password.equals(vipUserName.getPassword())) {
            return ServerResponse.error(ResponseEnum.PASSWORD_ERROR);
        }
        VipVo vipVo = new VipVo();
        vipVo.setUserName(userName);
        String uuid = UUID.randomUUID().toString();
        vipVo.setUuid(uuid);
        vipVo.setRealName(vipUserName.getRealName());
        vipVo.setId(vipUserName.getId());
        //将对象转换成json格式的字符串
        String jsonObject = JSONObject.toJSONString(vipVo);
        //编译
        String encode = Base64.getEncoder().encodeToString(jsonObject.getBytes());

        //加密
        String buildSign = Md5Util.buildSign(encode, SystemConstant.SECRET);
        //加密之后在进行编译
        String newEncode = Base64.getEncoder().encodeToString(buildSign.getBytes());

        //将其存入到redis  中
        JedisUtils.setSeconds(KeyUtil.buildVipKey(userName, uuid), SystemConstant.CODE_EXPIRE, "1");
        //通过 点  来将两个值进行分割
        return ServerResponse.success(encode + "." + newEncode);
    }

    @Override
    public ServerResponse loginPhone(Vip vip, String code) {
        String phone = vip.getPhone();
        if (StringUtils.isEmpty(phone)) {
            return ServerResponse.error(ResponseEnum.VIP_PHONE_ISNULL);
        }
        if (StringUtils.isEmpty(code)) {
            return ServerResponse.error(ResponseEnum.VIP_NOTE_IS_NULL);
        }
        //验证验证码是否正确
        String node = JedisUtils.get(KeyUtil.buildCodeKey(phone));
        if (StringUtils.isEmpty(node)) {
            return ServerResponse.error(ResponseEnum.VIP_SMS_ISEXPIRE);
        }
        if (!node.equals(code)) {
            return ServerResponse.error(ResponseEnum.CODES_IS_ERROR);
        }

        //验证会员是否存在
        QueryWrapper<Vip> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("phone", phone);
        Vip vipName = vipMapper.selectOne(queryWrapper);
        if (vipName == null) {
            return ServerResponse.error(ResponseEnum.USERNAME_ERROR);
        }

        String uuid = UUID.randomUUID().toString();
        Long id = vipName.getId();
        String realName = vipName.getRealName();
        String userName = vipName.getUserName();
        VipVo memberVo = new VipVo();
        memberVo.setId(id);
        memberVo.setRealName(realName);
        memberVo.setUserName(userName);
        memberVo.setUuid(uuid);

        //将对象转换成json格式的字符串
        String jsonObject = JSONObject.toJSONString(memberVo);
        //编译
        String encode = Base64.getEncoder().encodeToString(jsonObject.getBytes());

        //加密
        String buildSign = Md5Util.buildSign(encode, SystemConstant.SECRET);
        //加密之后在进行编译
        String newEncode = Base64.getEncoder().encodeToString(buildSign.getBytes());

        //将其存入到redis  中
        JedisUtils.setSeconds(KeyUtil.buildVipKey(userName, uuid), SystemConstant.CODE_EXPIRE, "1");
        //通过 点  来将两个值进行分割
        return ServerResponse.success(encode + "." + newEncode);
    }
}

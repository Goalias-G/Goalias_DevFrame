package com.dev.model.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.aliyun.dysmsapi20170525.Client;
import com.aliyun.dysmsapi20170525.models.SendSmsRequest;
import com.aliyun.dysmsapi20170525.models.SendSmsResponse;
import com.aliyun.tea.TeaException;
import com.aliyun.teautil.models.RuntimeOptions;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dev.model.mapper.UserMapper;
import com.dev.model.pojo.dto.PageQueryDto;
import com.dev.model.pojo.dto.PhoneLoginDto;
import com.dev.model.pojo.dto.UserDto;
import com.dev.model.pojo.entity.User;
import com.dev.model.pojo.vo.LoginVO;
import com.dev.model.pojo.vo.UserVo;
import com.dev.model.properties.JwtProperties;
import com.dev.model.service.IUserService;
import com.dev.model.utils.JwtUtil;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author gws
 * @since 2024-03-10
 */
@Service
@Primary
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
    @Resource
    private UserMapper userMapper;
    @Resource
    private JwtProperties jwtProperties;

    @Override
    public LoginVO login(PhoneLoginDto phoneLoginDto) {
        String phoneNum = phoneLoginDto.getPhoneNum();
        String password = phoneLoginDto.getPassword();
        QueryWrapper<User> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("phone_number",phoneNum);
        User user = userMapper.selectOne(queryWrapper);
        if (user == null || !password.equals(user.getPassword())) {
            return null;
        }
        Map<String,Object> claims=new HashMap<>(2);
        claims.put("userId",user.getId());
        String jwt = JwtUtil.createJWT(jwtProperties.getUserSecretKey(),
                jwtProperties.getUserTtl(), claims);
        LoginVO loginVO = LoginVO.builder()
                .id(user.getId().longValue())
                .account(user.getAccount())
                .token(jwt)
                .build();
        return loginVO;
    }

    @Override
    public UserVo userPageQuery(PageQueryDto pageQueryDto) {
        IPage<User> page=new Page<>(pageQueryDto.getPage(), pageQueryDto.getPageSize());
        IPage<User> userPage=userMapper.userPageQuery(page,pageQueryDto);
        UserVo userVo = UserVo.builder()
                .userList(userPage.getRecords())
                .total(userPage.getTotal())
                .build();
        return userVo;
    }

    @Override
    public void change(UserDto userDto) {
        userMapper.change(userDto);
    }

    @Override
    public void sendCode(String phone) {
        String message = null;
        Random random=new Random();
        String code = RandomUtil.randomNumbers(6);
        SendSmsRequest sendSmsRequest = new com.aliyun.dysmsapi20170525.models.SendSmsRequest()
                .setPhoneNumbers(phone)
                .setSignName("dev_frame")
                .setTemplateCode("SMS_465407442")
                .setTemplateParam("{'code':"+code+"}");

        Client client = null;
        try {
            client = createClient();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            // 复制代码运行请自行打印 API 的返回值
            SendSmsResponse sendSmsResponse = client.sendSmsWithOptions(sendSmsRequest, new RuntimeOptions());
            message = sendSmsResponse.getBody().getMessage();
        } catch (TeaException error) {
            // 此处仅做打印展示，请谨慎对待异常处理，在工程项目中切勿直接忽略异常。
            // 错误 message
            System.out.println(error.getMessage());
            // 诊断地址
            System.out.println(error.getData().get("Recommend"));
            com.aliyun.teautil.Common.assertAsString(error.message);
        } catch (Exception _error) {
            TeaException error = new TeaException(_error.getMessage(), _error);
            // 此处仅做打印展示，请谨慎对待异常处理，在工程项目中切勿直接忽略异常。
            // 错误 message
            System.out.println(error.getMessage());
            // 诊断地址
            System.out.println(error.getData().get("Recommend"));
            com.aliyun.teautil.Common.assertAsString(error.message);
        }
        if (message != null && message.equals("OK")) {
            UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("phone_number", phone).set("password", code);//用redis设置过期时间最好
            userMapper.update(updateWrapper);
        }
    }

    public  com.aliyun.dysmsapi20170525.Client createClient() throws Exception {
        // 工程代码泄露可能会导致 AccessKey 泄露，并威胁账号下所有资源的安全性。以下代码示例仅供参考，建议使用更安全的 STS 方式，更多鉴权访问方式请参见：https://help.aliyun.com/document_detail/378657.html。
        com.aliyun.teaopenapi.models.Config config = new com.aliyun.teaopenapi.models.Config()
                // 必填，请确保代码运行环境设置了环境变量 ALIBABA_CLOUD_ACCESS_KEY_ID。
                .setAccessKeyId("LTAI5tRqNf5HmrE8iT24br6J")
                // 必填，请确保代码运行环境设置了环境变量 ALIBABA_CLOUD_ACCESS_KEY_SECRET。
                .setAccessKeySecret("qSg26ZQHcMbHK0NH2SUzOP1E3VgdwH");
        // Endpoint 请参考 https://api.aliyun.com/product/Dysmsapi
        config.endpoint = "dysmsapi.aliyuncs.com";
        return new com.aliyun.dysmsapi20170525.Client(config);
    }



}

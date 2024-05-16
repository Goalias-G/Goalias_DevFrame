package com.aigc.vrmetal.service.impl;

import com.aigc.vrmetal.pojo.dto.PageQueryDto;
import com.aigc.vrmetal.pojo.dto.PhoneLoginDto;
import com.aigc.vrmetal.pojo.entity.Doctor;
import com.aigc.vrmetal.mapper.DoctorMapper;
import com.aigc.vrmetal.pojo.vo.DoctorVo;
import com.aigc.vrmetal.pojo.vo.LoginVO;
import com.aigc.vrmetal.properties.JwtProperties;
import com.aigc.vrmetal.service.IDoctorService;
import com.aigc.vrmetal.utils.JwtUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author gws
 * @since 2024-03-14
 */
@Service
public class DoctorServiceImpl extends ServiceImpl<DoctorMapper, Doctor> implements IDoctorService {
    @Resource
    private DoctorMapper doctorMapper;
    @Resource
    private JwtProperties jwtProperties;

    @Override
    public LoginVO login(PhoneLoginDto phoneLoginDto) {

        String phoneNum = phoneLoginDto.getPhoneNum();
        String password = phoneLoginDto.getPassword();
        QueryWrapper<Doctor> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("phone_num",phoneNum);
        Doctor doctor = doctorMapper.selectOne(queryWrapper);
        if (doctor == null || !password.equals(doctor.getPassword())) {
            return null;
        }
        Map<String,Object> claims=new HashMap<>(2);
        claims.put("doctorId",doctor.getId());
        String jwt = JwtUtil.createJWT(jwtProperties.getUserSecretKey(),
                jwtProperties.getUserTtl(), claims);
        LoginVO loginVO = LoginVO.builder()
                .id(doctor.getId().longValue())
                .token(jwt)
                .build();
        return loginVO;
    }

    @Override
    public DoctorVo userPageQuery(PageQueryDto pageQueryDto) {
        IPage<Doctor> page=new Page<>(pageQueryDto.getPage(), pageQueryDto.getPageSize());
        IPage<Doctor> doctorIPage = doctorMapper.selectPage(page, new QueryWrapper<>(Doctor.class));
        DoctorVo doctorVo = DoctorVo.builder().doctorList(doctorIPage.getRecords())
                .total(doctorIPage.getTotal()).build();
        return doctorVo;
    }

    @Override
    public void change(Doctor doctorDto) {
        doctorMapper.reChange(doctorDto);
    }
}

package com.aigc.vrmetal.service;

import com.aigc.vrmetal.pojo.entity.ChatEffect;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author gws
 * @since 2024-03-11
 */
public interface IChatEffectService extends IService<ChatEffect> {

    void compare(Long userId, Integer emo);
}

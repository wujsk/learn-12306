package com.learn.index12306.framework.starter.bizs.userservice.service;

import com.learn.index12306.framework.starter.bizs.userservice.dao.dto.request.PassengerRemoveReqDTO;
import com.learn.index12306.framework.starter.bizs.userservice.dao.dto.request.PassengerReqDTO;
import com.learn.index12306.framework.starter.bizs.userservice.dao.dto.response.PassengerActualRespDTO;
import com.learn.index12306.framework.starter.bizs.userservice.dao.dto.response.PassengerRespDTO;

import java.util.List;

/**
 * @author: cyy
 * @create: 2025-03-23 21:00
 * @description: 乘车人接口层
 **/
public interface PassengerService {

    /**
     * 根据用户名查询乘车人列表
     * @param username 用户名
     * @return 乘车人返回列表
     */
    List<PassengerRespDTO> listPassengerQueryByUsername(String username);

    /**
     * 根据乘车人 ID 集合查询乘车人列表
     * @param username 用户名
     * @param ids      乘车人 ID 集合
     * @return 乘车人返回列表
     */
    List<PassengerActualRespDTO> listPassengerQueryByIds(String username, List<Long> ids);

    /**
     * 新增乘车人
     * @param requestParam 乘车人信息
     */
    void savePassenger(PassengerReqDTO requestParam);

    /**
     * 修改乘车人
     * @param requestParam 乘车人信息
     */
    void updatePassenger(PassengerReqDTO requestParam);

    /**
     * 移除乘车人
     * @param requestParam 移除乘车人信息
     */
    void removePassenger(PassengerRemoveReqDTO requestParam);
}

package com.learn.index12306.framework.starter.bizs.userservice.controller;

import com.learn.index12306.framework.starter.bizs.userservice.dao.dto.request.PassengerRemoveReqDTO;
import com.learn.index12306.framework.starter.bizs.userservice.dao.dto.request.PassengerReqDTO;
import com.learn.index12306.framework.starter.bizs.userservice.dao.dto.response.PassengerActualRespDTO;
import com.learn.index12306.framework.starter.bizs.userservice.dao.dto.response.PassengerRespDTO;
import com.learn.index12306.framework.starter.bizs.userservice.service.PassengerService;
import com.learn.index12306.framework.starter.convention.result.Result;
import com.learn.index12306.framework.starter.idempotent.annotation.Idempotent;
import com.learn.index12306.framework.starter.idempotent.enums.IdempotentSceneEnum;
import com.learn.index12306.framework.starter.idempotent.enums.IdempotentTypeEnum;
import com.learn.index12306.framework.starter.user.core.UserContext;
import com.learn.index12306.framework.starter.web.Results;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author: cyy
 * @create: 2025-03-23 20:54
 **/
@Tag(name = "乘车人相关接口", description = "乘车人相关接口")
@RestController("/passenger/")
@RequiredArgsConstructor
public class PassengerController {

    private final PassengerService passengerService;

    /**
     * 根据用户名查询乘车人列表
     */
    @Schema(description = "根据用户名查询乘车人列表")
    @GetMapping("query")
    public Result<List<PassengerRespDTO>> listPassengerQueryByUsername() {
        return Results.success(passengerService.listPassengerQueryByUsername(UserContext.getUsername()));
    }

    /**
     * 根据乘车人 ID 集合查询乘车人列表
     */
    @Schema(description = "根据乘车人 ID 集合查询乘车人列表")
    @GetMapping("actual/query/ids")
    public Result<List<PassengerActualRespDTO>> listPassengerQueryByIds(@RequestParam("username") String username, @RequestParam("ids") List<Long> ids) {
        return Results.success(passengerService.listPassengerQueryByIds(username, ids));
    }

    /**
     * 新增乘车人
     */
    @Schema(description = "新增乘车人")
    @Idempotent(
            uniqueKeyPrefix = "index12306-user:lock_passenger-alter:",
            key = "T(com.learn.index12306.framework.starter.user.core.UserContext).getUsername()",
            type = IdempotentTypeEnum.SPEL,
            scene = IdempotentSceneEnum.RESTAPI,
            message = "正在新增乘车人，请稍后再试..."
    )
    @PostMapping("save")
    public Result<Void> savePassenger(@RequestBody PassengerReqDTO requestParam) {
        passengerService.savePassenger(requestParam);
        return Results.success();
    }

    /**
     * 修改乘车人
     */
    @Schema(description = "修改乘车人")
    @Idempotent(
            uniqueKeyPrefix = "index12306-user:lock_passenger-alter:",
            key = "T(com.learn.index12306.framework.starter.user.core.UserContext).getUsername()",
            type = IdempotentTypeEnum.SPEL,
            scene = IdempotentSceneEnum.RESTAPI,
            message = "正在修改乘车人，请稍后再试..."
    )
    @PostMapping("update")
    public Result<Void> updatePassenger(@RequestBody PassengerReqDTO requestParam) {
        passengerService.updatePassenger(requestParam);
        return Results.success();
    }

    /**
     * 移除乘车人
     */
    @Schema(description = "移除乘车人")
    @Idempotent(
            uniqueKeyPrefix = "index12306-user:lock_passenger-alter:",
            key = "T(com.learn.index12306.framework.starter.user.core.UserContext).getUsername()",
            type = IdempotentTypeEnum.SPEL,
            scene = IdempotentSceneEnum.RESTAPI,
            message = "正在移除乘车人，请稍后再试..."
    )
    @PostMapping("remove")
    public Result<Void> removePassenger(@RequestBody PassengerRemoveReqDTO requestParam) {
        passengerService.removePassenger(requestParam);
        return Results.success();
    }
}

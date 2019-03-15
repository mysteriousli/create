package edu.qit.cloudclass.tool;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author nic
 * @version 1.0
 */
@Getter
@AllArgsConstructor
public enum JsonState {
    /**
     * 成功
     */
    SUCCESS(0,"success"),
    /**
     * 未知异常
     */
    UNKNOWN_ERROR(-1,"unknown error"),
    /**
     * 缺少必要参数
     */
    MISSING_REQUIRED_PARAM(-2,"missing required param"),
    /**
     * 权限不足(含登录时账号或密码错误)
     */
    PERMISSION_DENIED(-3,"permission denied");

    private int code;
    private String msg;
}

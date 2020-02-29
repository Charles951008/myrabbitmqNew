package com.wsl.constant;

/**
 *
 * @ClassName:	LogRootConstant
 * @Description:	日志流向
 */
public enum LogRootConstant {
    /**
     * SYSTEM_LOG: 系统日志key
     */
    SYSTEM_LOG("LOG_ROOT_CONSTANT_SYSTEM_LOG"),
    /**
     * BUSINESS_LOG: 业务日志key
     */
    BUSINESS_LOG("LOG_ROOT_CONSTANT_BUSINESS_LOG");
    private String logRootName;

    private LogRootConstant(String logRootName) {
        this.logRootName = logRootName;
    }

    public String getLogRootName() {
        return logRootName;
    }


}

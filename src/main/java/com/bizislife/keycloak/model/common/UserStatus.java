package com.bizislife.keycloak.model.common;

import org.apache.commons.lang3.StringUtils;

public enum UserStatus {
    PREPARE("PRE"),
    INFORMED("INF")
    ;

    private String code;

    UserStatus(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public static UserStatus fromCode(String code) {
        if (StringUtils.isNotEmpty(code)) {
            for (UserStatus status : UserStatus.values()) {
                if (status.getCode().equals(code))
                    return status;
            }
        }
        return null;
    }
}

package com.cxf.modules.sys.entity;

import lombok.Data;

import java.util.Date;

/**
 * 認証コード
 *
 * @author cxf
 */
@Data
public class SysCaptchaEntity {
    private String uuid;
    /**
     * 認証コード
     */
    private String code;
    /**
     * 有効期限
     */
    private Date expireTime;

}

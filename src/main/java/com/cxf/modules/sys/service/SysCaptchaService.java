package com.cxf.modules.sys.service;

import java.awt.image.BufferedImage;

/**
 * 認証コード
 *
 * @author cxf
 */
public interface SysCaptchaService {

    /**
     * 画像認証コードを取得
     */
    BufferedImage getCaptcha(String uuid);

    /**
     * 認証コードを検証
     * @param uuid  uuid
     * @param code  認証コード
     * @return  true：成功  false：失敗
     */
    boolean validate(String uuid, String code);
}

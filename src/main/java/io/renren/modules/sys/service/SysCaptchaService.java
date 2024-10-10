package io.renren.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.modules.sys.entity.SysCaptchaEntity;

import java.awt.image.BufferedImage;

/**
 * 認証コード
 *
 * @author cxf
 */
public interface SysCaptchaService extends IService<SysCaptchaEntity> {

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

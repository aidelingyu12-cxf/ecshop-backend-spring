package com.cxf.modules.sys.service.impl;


import com.google.code.kaptcha.Producer;
import com.cxf.common.exception.RRException;
import com.cxf.common.utils.DateUtils;
import com.cxf.modules.sys.dao.SysCaptchaDao;
import com.cxf.modules.sys.entity.SysCaptchaEntity;
import com.cxf.modules.sys.service.SysCaptchaService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;
import java.util.Date;

import javax.annotation.Resource;

/**
 * 認証コード
 *
 * @author cxf
 */
@Service("sysCaptchaService")
public class SysCaptchaServiceImpl implements SysCaptchaService {
    @Autowired
    private Producer producer;
    
    @Resource
    private SysCaptchaDao sysCaptchaDao;

    @Override
    public BufferedImage getCaptcha(String uuid) {
        if(StringUtils.isBlank(uuid)){
            throw new RRException("uuidはヌルデータです。");
        }
        //文字の認証コードを生成する
        String code = producer.createText();
        //認証コードを設定する
        SysCaptchaEntity captchaEntity = new SysCaptchaEntity();
        captchaEntity.setUuid(uuid);
        captchaEntity.setCode(code);
        //有効期限を５分で設定する
        captchaEntity.setExpireTime(DateUtils.addDateMinutes(new Date(), 5));
        sysCaptchaDao.save(captchaEntity);

        return producer.createImage(code);
    }

    @Override
    public boolean validate(String uuid, String code) {
    	SysCaptchaEntity captchaEntity = sysCaptchaDao.getCaptcha(uuid);
        if(captchaEntity == null){
            return false;
        }

        //認証コードを削除する
        sysCaptchaDao.removeById(uuid);

        if(captchaEntity.getCode().equalsIgnoreCase(code) && captchaEntity.getExpireTime().getTime() >= System.currentTimeMillis()){
            return true;
        }

        return false;
    }
}

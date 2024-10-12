package com.cxf.modules.sys.service.impl;

import com.cxf.common.utils.R;
import com.cxf.modules.sys.dao.SysUserTokenDao;
import com.cxf.modules.sys.entity.SysUserTokenEntity;
import com.cxf.modules.sys.jwt.TokenGenerator;
import com.cxf.modules.sys.service.SysUserTokenService;
import org.springframework.stereotype.Service;

import java.util.Date;

import javax.annotation.Resource;


@Service("sysUserTokenService")
public class SysUserTokenServiceImpl implements SysUserTokenService {
	
	@Resource
	private SysUserTokenDao sysUserTokenDao;
	//12時間後有効期限切れ
	private final static int EXPIRE = 3600 * 12;


	@Override
	public R createToken(long userId) {
		//tokenを生成する
		String token = TokenGenerator.generateValue();

		//現在の時間
		Date now = new Date();
		//有効期間
		Date expireTime = new Date(now.getTime() + EXPIRE * 1000);

		SysUserTokenEntity tokenEntity = sysUserTokenDao.getById(userId);
		//tokenが既に存在するかを判断する
		if(tokenEntity == null){
			//tokenが存在しない場合、新しいtokenを設定する
			tokenEntity = new SysUserTokenEntity();
			tokenEntity.setUserId(userId);
			tokenEntity.setToken(token);
			tokenEntity.setUpdateTime(now);
			tokenEntity.setExpireTime(expireTime);

			//tokenを保存する
			sysUserTokenDao.save(tokenEntity);
		}else{
			//tokenが存在する場合、古いtokenを更新する
			tokenEntity.setToken(token);
			tokenEntity.setUpdateTime(now);
			tokenEntity.setExpireTime(expireTime);

			//tokenを更新する
			sysUserTokenDao.updateById(tokenEntity);
		}

		R r = R.ok().put("token", token).put("expire", EXPIRE);

		return r;
	}

	@Override
	public void logout(long userId) {
		//tokenを生成する
		String token = TokenGenerator.generateValue();

		//tokenを更新する
		SysUserTokenEntity tokenEntity = new SysUserTokenEntity();
		tokenEntity.setUserId(userId);
		tokenEntity.setToken(token);
		sysUserTokenDao.updateById(tokenEntity);
	}
}

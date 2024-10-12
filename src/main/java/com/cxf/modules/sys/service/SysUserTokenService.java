package com.cxf.modules.sys.service;

import com.cxf.common.utils.R;

/**
 * Token
 *
 * @author cxf
 */
public interface SysUserTokenService {

	/**
	 * tokenを生成する
	 * @param userId  ユーザーID
	 */
	R createToken(long userId);

	/**
	 * ログアウト
	 * @param userId  ユーザーID
	 */
	void logout(long userId);

}

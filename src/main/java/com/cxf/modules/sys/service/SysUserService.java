/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package com.cxf.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cxf.common.utils.PageUtils;
import com.cxf.common.utils.PageUtil;
import com.cxf.modules.sys.entity.SysUserEntity;

import java.util.List;
import java.util.Map;


/**
 * 系统用户
 *
 * @author Mark sunlightcs@gmail.com
 */
public interface SysUserService extends IService<SysUserEntity> {

	PageUtils queryPage(Map<String, Object> params);
	
	PageUtil queryByPage(Map<String, String> params);
	/**
	 * 查询用户的所有权限
	 * @param userId  用户ID
	 */
	List<String> queryAllPerms(Long userId);
	
	/**
	 * 查询用户的所有菜单ID
	 */
	List<Long> queryAllMenuId(Long userId);

	/**
	 * ユーザネームによって、ユーザーの情報を抽出する
	 */
	SysUserEntity queryByUserName(String username);

	/**
	 * ユーザー情報を保存する
	 */
	void saveUser(SysUserEntity user);
	
	/**
	 * ユーザー情報を変える
	 */
	void update(SysUserEntity user);
	
	/**
	 * 複数ユーザーを削除する
	 */
	void deleteBatch(Long[] userIds);

	/**
	 * パスワードを変える
	 * @param userId       ユーザーID
	 * @param password     古いパスワード
	 * @param newPassword  新しいパスワード
	 */
	boolean updatePassword(Long userId, String password, String newPassword);
}

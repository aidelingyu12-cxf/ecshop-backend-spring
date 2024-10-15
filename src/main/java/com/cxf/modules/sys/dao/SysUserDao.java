package com.cxf.modules.sys.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cxf.common.utils.PageUtil;
import com.cxf.modules.sys.entity.SysUserEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * システムユーザー
 *
 * @author cxf
 */
@Mapper
public interface SysUserDao extends BaseMapper<SysUserEntity> {
	
	/**
	 * ユーザーリストを抽出する
	 * @param userId  用户ID
	 */
	List<SysUserEntity> getUsersByPage(String userName, Integer curPage, Integer pageSize);
	
	/**
	 * ユーザー総数を抽出する
	 */
	Integer getCount(String userName);
	
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
	 * ユーザネームによって、ユーザー情報を検索する
	 */
	SysUserEntity queryByUserName(String username);
	
	/**
	 * ユーザIdによって、ユーザー情報を検索する
	 */
	SysUserEntity queryByUserId(Long userId);
	
	/**
	 * ユーザIdによって、パスワードを更新する
	 */
	Integer updatePassword(Long userId, String password);

}

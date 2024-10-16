/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package com.cxf.modules.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cxf.common.exception.RRException;
import com.cxf.common.utils.Constant;
import com.cxf.common.utils.PageUtil;
import com.cxf.common.utils.PageUtils;
import com.cxf.common.utils.Query;
import com.cxf.modules.sys.dao.SysUserDao;
import com.cxf.modules.sys.entity.SysUserEntity;
import com.cxf.modules.sys.service.SysRoleService;
import com.cxf.modules.sys.service.SysUserRoleService;
import com.cxf.modules.sys.service.SysUserService;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;


/**
 * 系统用户
 *
 * @author Mark sunlightcs@gmail.com
 */
@Service("sysUserService")
public class SysUserServiceImpl extends ServiceImpl<SysUserDao, SysUserEntity> implements SysUserService {
	@Autowired
	private SysUserRoleService sysUserRoleService;
	@Autowired
	private SysRoleService sysRoleService;
	@Resource
	private SysUserDao sysUserDao;
	
	@Override
	public PageUtils queryPage(Map<String, Object> params) {
		String username = (String)params.get("username");
		Long createUserId = (Long)params.get("createUserId");

		IPage<SysUserEntity> page = this.page(
			new Query<SysUserEntity>().getPage(params),
			new QueryWrapper<SysUserEntity>()
				.like(StringUtils.isNotBlank(username),"username", username)
				.eq(createUserId != null,"create_user_id", createUserId)
		);

		return new PageUtils(page);
	}
	
	@Override
	public PageUtil queryByPage(Map<String, String> params) {
		//現在のページ
		int curPage = Integer.parseInt(params.get("page"));
		if(curPage >= 1)
			curPage = curPage-1;
		//ページごとに表す数
		String userName = params.get("username");
		//ページごとに表す数
		int pageSize = Integer.parseInt(params.get("limit"));
		//リスト総数
		int totalCount = sysUserDao.getCount(userName);
		PageUtil pageUtil = new PageUtil(curPage, pageSize, totalCount);
		//結果リスト
		List<SysUserEntity> userList = sysUserDao.getUsersByPage(userName, curPage, pageSize);
		pageUtil.setList(userList);
		return pageUtil;
	}
	

	@Override
	public List<String> queryAllPerms(Long userId) {
		return baseMapper.queryAllPerms(userId);
	}

	@Override
	public List<Long> queryAllMenuId(Long userId) {
		return baseMapper.queryAllMenuId(userId);
	}

	@Override
	public SysUserEntity queryByUserName(String username) {
		return sysUserDao.queryByUserName(username);
	}

	@Override
	@Transactional
	public void saveUser(SysUserEntity user) {
		user.setCreateTime(new Date());
		//sha256
		String salt = RandomStringUtils.randomAlphanumeric(20);
		user.setPassword(new Sha256Hash(user.getPassword(), salt).toHex());
		user.setSalt(salt);
		sysUserDao.save(user);
		
		//检查角色是否越权
		checkRole(user);
		
		//保存用户与角色关系
		sysUserRoleService.saveOrUpdate(user.getUserId(), user.getRoleIdList());
	}

	@Override
	@Transactional
	public void update(SysUserEntity user) {
		if(StringUtils.isBlank(user.getPassword())){
			user.setPassword(null);
		}else{
			user.setPassword(new Sha256Hash(user.getPassword(), user.getSalt()).toHex());
		}
		this.updateById(user);
		
		//检查角色是否越权
		checkRole(user);
		
		//保存用户与角色关系
		sysUserRoleService.saveOrUpdate(user.getUserId(), user.getRoleIdList());
	}

	@Override
	public void deleteBatch(Long[] userId) {
		this.removeByIds(Arrays.asList(userId));
	}

	@Override
	public boolean updatePassword(Long userId, String password, String newPassword) {
		
		if(password.equals(newPassword))
			return false;
		Integer updateRes = sysUserDao.updatePassword(userId, newPassword);
		
		if(updateRes > 0)
			return true;
		else
			return false;
	}
	
	/**
	 * ユーザー権限をチェックする
	 */
	private void checkRole(SysUserEntity user){
		if(user.getRoleIdList() == null || user.getRoleIdList().size() == 0){
			return;
		}
		//管理者の場合、リターンする
		if(user.getCreateUserId() == Constant.SUPER_ADMIN){
			return ;
		}
		
		//ユーザー役割
		List<Long> roleIdList = sysRoleService.queryRoleIdList(user.getCreateUserId());

		//判断是否越权
		if(!roleIdList.containsAll(user.getRoleIdList())){
			throw new RRException("新增用户所选角色，不是本人创建");
		}
	}
}
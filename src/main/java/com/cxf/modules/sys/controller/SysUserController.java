package com.cxf.modules.sys.controller;

import com.cxf.common.utils.Constant;
import com.cxf.common.utils.PageUtil;
import com.cxf.common.utils.PageUtils;
import com.cxf.common.utils.R;
import com.cxf.common.validator.Assert;
import com.cxf.common.validator.ValidatorUtils;
import com.cxf.common.validator.group.AddGroup;
import com.cxf.common.validator.group.UpdateGroup;
import com.cxf.modules.sys.entity.SysUserEntity;
import com.cxf.modules.sys.service.SysUserRoleService;
import com.cxf.modules.sys.service.SysUserService;
import org.apache.commons.lang.ArrayUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.cxf.common.annotation.SysLog;

import java.util.List;
import java.util.Map;

/**
 * システムユーザー
 *
 * @author cxf
 */
@RestController
@RequestMapping("/sys/user")
public class SysUserController extends AbstractController {
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private SysUserRoleService sysUserRoleService;
	
	/**
	 * ユーザーリスト
	 */
	@GetMapping("/list")
	@RequiresPermissions("sys:user:list")
	public R list(@RequestParam Map<String, String> params){
		//スーパーアカウントだけ、アクセスできる
//		if(getUserId() != Constant.SUPER_ADMIN){
//			params.put("createUserId", getUserId());
//		}
		PageUtil page = sysUserService.queryByPage(params);

		return R.ok().put("page", page);
	}
	
	/**
	 * 获取登录的用户信息
	 */
	@GetMapping("/info")
	public R info(){
		return R.ok().put("user", getUser());
	}
	
	/**
	 * パスワードを変える
	 */
	@SysLog("パスワードを変える")
	@PostMapping("/password")
	public R password(@RequestBody Map<String, String> passwordForm){
		Assert.isBlank(passwordForm.get("newPassword"), "新しいパスワードがヌルです！");
		
		//sha256
		String password = new Sha256Hash(passwordForm.get("password"), getUser().getSalt()).toHex();
		//sha256
		String newPassword = new Sha256Hash(passwordForm.get("newPassword"), getUser().getSalt()).toHex();		
		//パスワードを更新する
		boolean flag = sysUserService.updatePassword(getUserId(), password, newPassword);
		if(!flag){
			return R.error("元のパスワードが不正です");
		}
		
		return R.ok();
	}
	
	/**
	 * 用户信息
	 */
	@GetMapping("/info/{userId}")
	@RequiresPermissions("sys:user:info")
	public R info(@PathVariable("userId") Long userId){
		SysUserEntity user = sysUserService.getById(userId);
		
		//获取用户所属的角色列表
		List<Long> roleIdList = sysUserRoleService.queryRoleIdList(userId);
		user.setRoleIdList(roleIdList);
		
		return R.ok().put("user", user);
	}
	
	/**
	 * ユーザー情報を保存する
	 */
	@SysLog("ユーザー情報を保存する")
	@PostMapping("/save")
	@RequiresPermissions("sys:user:save")
	public R save(@RequestBody SysUserEntity user){
		//ValidatorUtils.validateEntity(user, AddGroup.class);
		
		user.setCreateUserId(getUserId());
		sysUserService.saveUser(user);
		
		return R.ok();
	}
	
	/**
	 * 修改用户
	 */
	@SysLog("修改用户")
	@PostMapping("/update")
	@RequiresPermissions("sys:user:update")
	public R update(@RequestBody SysUserEntity user){
		ValidatorUtils.validateEntity(user, UpdateGroup.class);

		user.setCreateUserId(getUserId());
		sysUserService.update(user);
		
		return R.ok();
	}
	
	/**
	 * 删除用户
	 */
	@SysLog("删除用户")
	@PostMapping("/delete")
	@RequiresPermissions("sys:user:delete")
	public R delete(@RequestBody Long[] userIds){
		if(ArrayUtils.contains(userIds, 1L)){
			return R.error("管理者のアカウントが削除できません");
		}
		
		if(ArrayUtils.contains(userIds, getUserId())){
			return R.error("現ユーザー情報が削除できません");
		}
		
		sysUserService.deleteBatch(userIds);
		
		return R.ok();
	}
}

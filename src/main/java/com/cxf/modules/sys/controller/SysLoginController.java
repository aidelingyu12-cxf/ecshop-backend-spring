package com.cxf.modules.sys.controller;

import com.cxf.common.utils.R;
import com.cxf.modules.sys.entity.SysUserEntity;
import com.cxf.modules.sys.service.SysCaptchaService;
import com.cxf.modules.sys.service.SysUserService;
import com.cxf.modules.sys.service.SysUserTokenService;
import org.apache.commons.io.IOUtils;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Map;

/**
 * ログイン関連
 *
 * @author cxf
 */
@RestController
public class SysLoginController extends AbstractController {
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private SysUserTokenService sysUserTokenService;
	@Autowired
	private SysCaptchaService sysCaptchaService;

	/**
	 * 認証コード
	 */
	@GetMapping("captcha.jpg")
	public void captcha(HttpServletResponse response, String uuid)throws IOException {
		response.setHeader("Cache-Control", "no-store, no-cache");
		response.setContentType("image/jpeg");

		//画像認証コードを取得する
		BufferedImage image = sysCaptchaService.getCaptcha(uuid);

		ServletOutputStream out = response.getOutputStream();
		ImageIO.write(image, "jpg", out);
		IOUtils.closeQuietly(out);
	}

	/**
	 *　ログイン
	 */
	@PostMapping("/sys/login")
	public Map<String, Object> login(@RequestBody Map<String,String> loginForm)throws IOException {
		boolean captcha = sysCaptchaService.validate(loginForm.get("uuid"), loginForm.get("captcha"));
		if(!captcha){
			return R.error("認証コードは不正です");
		}

		//ユーザー情報
		SysUserEntity user = sysUserService.queryByUserName(loginForm.get("username"));

		//アカウントが存在しない、パスワードが間違った場合
		if(user == null || !user.getPassword().equals(new Sha256Hash(loginForm.get("password"), user.getSalt()).toHex())) {
			return R.error("アカウントまたはパスワードは不正です");
		}

		//アカウントがロックされた場合
		if(user.getStatus() == 0){
			return R.error("アカウントがロックされました、管理者へ連絡してください");
		}

		//tokenを生成する
		R r = sysUserTokenService.createToken(user.getUserId());
		return r;
	}


	/**
	 * ログアウト
	 */
	@PostMapping("/sys/logout")
	public R logout() {
		sysUserTokenService.logout(getUserId());
		return R.ok();
	}
	
}

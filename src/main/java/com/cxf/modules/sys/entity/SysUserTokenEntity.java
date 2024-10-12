package com.cxf.modules.sys.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;


/**
 * ユーザーTOKEN
 *
 * @author cxf
 */
@Data
public class SysUserTokenEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//ユーザーID
	private Long userId;
	//token
	private String token;
	//有効期限
	private Date expireTime;
	//更新日付
	private Date updateTime;

}
package com.cxf.modules.sys.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cxf.common.validator.group.AddGroup;
import com.cxf.common.validator.group.UpdateGroup;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * システムユーザー
 *
 * @author cxf
 */
@Data
@TableName("sys_user")
public class SysUserEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/**
	 * ユーザーID
	 */
	@TableId
	@JsonSerialize(using = ToStringSerializer.class)
	private Long userId;

	/**
	 * ユーザネーム
	 */
	@NotBlank(message="ユーザネームはヌルです！", groups = {AddGroup.class, UpdateGroup.class})
	private String username;

	/**
	 * パスワード
	 */
	@NotBlank(message="パスワードはヌルです！", groups = AddGroup.class)
	private String password;

	/**
	 * 
	 */
	private String salt;

	/**
	 * メールアドレス
	 */
	@NotBlank(message="メールアドレスはヌルです！", groups = {AddGroup.class, UpdateGroup.class})
	@Email(message="メールアドレスのフォーマットは不正です！", groups = {AddGroup.class, UpdateGroup.class})
	private String email;

	/**
	 * 携帯番号
	 */
	private String mobile;

	/**
	 * ステータス  0：禁止   1：正常
	 */
	private Integer status;

	/**
	 * 役割ID列表
	 */
	@TableField(exist=false)
	private List<Long> roleIdList;

	/**
	 * 作成者ID
	 */
	private Long createUserId;

	/**
	 * 作成時間
	 */
	private Date createTime;
	
	/**
	 * 削除時間
	 */
	private Integer delete_flag;
	

}
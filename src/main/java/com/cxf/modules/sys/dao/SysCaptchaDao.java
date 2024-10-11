package com.cxf.modules.sys.dao;

import com.cxf.modules.sys.entity.SysCaptchaEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 認証コード
 *
 * @author cxf
 */
@Mapper
public interface SysCaptchaDao {
	
	public void save(SysCaptchaEntity chaptcha);
	
	public SysCaptchaEntity getCaptcha(String uuid);
	
	public void removeById(String uuid);
}

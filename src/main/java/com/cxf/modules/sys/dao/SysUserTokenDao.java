package com.cxf.modules.sys.dao;

import com.cxf.modules.sys.entity.SysUserTokenEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * Token
 *
 * @author cxf
 */
@Mapper
public interface SysUserTokenDao {

    SysUserTokenEntity queryByToken(String token);
    
    SysUserTokenEntity getById(Long userId);
    
    void save(SysUserTokenEntity token);
    
    void updateById(SysUserTokenEntity token);
	
}

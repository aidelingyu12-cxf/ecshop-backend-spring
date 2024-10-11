package com.cxf.modules.sys.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cxf.modules.sys.entity.SysCaptchaEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 認証コード
 *
 * @author cxf
 */
@Mapper
public interface SysCaptchaDao extends BaseMapper<SysCaptchaEntity> {

}

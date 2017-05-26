package org.spring.data.framework.mybatis.bean;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * Description    :   通用mapper接口，该接口不能被mybatis扫描到
 */
public interface IMapper<T> extends Mapper<T>,MySqlMapper<T> {
}

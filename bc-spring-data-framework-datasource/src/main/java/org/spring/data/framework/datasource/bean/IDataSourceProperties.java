package org.spring.data.framework.datasource.bean;

import java.util.List;

/**
 * 数据源配置
 */
public interface IDataSourceProperties {

    String getDefaultDataSourceType();

    String getDefaultDriverClassName();


    List<DataSourceItem> getDataSourceItems();
}

package org.spring.data.framework.datasource.bean;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/3/2.
 */
@ConfigurationProperties(prefix = "bc.data.dataSource",ignoreInvalidFields = true,exceptionIfInvalid = false)
public class DefaultDatasourceProperties implements IDataSourceProperties {
    /**
     * 默认数据源类型
     */
    private String                  defaultDataSourceType;
    /**
     * 默认jdbc驱动
     */
    private String                  defaultDriverClassName;

    private List<DataSourceItem> dataSourceItems = new ArrayList<>(2);


    @Override
    public String getDefaultDataSourceType() {
        return defaultDataSourceType;
    }

    public void setDefaultDataSourceType(String defaultDataSourceType) {
        this.defaultDataSourceType = defaultDataSourceType;
    }

    @Override
    public String getDefaultDriverClassName() {
        return defaultDriverClassName;
    }

    public void setDefaultDriverClassName(String defaultDriverClassName) {
        this.defaultDriverClassName = defaultDriverClassName;
    }

    @Override
    public List<DataSourceItem> getDataSourceItems() {
        return dataSourceItems;
    }

    public void setDataSourceItems(List<DataSourceItem> dataSourceItems) {
        this.dataSourceItems = dataSourceItems;
    }
}

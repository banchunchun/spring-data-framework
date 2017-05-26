package org.spring.data.framework.datasource.helper;

import javax.sql.DataSource;

/**
 * 数据源包装
 */
public class DataSourceWrapper {
    private String name;
    private boolean master;
    private DataSource dataSource;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isMaster() {
        return master;
    }

    public void setMaster(boolean master) {
        this.master = master;
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
}

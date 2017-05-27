package org.spring.data.framework.jpa.bean;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**

 */
@ConfigurationProperties("bc.data.jpa")
public class DefaultJpaProperties implements IJpaProperties {
    private boolean             showSql = false;
    private boolean             formatSql = true;
    private boolean             openInView = false;
    private String              entityPackages;

    private String              hibernateDdlAuto = null;
    private String              hibernateDialect;


    @Override
    public boolean isShowSql() {
        return showSql;
    }

    public void setShowSql(boolean showSql) {
        this.showSql = showSql;
    }

    @Override
    public boolean isFormatSql() {
        return formatSql;
    }

    public void setFormatSql(boolean formatSql) {
        this.formatSql = formatSql;
    }

    @Override
    public boolean isOpenInView() {
        return openInView;
    }

    public void setOpenInView(boolean openInView) {
        this.openInView = openInView;
    }

    @Override
    public String getEntityPackages() {
        return entityPackages;
    }

    public void setEntityPackages(String entityPackages) {
        this.entityPackages = entityPackages;
    }

    @Override
    public String getHibernateDdlAuto() {
        return hibernateDdlAuto;
    }

    public void setHibernateDdlAuto(String hibernateDdlAuto) {
        this.hibernateDdlAuto = hibernateDdlAuto;
    }

    @Override
    public String getHibernateDialect() {
        return hibernateDialect;
    }

    public void setHibernateDialect(String hibernateDialect) {
        this.hibernateDialect = hibernateDialect;
    }
}

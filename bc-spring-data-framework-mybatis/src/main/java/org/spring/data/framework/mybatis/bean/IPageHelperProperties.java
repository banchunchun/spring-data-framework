package org.spring.data.framework.mybatis.bean;

import java.util.Properties;

/**
 * Created by Administrator on 2017/4/6.
 */
public interface IPageHelperProperties {

    Properties getProperties();

    String getOffsetAsPageNum();


    String getRowBoundsWithCount();


    String getPageSizeZero();


    String getReasonable();



    String getSupportMethodsArguments();


    String getDialect();

    String getHelperDialect();

    String getAutoRuntimeDialect();


    String getAutoDialect();


    String getCloseConn();


    String getParams();
}

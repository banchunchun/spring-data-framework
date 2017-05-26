package org.spring.data.framework.datasource.helper;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Map;

/**
 * Company        :   mamahao.com
 * author         :   guxiaolong
 * Date           :   2016/6/29
 * Time           :   14:35
 * Description    :   使用threadloal绑定当前数据源
 */
public class DynamicContextHolder {
    private static final ThreadLocal<String> holder = new ThreadLocal<String>();
    public static List<String> dataSourceNames = Lists.newArrayList();
    public static Map<String,DataSourceWrapper> dataSources = Maps.newConcurrentMap();
    public static DataSourceWrapper defaultDataSource;
    public static void setCurrentDataSource(String name){
        holder.set(name);
    }

    public static void setDefaultDataSource(){
        if(defaultDataSource != null){
            holder.set(defaultDataSource.getName());
        }
    }

    public static String getCurrentDataSource(){
       return holder.get();
    }
    public static void clearCurrentDataSource(){
        holder.remove();
    }

    public static boolean containsDataSource(String dataSourceName){
        return dataSourceNames.contains(dataSourceName);
    }
    public static void addDataSource(String dataSourceName,DataSourceWrapper dataSourceWrapper){
        dataSourceNames.add(dataSourceName);
        dataSources.put(dataSourceName,dataSourceWrapper);
    }
    public static DataSourceWrapper getDataSourceWrapper(String dataSourceName){
        if(StringUtils.isNotBlank(dataSourceName)){
            return dataSources.get(dataSourceName);
        }
        return null;
    }
    public static void setDefaultDataSource(DataSourceWrapper dataSource){
        defaultDataSource = dataSource;
    }
}

package org.spring.data.framework.datasource.helper;

import javax.persistence.Transient;
import java.io.Serializable;

/**
 * Created by Administrator on 2017/3/6.
 */
public class RDSEntity implements Serializable{
    @Transient
    protected long version;
}

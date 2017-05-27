package org.spring.data.framework.jpa.bean.base;

import javax.persistence.MappedSuperclass;
import java.io.Serializable;

/**

 */
@MappedSuperclass
public class JPAEntity implements Serializable {
    @Override
    public String toString() {
        return super.toString();
    }
}

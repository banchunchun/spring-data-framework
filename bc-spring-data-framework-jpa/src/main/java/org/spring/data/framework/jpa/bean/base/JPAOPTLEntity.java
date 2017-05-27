package org.spring.data.framework.jpa.bean.base;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

/**

 * Description    :     支持乐观锁的实体基类
 */
@MappedSuperclass
public class JPAOPTLEntity extends JPAEntity {
    @Version
    @Column(name = "version")
    private long            version;
}

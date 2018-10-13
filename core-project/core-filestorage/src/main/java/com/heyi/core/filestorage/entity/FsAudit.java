package com.heyi.core.filestorage.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * 审计字段
 */
@MappedSuperclass
public abstract class FsAudit<T> extends FsAuditWithNoId {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private T id;

    public T getId() {
        return id;
    }

    public void setId(T id) {
        this.id = id;
    }
}

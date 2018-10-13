package com.heyi.core.filestorage.repository.batch;

import java.util.List;

public interface BatchRepository<T> {
    void batchInsert(List<T> list);

    void batchUpdate(List<T> list);
}

package com.heyi.core.filestorage.repository.batch;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

public class AbstractBatchRepository<T> implements BatchRepository<T> {

    @PersistenceContext
    protected EntityManager em;

    @Transactional
    @Override
    public void batchInsert(List<T> list) {
        for (int i = 0; i < list.size(); i++) {
            em.persist(list.get(i));
            if (i % 30 == 0) {
                em.flush();
                em.clear();
            }
        }
    }

    @Transactional
    @Override
    public void batchUpdate(List<T> list) {
        for (int i = 0; i < list.size(); i++) {
            em.merge(list.get(i));
            if (i % 30 == 0) {
                em.flush();
                em.clear();
            }
        }
    }

}

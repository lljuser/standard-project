package com.heyi.core.filestorage.repository;

import com.heyi.core.filestorage.entity.BcMessage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<BcMessage, Long> {

    Page<BcMessage> findBcMessagesByReceiveBrokerContaining(String account, Pageable pageable);
}

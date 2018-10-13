package com.heyi.core.filestorage.service;

import com.heyi.core.common.PageResult;
import com.heyi.core.filestorage.entity.BcMessage;

public interface MessageService {

    void createMessage(BcMessage message) throws Exception;

    void deleteMessage(Long id) throws Exception;

    void updateMessage(BcMessage message) throws Exception;

    PageResult<BcMessage> messageList(int page, int size) throws Exception;

    void updateMessageStatusById(Long msgId, Short status) throws Exception;
}

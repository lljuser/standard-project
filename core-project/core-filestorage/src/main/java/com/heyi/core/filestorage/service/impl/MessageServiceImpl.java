package com.heyi.core.filestorage.service.impl;

import com.heyi.core.common.PageResult;
import com.heyi.core.common.CookieManager;
import com.heyi.core.filestorage.entity.BcMessage;
import com.heyi.core.filestorage.repository.MessageRepository;
import com.heyi.core.filestorage.service.MessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Optional;

@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageRepository messageRepository;

    @Override
    public void createMessage(BcMessage message) throws Exception {
        if (ObjectUtils.isEmpty(message)) {
            throw new Exception("消息内容不能为空");
        }
        String title = message.getTitle();
        if (StringUtils.isEmpty(title)) {
            throw new Exception("消息标题不能为空");
        }
        short type = message.getType();
        String sid = message.getSid();
        String receiveBroker = message.getReceiveBroker();
        short status = message.getStatus();

        BcMessage msg = new BcMessage();
        msg.setTitle(title);
        msg.setType(type);
        msg.setSid(sid);
        msg.setReceiveBroker(receiveBroker);
        msg.setStatus(status);
        messageRepository.save(msg);
    }

    @Override
    public void deleteMessage(Long id) throws Exception {
        if (ObjectUtils.isEmpty(id)) {
            throw new Exception("消息id不能为空");
        }
        try {
            messageRepository.deleteById(id);
        } catch (Exception e) {
            throw new Exception("消息不存在");
        }
    }

    @Override
    public void updateMessage(BcMessage message) throws Exception {
        if (ObjectUtils.isEmpty(message)) {
            throw new Exception("消息内容不能为空");
        }
        Long messageId = message.getId();
        if (ObjectUtils.isEmpty(messageId)) {
            throw new Exception("消息id不能为空");
        }
        Optional<BcMessage> messageOptional = messageRepository.findById(messageId);
        if (ObjectUtils.isEmpty(messageOptional)) {
            throw new Exception("消息不存在");
        }

        BcMessage message1 = messageOptional.get();
        String title = message.getTitle();
        if (!StringUtils.isEmpty(title)) {
            message1.setTitle(title);
        }
        String sid = message.getSid();
        if (!StringUtils.isEmpty(sid)) {
            message1.setSid(sid);
        }
        String receiveBroker = message.getReceiveBroker();
        if (!StringUtils.isEmpty(receiveBroker)) {
            message1.setReceiveBroker(receiveBroker);
        }
        short status = message.getStatus();
        if (status >= 0) {
            message1.setStatus(status);
        }

        Timestamp currentTimestamp = new Timestamp(new Date().getTime());
        message1.setUpdateTime(currentTimestamp);
        messageRepository.save(message1);
    }

    @Override
    public PageResult<BcMessage> messageList(int page, int size) throws Exception {
        int p = page >= 1 ? page - 1 : 0;
        int s = size >= 0 ? size : 10;

        ThreadLocal<String> threadLocalAccount = CookieManager.getInstance().getAccount();
        String account = threadLocalAccount.get();
        if (StringUtils.isEmpty(account)) {
            throw new Exception("请重新登录后再尝试");
        }
        Sort sort = new Sort(Sort.Direction.DESC, "createTime");
        Pageable pageable = PageRequest.of(p, s, sort);
        Page<BcMessage> messages = messageRepository.findBcMessagesByReceiveBrokerContaining(account, pageable);
        return PageResult.build(messages);
    }

    @Override
    public void updateMessageStatusById(Long msgId, Short status) throws Exception {
        if (msgId == null) {
            throw new Exception("消息id不能为空");
        }
        Optional<BcMessage> messageOptional = messageRepository.findById(msgId);
        BcMessage message = messageOptional.get();
        message.setStatus(status);
        messageRepository.save(message);
    }
}

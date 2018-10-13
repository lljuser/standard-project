package com.heyi.webapp.filesystem.controller;

import com.heyi.core.common.ApiResponse;
import com.heyi.core.common.PageResult;
import com.heyi.core.filestorage.entity.BcMessage;
import com.heyi.core.filestorage.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/message")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @PostMapping("/create")
    public ApiResponse<Void> createMessage(@RequestBody BcMessage message) {
        try {
            messageService.createMessage(message);
            return ApiResponse.success(null);
        } catch (Exception e) {
            return ApiResponse.error(e.getMessage());
        }
    }

    @PostMapping("/delete")
    public ApiResponse<Void> deleteMessage(@RequestBody Map<String, Object> body) {
        Integer id = (Integer) body.get("id");
        try {
            messageService.deleteMessage(new Long(id));
            return ApiResponse.success(null);
        } catch (Exception e) {
            return ApiResponse.error(e.getMessage());
        }
    }

    @PostMapping("/update")
    public ApiResponse<Void> updateMessage(@RequestBody BcMessage message) {
        try {
            messageService.updateMessage(message);
            return ApiResponse.success(null);
        } catch (Exception e) {
            return ApiResponse.error(e.getMessage());
        }
    }

    @GetMapping("/list")
    public ApiResponse<PageResult> messageList(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size
    ) {
        try {
            PageResult<BcMessage> pageResult = messageService.messageList(page, size);
            return ApiResponse.success(pageResult);
        } catch (Exception e) {
            return ApiResponse.error(e.getMessage());
        }
    }
}

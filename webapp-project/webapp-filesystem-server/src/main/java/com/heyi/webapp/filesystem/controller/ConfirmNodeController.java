package com.heyi.webapp.filesystem.controller;

import com.heyi.core.common.ApiResponse;
import com.heyi.core.filestorage.entity.BcTempFileTransaction;
import com.heyi.core.filestorage.service.ConfirmNodeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/node")
public class ConfirmNodeController {

    private static final Logger log = LoggerFactory.getLogger( ConfirmNodeController.class );

    @Autowired
    ConfirmNodeService confirmNodeService;

    @PostMapping (value = "/tx/confirm")
    public ApiResponse<Boolean> completeTask(@RequestBody Map<String, Object> body) {
        Long msgId = Long.valueOf((String) body.get("msgId"));
        String txId = (String) body.get("txId");
        confirmNodeService.confirmFileTransaction(msgId, txId);
        return ApiResponse.success(true);
    }

    /**
     * 接收Order提交的未确认交易
     *
     * @param transaction
     * @return
     */
    @PostMapping("/tx/recv")
    public ApiResponse<Boolean> receiveTransaction(@RequestBody BcTempFileTransaction transaction) {
        try {
            confirmNodeService.receiveFileTransaction( transaction );
            return ApiResponse.success( true );
        } catch (Exception ex) {
            return ApiResponse.error( null );
        }
    }
}

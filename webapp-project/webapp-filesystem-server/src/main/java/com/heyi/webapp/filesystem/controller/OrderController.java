package com.heyi.webapp.filesystem.controller;


import com.heyi.core.common.ApiResponse;
import com.heyi.core.filestorage.entity.BcTask;
import com.heyi.core.filestorage.entity.BcTempFileTransaction;
import com.heyi.core.filestorage.service.BcTempFileTransactionService;
import com.heyi.core.filestorage.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @ClassName OrderController
 * @Description: 排序节点
 * @Author: lidong
 * @CreateDate: 2018/8/29$ 10:25 AM$
 * @UpdateUser: lidong
 * @UpdateDate: 2018/8/29$ 10:25 AM$
 * @UpdateRemark: TODO
 * @Version: 1.0
 **/
@RestController
@RequestMapping(value = "/order", consumes = "application/json;charset=UTF-8")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private BcTempFileTransactionService service;

    @PostMapping(value = "/dispatch")
    public ApiResponse dispatchTrans(@RequestBody BcTempFileTransaction transaction, HttpServletRequest request) {
        try {
            boolean result = orderService.dispatchTrans( transaction, request );
            return result ? ApiResponse.success( null ) : ApiResponse.error( "发送失败" );
        } catch (Exception e) {
            return ApiResponse.error( e.getMessage() );
        }
    }

    /**
     * 排序节点最终确认file transaction
     */
    @PostMapping("/confirm-file-transaction")
    public ApiResponse<Boolean> finalConfirmFileTransaction(@RequestBody BcTask task) {
        try {
            orderService.finalConfirmFileTransaction( task.getTxId(), task.getReceiveBroker() );
            return ApiResponse.success( true );
        } catch (Exception e) {
            return ApiResponse.error( e.getMessage() );
        }
    }

    @PostMapping("/test")
    public ApiResponse<Boolean> test() {
        service.packTransactions();
        return ApiResponse.success( null );
    }
}

package com.heyi.webapp.filesystem.controller;

import com.heyi.core.common.ApiResponse;
import com.heyi.core.common.PageQuery;
import com.heyi.core.common.PageResult;
import com.heyi.core.filestorage.entity.BcTempFileTransaction;
import com.heyi.core.filestorage.service.BcTempFileTransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(value = "/blockchain")
public class BlockchainController {

    @Autowired
    private BcTempFileTransactionService bcTempFileTransactionService;

    /**
     * 获取未确认的交易列表
     */
    @RequestMapping("/tx/unconfirm/list")
    public ApiResponse<PageResult<BcTempFileTransaction>> getUnCommitedTransactions(@RequestParam("status") short status,
                                                                                    @Nullable @RequestParam("chainCode") String chainCode,
                                                                                    PageQuery pageQuery) {

        return ApiResponse.success(bcTempFileTransactionService.findAllByStatusAndChainCode(status, chainCode, pageQuery));
    }

    /**
     * 获取指定的未确认交易
     */
    @GetMapping("/tx/{txId}")
    public ApiResponse<BcTempFileTransaction> getUnCommitedTransaction(String txId) {
        Optional<BcTempFileTransaction> transaction = bcTempFileTransactionService.getTransaction(txId);
        if (transaction == null || !transaction.isPresent()) return ApiResponse.notFound("指定交易不存在");
        return ApiResponse.success(transaction.get());
    }
}

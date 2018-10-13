package com.heyi.webapp.blockchain.controller;

import com.heyi.core.blockchain.repository.sql.entity.SqlFileTransaction;
import com.heyi.core.blockchain.service.FileTransactionService;
import com.heyi.core.blockchain.transaciton.FileTransation;
import com.heyi.core.common.ApiResponse;
import com.heyi.core.common.PageQuery;
import com.heyi.core.common.PageResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName TransactionController
 * @Description: TODO
 * @Author: lidong
 * @CreateDate: 2018/9/6$ 5:55 PM$
 * @UpdateUser: lidong
 * @UpdateDate: 2018/9/6$ 5:55 PM$
 * @UpdateRemark: TODO
 * @Version: 1.0
 **/
@RestController
@RequestMapping(value = "/tx")
@Api(tags = "交易服务接口")
public class TransactionController {

    @Autowired
    private FileTransactionService transactionService;

    /**
     * 获取所有的已经确认交易列表
     */
    @GetMapping("/list")
    @ApiOperation(value = "获取已经确认交易列表")
    public ApiResponse<PageResult<SqlFileTransaction>> getTransactionsList(PageQuery pageQuery) {
        return ApiResponse.success( transactionService.getAll( pageQuery ) );
    }

    /**
     * 查询某一区块链下的交易列表
     **/
    @GetMapping("list/{identityCode}")
    @ApiOperation(value = "查询某一区块下的交易列表")
    public ApiResponse<PageResult<SqlFileTransaction>> getTransactionsByBlock(
            @PathVariable String identityCode, PageQuery pageQuery) {
        return ApiResponse.success( transactionService.findByIdentityCode( identityCode, pageQuery ) );
    }
}

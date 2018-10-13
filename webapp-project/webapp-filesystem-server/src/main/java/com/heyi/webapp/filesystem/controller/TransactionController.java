package com.heyi.webapp.filesystem.controller;

import com.heyi.core.common.ApiResponse;
import com.heyi.core.filestorage.service.BcTempFileTransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName TransactionController
 * @Description: 交易查询及交易详情接口
 * @Author: lidong
 * @CreateDate: 2018/9/3$ 3:20 PM$
 * @UpdateUser: lidong
 * @UpdateDate: 2018/9/3$ 3:20 PM$
 * @UpdateRemark: TODO
 * @Version: 1.0
 **/
@RestController
@RequestMapping("/transaction/")
public class TransactionController {

    @Autowired
    private BcTempFileTransactionService bcTempFileTransactionService;

    /**
     * 根据交易信息，返回所需的信息
     * 1、交易基础信息（txid，文件名、发送者、交易生成时间、所属证券（取出文件夹的名称））
     * 2、确认列表及各自确认状态
     * 3、关注者列表（文件基础信息(文件名称含扩展名、文件编码、hash、文件大小、上传人ip、文件版本)）
     * 4、文件下载（ 交易确认操作按钮(仅确认页面)）
     *
     * @param txId
     * @return
     */
    @GetMapping("{txId}")
    public ApiResponse getBasicInfo(@PathVariable("txId") String txId) {
        if (StringUtils.isEmpty( txId ))
            return ApiResponse.error( "查无数据" );
        return ApiResponse.success( bcTempFileTransactionService.getTxInfo( txId ) );
    }

}

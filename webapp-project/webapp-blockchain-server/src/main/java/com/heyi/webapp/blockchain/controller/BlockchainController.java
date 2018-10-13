package com.heyi.webapp.blockchain.controller;

import com.heyi.core.blockchain.service.BlockService;
import com.heyi.core.blockchain.service.FileTransactionService;
import com.heyi.core.blockchain.service.SyncDataService;
import com.heyi.core.blockchain.service.TransactionPackageService;
import com.heyi.core.blockchain.storage.Block;
import com.heyi.core.blockchain.storage.Blockchain;
import com.heyi.core.blockchain.storage.BlockchainBuilder;
import com.heyi.core.blockchain.storage.IBlockchainStorage;
import com.heyi.core.blockchain.transaciton.FileTransation;
import com.heyi.core.common.ApiResponse;
import com.heyi.core.common.PageQuery;
import com.heyi.core.common.PageResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


/**
 * 区块链服务接口
 **/
@RestController
@RequestMapping(value = "/blockchain")
@Api(tags = "区块链服务接口")
public class BlockchainController {
    @Autowired
    private BlockchainBuilder blockchainBuilder;

    @Autowired
    private IBlockchainStorage storage;

    @Autowired
    private BlockService blockService;

    @Autowired
    private FileTransactionService transactionService;

    @Autowired
    private TransactionPackageService packageService;

    @Autowired
    private SyncDataService syncDataService;

    /**
     * 接收分发的区块及交易数据(非挖矿节点)
     **/
    @GetMapping(value = "/receive")
    @ApiOperation(value = "接收分发的区块及交易数据(非挖矿节点)")
    public ApiResponse receiveBlock(Block block, BindingResult result) {
        if (result.hasErrors()) {
            return ApiResponse.error( "数据错误" );
        }
        Blockchain chain = blockchainBuilder.createBlockChain( block.getIdentityCode() );
        if (chain.validateAndAddBlock( block )) {
            return ApiResponse.success( null );
        }
        return ApiResponse.error( "the block is invalid" );
    }

    /**
     * 获取链对应的所有区块
     *
     * @param identityCode
     * @return
     */
    @GetMapping("allBlock/{identityCode}")
    @ApiOperation(value = "获取链对应的所有区块")
    public ApiResponse getBlockByChain(@PathVariable String identityCode, PageQuery pageQuery) {
        return ApiResponse.success( blockService.getBlockByChain( identityCode, pageQuery ) );
    }

    /**
     * 根据链标识和时间戳获取区块集合
     *
     * @param identityCode 链标识
     * @param timestamp    时间戳
     */
    @GetMapping("/sync/{identityCode}/{timestamp}")
    public ApiResponse<List<Block>> syncBlock(@PathVariable String identityCode, @PathVariable long timestamp) {
        return ApiResponse.success( blockService.getBlockByChainAndTimestamp( identityCode, timestamp ) );
    }

    /**
     * 获取某区块
     **/
    @GetMapping("/block/{hash}")
    @ApiOperation(value = "获取某区块的数据")
    public ApiResponse<Block> getBlock(@PathVariable String hash) {
        Block b = storage.getBlock( hash );
        if (b == null) return ApiResponse.error( "指定区块不存在" );
        return ApiResponse.success( b );
    }

    /**
     * 获取某区块 -- 涉及多条
     **/
    @GetMapping("/block")
    @ApiOperation(value = "获取某区块的数据 -- 涉及多条")
    public ApiResponse getBlock(@RequestParam(defaultValue = "") String identityCode, @RequestParam(defaultValue = "") String hash) {
        return ApiResponse.success( blockService.getBlock( new Block( identityCode, hash ) ) );
    }

    /**
     * 验证某区块
     **/
    @PostMapping("/block/validate")
    @ApiOperation(value = "验证某区块")
    public ApiResponse validateBlock(Block block, BindingResult result) {
        if (result.hasErrors()) {
            return ApiResponse.error( "数据错误" );
        }
        Blockchain chain = blockchainBuilder.createBlockChain( block.getIdentityCode() );
        boolean validationResult = chain.validateOwnerBlock( block );
        if (validationResult)
            return ApiResponse.success( null );
        return ApiResponse.error( "invalid" );
    }

    /**
     * 获取某笔交易详情
     **/
    @GetMapping(value = "/tx/{txId}")
    @ApiOperation(value = "获取某笔交易详情")
    public ApiResponse<FileTransation> getTransaction(@PathVariable String txId) {
        FileTransation fileTransation = transactionService.getTransactionByTxId( txId );
        if (fileTransation == null) return ApiResponse.error( "指定交易不存在" );
        return ApiResponse.success( fileTransation );
    }

    /**
     * 验证某笔交易
     **/
    @PostMapping("/tx/validate/{identityCode}")
    @ApiOperation(value = "验证某笔交易")
    public ApiResponse validateTransaction(@PathVariable String identityCode, FileTransation transation, BindingResult result) {
        if (result.hasErrors()) {
            return ApiResponse.error( "数据错误" );
        }
        Blockchain chain = blockchainBuilder.createBlockChain( identityCode );
        Boolean validationResult = chain.validateTransaction( transation );
        if (validationResult)
            return ApiResponse.success( null );
        return ApiResponse.error( "invalid" );
    }


    /**
     * 查询某一区块下的交易列表
     **/
    @GetMapping("/tx/list/{blockHash}")
    @ApiOperation(value = "查询某一区块下的交易列表")
    public ApiResponse<PageResult<FileTransation>> getTransactionsByBlock(
            @PathVariable String blockHash, PageQuery pageQuery) {
        return ApiResponse.success( transactionService.findAllByBlockHash( blockHash, pageQuery ) );
    }

    /**
     * 临时交易打包、产生区块、加入区块链
     *
     * @param map
     */
    @PostMapping("/tx/pack-trans")
    @ApiOperation(value = "临时交易打包、产生区块、加入区块链")
    public ApiResponse<List<String>> packTransactions(@RequestBody Map<String, List<FileTransation>> map) {
        try {
            return ApiResponse.success( packageService.packTransactions( map ) );
        } catch (Exception ex) {
            return ApiResponse.error( null );
        }
    }
    /**************** 同步order节点的接口 ***************/

    /**
     * 获取所有的链
     **/
    @PostMapping(value = "/getChains")
    @ApiOperation(value = "同步order节点的接口 - 获取所有的链")
    public ApiResponse getChains(PageQuery pageQuery) {
        return ApiResponse.success( syncDataService.getChains( pageQuery ) );
    }

    /**
     * 获取所有的区块
     **/
    @PostMapping(value = "/getBlocks")
    @ApiOperation(value = "同步order节点的接口 - 获取所有的区块")
    public ApiResponse getBlocks(PageQuery pageQuery) {
        return ApiResponse.success( syncDataService.getBlocks( pageQuery ) );
    }


    /**
     * 获取所有的交易
     **/
    @PostMapping(value = "/getTxs")
    @ApiOperation(value = "同步order节点的接口 - 获取所有的交易")
    public ApiResponse getTxs(PageQuery pageQuery) {
        return ApiResponse.success( syncDataService.getTxs( pageQuery ) );
    }

    /**
     * 保存获取所有的链
     *
     * @param data
     */
    @PostMapping(value = "/syncChains")
    @ApiOperation(value = "同步order节点的接口 - 保存获取所有的链")
    public void syncChains(@RequestBody Map data) {
        syncDataService.syncChains( data );
    }

    /**
     * 保存获取所有的区块
     *
     * @param data
     */
    @PostMapping(value = "/syncBlocks")
    @ApiOperation(value = "同步order节点的接口 - 保存获取所有的区块")
    public void syncBlocks(@RequestBody Map data) {
        syncDataService.syncBlocks( data );
    }


    /**
     * 保存获取所有的交易
     *
     * @param data
     */
    @PostMapping(value = "/syncTxs")
    @ApiOperation(value = "同步order节点的接口 - 保存获取所有的交易")
    public void syncTxs(@RequestBody Map data) {
        syncDataService.syncTxs( data );
    }

}

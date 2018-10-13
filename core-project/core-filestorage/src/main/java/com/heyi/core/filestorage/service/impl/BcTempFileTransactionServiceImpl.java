package com.heyi.core.filestorage.service.impl;

import com.heyi.core.common.PageQuery;
import com.heyi.core.common.PageResult;
import com.heyi.core.filestorage.dto.FileTransationDto;
import com.heyi.core.filestorage.entity.BcTask;
import com.heyi.core.filestorage.entity.BcTempFileTransaction;
import com.heyi.core.filestorage.entity.FsFile;
import com.heyi.core.filestorage.repository.BcTempFileTransactionRepository;
import com.heyi.core.filestorage.service.BcTaskService;
import com.heyi.core.filestorage.service.BcTempFileTransactionService;
import com.heyi.core.filestorage.service.FileService;
import com.heyi.core.filestorage.util.RestTemplateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class BcTempFileTransactionServiceImpl implements BcTempFileTransactionService {
    @Autowired
    private BcTempFileTransactionRepository bcTempFiletransactionRepository;

    @Autowired
    private FileService fileService;

    @Autowired
    private BcTaskService bcTaskService;

    @Override
    public boolean save(BcTempFileTransaction data) {
        try {
            bcTempFiletransactionRepository.save( data );
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean delete(Integer id) {
        try {
            bcTempFiletransactionRepository.deleteById( id );
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public PageResult<BcTempFileTransaction> findAllByStatusAndChainCode(short status, String chainCode, PageQuery pageQuery) {
        Pageable pageable = PageRequest.of( pageQuery.getPageNumber(), pageQuery.getPageSize(), Sort.Direction.DESC, ("id") );
        if (StringUtils.isEmpty( chainCode )) {
            return PageResult.build( bcTempFiletransactionRepository.findAllByStatus( status, pageable ) );
        } else {
            return PageResult.build( bcTempFiletransactionRepository.findAllByStatusAndChainCode( status, chainCode, pageable ) );
        }
    }

    @Override
    public BcTempFileTransaction updateStatusByTxId(String txId) {
        Optional<BcTempFileTransaction> data = getTransaction( txId );
        if (data.isPresent()) {
            BcTempFileTransaction transaction = data.get();
            if (ObjectUtils.isEmpty( transaction )) return null;
            transaction.setStatus( BcTempFileTransaction.Status.HAS_CONFIRMED );
            bcTempFiletransactionRepository.save( transaction );
            return transaction;
        }
        return null;
    }

    @Override
    public Object getTxInfo(String txId) {
        // 返回的结果
        Map<String, Object> result = new LinkedHashMap<>();
        // 查询临时交易信息
        Optional<BcTempFileTransaction> txInfo = bcTempFiletransactionRepository.findByTxId( txId );
        result.put( "transaction", txInfo.isPresent() ? txInfo.get() : null );
        // 查询文件信息
        if (txInfo.isPresent()) {
            FsFile file = fileService.findFsFileByCode( txInfo.get().getFileCode() );
            result.put( "file", file );
        } else {
            result.put( "file", null );
        }
        // 获取确认信息
        result.put( "receiver", bcTaskService.getDataByTxIdAndType( txId, BcTask.Type.CONFIRM_TRANSACTION ) );
        // 获取关注信息
        result.put( "follower", bcTaskService.getDataByTxIdAndType( txId, BcTask.Type.FOLLOW_TRANSACTION ) );
        return result;
    }

    @Override
    public Optional<BcTempFileTransaction> getTransaction(String txId) {
        return bcTempFiletransactionRepository.findByTxId( txId );
    }

    @Value("${app.endpoints.order:127.0.0.1}:${app.ports.blockchain:8109}")
    String ORDER_BLOCK_CHAIN;

    /**
     * 打包临时交易
     */
    @Override
    public void packTransactions() {
        Pageable pageable = PageRequest.of( 0, 20, Sort.by( "timestamp" ).descending() );
        // 这里有并发问题，多个节点可能都会取到全部或部分一样的数据
        Page<BcTempFileTransaction> pageData = bcTempFiletransactionRepository.findAllByStatus( BcTempFileTransaction.Status.HAS_CONFIRMED, pageable );
        if (pageData.getNumberOfElements() <= 0) return;
        List<BcTempFileTransaction> transactions = pageData.stream().collect( Collectors.toList() );
        // 状态更新为待打包
        batchUpdateStatus( transactions, BcTempFileTransaction.Status.BE_PACKED );
        String chainServerUrl = "http://" + ORDER_BLOCK_CHAIN + "/blockchain/tx/pack-trans";
        Map<String, List<FileTransationDto>> postData = parsePostDataForPack( transactions );
        // 发送到Order节点去打包
        ResponseEntity result = RestTemplateUtil.post( chainServerUrl, postData );
        // 由于挖矿过程比较耗时，可能导致超时，所以这里不再等待响应结果来处理业务
//        if (result == null) {
//            batchUpdateStatus(transactions, BcTempFileTransaction.Status.HAS_CONFIRMED);
//            return;
//        }
//        Map map = MapUtil.object2Map(result.getBody());
//        if (map != null && !map.isEmpty() && map.get("status").toString().equals("OK")) {
//            // order处理成功, 状态更新由Order处理
//        } else {
//            batchUpdateStatus(transactions, BcTempFileTransaction.Status.HAS_CONFIRMED);
//        }
    }


    private void batchUpdateStatus(List<BcTempFileTransaction> transactions, short status) {
        if (transactions == null || transactions.size() < 1) return;
        for (BcTempFileTransaction transaction : transactions) {
            transaction.setStatus( status );
        }
        bcTempFiletransactionRepository.batchUpdate( transactions );
    }

    private Map<String, List<FileTransationDto>> parsePostDataForPack(List<BcTempFileTransaction> transactions) {
        if (transactions == null || transactions.size() < 1) return null;
        Map<String, List<BcTempFileTransaction>> groups = transactions
                .stream()
                .collect( Collectors.groupingBy( BcTempFileTransaction::getChainCode ) );
        Map<String, List<FileTransationDto>> map = new LinkedMultiValueMap<>();
        for (Map.Entry<String, List<BcTempFileTransaction>> entry : groups.entrySet()) {
            List<BcTempFileTransaction> tempTrans = entry.getValue();
            if (tempTrans == null) continue;
            List<FileTransationDto> trans = new ArrayList<>();
            for (BcTempFileTransaction tempTran : tempTrans) {
                trans.add( FileTransationDto.castTo( tempTran ) );
            }
            if (!map.containsKey( entry.getKey() ))
                map.put( entry.getKey(), trans );
        }
        return map;
    }

    @Override
    public BcTempFileTransaction getByFileCode(String fileCode) {
        Optional<BcTempFileTransaction> result = bcTempFiletransactionRepository.getByFileCode( fileCode );
        if (result.isPresent()) return result.get();
        return null;
    }
}

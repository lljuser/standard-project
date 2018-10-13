package com.heyi.core.blockchain.service.impl.sql;

import com.heyi.core.blockchain.repository.sql.SqlBcTempFileTransactionRepository;
import com.heyi.core.blockchain.repository.sql.SqlFileTransactionRepository;
import com.heyi.core.blockchain.repository.sql.entity.SqlFileTransaction;
import com.heyi.core.blockchain.repository.sql.entity.SqlTempFileTransaction;
import com.heyi.core.blockchain.service.TransactionPackageService;
import com.heyi.core.blockchain.storage.Block;
import com.heyi.core.blockchain.storage.Blockchain;
import com.heyi.core.blockchain.storage.BlockchainBuilder;
import com.heyi.core.blockchain.transaciton.FileTransation;
import com.heyi.core.blockchain.transaciton.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TransactionPackageServiceImpl implements TransactionPackageService {

    @Autowired
    private SqlBcTempFileTransactionRepository tempFileTransactionRepository;

    @Autowired
    private SqlFileTransactionRepository fileTransactionRepository;

    @Autowired
    BlockchainBuilder blockchainBuilder;

    @Override
    @Transactional
    public List<String> packTransactions(Map<String, List<FileTransation>> map) {
        List<String> successfulAddedTrans = new ArrayList<>();
        for (Map.Entry<String, List<FileTransation>> entry : map.entrySet()) {
            List<FileTransation> transations = entry.getValue();
            filterCommitedTrans(transations);
            filterNotBeingPackedTrans(transations);

            Transaction[] transArray = new Transaction[transations.size()];

            try {
                // 打包
                Blockchain blockchain = blockchainBuilder.createBlockChain(entry.getKey());
                // 挖矿
                Block block = blockchain.mineBlock(transations.toArray(transArray));

                successfulAddedTrans.addAll(transations.stream().map(FileTransation::getTxId).collect(Collectors.toList()));

                // update trans
                batchUpdateStatus(transArray, SqlTempFileTransaction.Status.HAS_PACKED);

            } catch (Exception ex) {
                batchUpdateStatus(transArray, SqlTempFileTransaction.Status.HAS_CONFIRMED);
                ex.printStackTrace();
            }
        }

        return successfulAddedTrans;
    }

    private List<FileTransation> filterCommitedTrans(List<FileTransation> transations) {

        List<String> txIds = transations.stream().map(FileTransation::getTxId).collect(Collectors.toList());
        List<String> existsTrans = fileTransactionRepository.findAll(new Specification<SqlFileTransaction>() {
            @Override
            public Predicate toPredicate(Root<SqlFileTransaction> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list = new ArrayList<>();
                list.add(root.<String>get("txId").in(txIds));
                Predicate[] p = new Predicate[list.size()];
                return criteriaBuilder.and(list.toArray(p));
            }
        }).stream().map(SqlFileTransaction::getTxId).collect(Collectors.toList());


        transations = transations.stream().filter(s -> !existsTrans.contains(s.getTxId()))
                .collect(Collectors.toList());

        return transations;
    }

    private List<FileTransation> filterNotBeingPackedTrans(List<FileTransation> transations) {
        List<String> txIds = transations.stream().map(FileTransation::getTxId).collect(Collectors.toList());
        List<String> existsTrans = tempFileTransactionRepository.findAll(new Specification<SqlTempFileTransaction>() {
            @Override
            public Predicate toPredicate(Root<SqlTempFileTransaction> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list = new ArrayList<>();
                list.add(criteriaBuilder.notEqual(root.<Short>get("status"), SqlTempFileTransaction.Status.BE_PACKED));
                list.add(root.<String>get("txId").in(txIds));
                Predicate[] p = new Predicate[list.size()];
                return criteriaBuilder.and(list.toArray(p));
            }
        }).stream().map(SqlTempFileTransaction::getTxId).collect(Collectors.toList());


        transations = transations.stream().filter(s -> !existsTrans.contains(s.getTxId()))
                .collect(Collectors.toList());

        return transations;
    }

    private void batchUpdateStatus(Transaction[] transations, short status) {
        if (transations == null || transations.length <= 0) return;
        batchUpdateStatus(Arrays.stream(transations).collect(Collectors.toList()), status);
    }

    private void batchUpdateStatus(List<Transaction> transations, short status) {
        if (transations == null || transations.size() <= 0) return;

        List<String> txids = transations.stream().map(Transaction::getTxId).collect(Collectors.toList());
        tempFileTransactionRepository.batchUpdateStatus(txids, status);
    }
}

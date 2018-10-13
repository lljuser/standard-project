package com.heyi.core.blockchain.service;

import com.heyi.core.blockchain.transaciton.FileTransation;

import java.util.List;
import java.util.Map;

public interface TransactionPackageService {

    List<String> packTransactions(Map<String, List<FileTransation>> map);
}

package com.heyi.webapp.blockchain;

import com.heyi.core.blockchain.storage.Block;
import com.heyi.core.blockchain.storage.Blockchain;
import com.heyi.core.blockchain.storage.BlockchainBuilder;
import com.heyi.core.blockchain.transaciton.FileTransation;
import com.heyi.core.blockchain.transaciton.Transaction;
import com.heyi.core.common.UUIDHexGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Iterator;

@SpringBootApplication
@ComponentScan(basePackages = {"com.heyi"})
//@EnableDiscoveryClient
@EnableJpaRepositories(basePackages = {"com.heyi.core.blockchain.repository.sql"})
@EntityScan(basePackages = {"com.heyi.core.blockchain.repository.sql.entity"})
@EnableSwagger2
public class WebappBlockchainServerApplication {
    @Autowired
    private BlockchainBuilder blockchainBuilder;

    public static void main(String[] args) throws InterruptedException {
        ApplicationContext context = SpringApplication.run( WebappBlockchainServerApplication.class, args );
                BlockchainBuilder blockchainBuilder =  context.getBean(BlockchainBuilder.class);
//        Blockchain blockchain = blockchainBuilder.createBlockChain();
//
//        Transaction transaction1=new FileTransation(UUIDHexGenerator.generate(),"spring.mvc.book");
//
//        //Thread.sleep(1);
//        Transaction transaction2=new FileTransation(UUIDHexGenerator.generate(),"spring.boot.book");
//
//        //Thread.sleep(1);
//        Transaction transaction3=new FileTransation(UUIDHexGenerator.generate(),"spring.cloud.book");
//
//        Block block1 = blockchain.mineBlock(new Transaction[]{transaction3,transaction2,transaction1});
//
//        Iterator<Block> blockIterator = blockchain.getBlockchainIterator();
//
//        while (blockIterator.hasNext()){
//            Block block= blockIterator.next();
//            System.out.println("-----------------------");
//            System.out.println("Block IdentityCode:"+block.getIdentityCode());
//            System.out.println("Block Hash:"+block.getHash());
//
//            for(Transaction item:block.getTransactions()){
//                System.out.println("Transaction TxId:" + item.getTxId());
//                System.out.println("Transaction TimeStamp:" + item.getTimestamp());
//                System.out.println("Transaction FileName:"+ item.getDescription());
//                System.out.println("-------");
//            }
//
//
//        }
//
//        blockchain.validateBlock(block1);
//        blockchain.validateOwnerBlock(block1);
    }
}

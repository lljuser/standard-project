package com.heyi.webapp.filesystem.job;

import com.heyi.core.filestorage.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @ClassName OrderJob
 * @Description: Order节点的区块和交易数据，同步到本地节点
 * @Author: lidong
 * @CreateDate: 2018/9/19$ 2:15 PM$
 * @UpdateUser: lidong
 * @UpdateDate: 2018/9/19$ 2:15 PM$
 * @UpdateRemark: TODO
 * @Version: 1.0
 **/
@Component
@EnableScheduling
public class OrderJob {

    @Autowired
    private OrderService orderService;

    @Value("${app.endpoints.local:127.0.0.1}:${app.ports.blockchain:8109}")
    private String localChainServerPort;

    @Value("${app.endpoints.order:127.0.0.1}:${app.ports.blockchain:8109}")
    private String orderChainServerPort;

    /***
     * 每隔5秒钟拉取数据
     */
    @Scheduled(fixedRate = 8000)
    public void synchronousData() {
        // 如果是order节点，则不同步信息
        if (localChainServerPort.equalsIgnoreCase( orderChainServerPort )) return;
        orderService.synchronousData();
    }
}

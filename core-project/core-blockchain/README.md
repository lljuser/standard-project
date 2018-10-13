## 设计思路
- Block 区块结构类
- Blockchain 区块链类 
  - 创建或恢复链
  - 创建块 验证块 验证链上块
  - 持久久存储
- Transaction 交易结构信息类 可搭载具体有交易或确认数据或文件数据
- ProofOfWork 工作量证明计算挖矿类
- IBlockchainStorage 持久化存储接口类 目前有两个简单的实现类
# RMI

RMI特性用于组件的分布式调用，能够有效减少目标网站反爬虫机制触发几率，由于在顶层有效的封装，
模块贡献者无需额外开发并可以获得此特性，只需继承AbstractRemoteService或者其子类即可
唯一需要注意的是，所有模块内部参数必须实现Serializable接口，本框架entity顶层接口或者
顶层抽象类皆已继承Serializable接口。

# 使用方法

## 配置文件config.rmi

配置文件中可以添加RMI相应配置,“##”为注释符，代表此行被忽略，

### master_req_ip

master_req_ip代表master请求slave服务的slave地址，格式为ip:port（例如：192.168.1.100:7779）
在master端，master_req_ip可以配置多个，以减轻请求负载，前提是slave为不同的公网IP。
程序框架中默认随机选取其中一个slave为master进行服务，当然，后续会拓展更多策略，
如果你想贡献更高效率的slave调度策略，可以实现SlaveChooser接口。

### slave_rcv_ip

slave_rcv_ip代表slave部署机器的本地ip地址，格式为ip:port（例如：192.168.1.100:7779）
在linux机器上，为了稳妥的部署slave集群，最好将hostname改为本机公网IP。

## 部署服务

由于程序顶层类做了大部分工作，服务的部署尤其简单，以按行业分类获取所有股票为例子

###普通版本：

```java

    //按行业分类获取所有股票
    public void IndustryStockInfo() throws RemoteException {

        CommissionIndustryCollector collector = new CommissionIndustryCollector();
        IndustryToStocksMapper mapper = new IndustryToStocksMapper();
        Map<Industry, List<Stock>> res = collector.get()
                .parallelStream()
                .map(mapper)
                .flatMap(Collection::stream)
                .collect(Collectors.groupingBy(Stock::getIndustry));

        for (Map.Entry<Industry, List<Stock>> entry : res.entrySet()) {
            for (Stock stock : entry.getValue()) {
                System.out.println(entry.getKey().getIndustryName() + " -> " + stock.getStockName());
            }
        }

    }       
         
```

###分布式版本：

* slave集群配置（先配置好config.rmi，以及hostname）

```java

    @Test
    public void IndustryStockInfoSlave() throws RemoteException {
        CommissionIndustryCollector collector = new CommissionIndustryCollector();
        IndustryToStocksMapper mapper = new IndustryToStocksMapper();
        collector.asRMISlave();//切换为slave模式
        mapper.asRMISlave();//切换为slave模式
    }

```

* master配置

```java

    @Test
    public void IndustryStockInfoMaster() throws RemoteException {

        CommissionIndustryCollector collector = new CommissionIndustryCollector();
        IndustryToStocksMapper mapper = new IndustryToStocksMapper();
        collector.asRMIMaster();
        collector.setSlaveChooser(new RandomSlaveChooser());//设置slave调度策略
        collector.setRMIOnly(true); //仅通过远程调用计算结果
        mapper.asRMIMaster();
        mapper.setRMIOnly(true); //仅通过远程调用计算结果
        
        Map<Industry, List<Stock>> res = collector.get()
                .parallelStream()
                .map(mapper)
                .flatMap(Collection::stream)
                .collect(Collectors.groupingBy(Stock::getIndustry));

        for (Map.Entry<Industry, List<Stock>> entry : res.entrySet()) {
            for (Stock stock : entry.getValue()) {
                System.out.println(entry.getKey().getIndustryName() + " -> " + stock.getStockName());
            }
        }

    }

```

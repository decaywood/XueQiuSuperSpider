#雪球超级爬虫
<a href="http://junit.org/"><img src="https://img.shields.io/badge/Test-JUnit-orange.svg?style=flat"></a>
<a href="http://jsoup.org/"><img src="https://img.shields.io/badge/Dependency-Jsoup-yellow.svg?style=flat"></a>
<a href="http://jackson-users.ning.com/"><img src="https://img.shields.io/badge/Dependency-Jackson-blue.svg?style=flat"></a>
<a href="http://dev.mysql.com/"><img src="https://img.shields.io/badge/Database-MySQL-red.svg?style=flat"></a>

##更新日志

2016.4.12 -- 更新url: http -\> https

2016.4.10 -- 增加自动登录接口,用户在config.sys 下配置用户名以及密码等信息可自动登录并提高爬虫权限。 具体配置格式如下:

```
areaCode = 86 可选 默认国内
userID = 186xxxxxxxx
password = xxxx
rememberMe = true 可选 默认开启
```

2016.2.20 -- 修复雪球后台cookie设置变更所导致的程序更新cookie失效问题 Issues #2

2015.12.4 -- 增加根据股票获取公司信息功能（公司名称、组织形式、成立日期、经营范围、主营业务、地区代码、所属板块）

2015.12.7 -- 游资追踪增加模糊匹配特性

2015.12.10 -- 添加了必要的代码注释，添加了统计一阳穿三线个股的example，另外：雪球网貌似因为我大量抓取数据已经采取反爬虫措施
各位用的时候最好修改一下抓取频率，或者不要一次性抓取太多信息，对于雪球网的输入验证码，应对措施如下：

当日志超时重试次数达到阈值时，很可能雪球封住了你的IP，这时候人工登录雪球网输入验证码后，代码经过重试等待时间后会自动恢复
抓取数据流程。

12.14 -- 更新高级特性，增加RMI分布式数据抓取特性，可将需要访问网络的组件部署至Slave集群，分散流量，
防止被反爬虫机制锁定。   [RMI高级特性](https://github.com/decaywood/XueQiuSuperSpider/blob/master/info/RMI.md)

##前言

雪球网或者东方财富或者同花顺目前已经提供了很多种股票筛选方式，但是筛选方式是根据个人操作
风格来定义的，三个网站有限的筛选方式显然不能满足广大股民、程序员特别是数据分析控的要求，
基于此，本人设计了一个可以任意拓展，实现任意数据搜集与分析的爬虫程序，满足股友们的需要，
只要你能想到的数据搜集与分析策略它都能实现。
（项目严重依赖JDK8新特性，偏重函数式编程思想，不熟悉的已备好教程以及例子：
[Java8 简明教程](http://decaywood.github.io/2015/12/23/Java8-guide/)）

##结构

雪球超级爬虫的所有组件互相没有任何依赖，包括参数。整体架构由Collector、Mapper
以及Consumer三个接口支撑。功能分别为数据搜集、数据相关信息（分支信息）的组装、以及最终
的数据分析，三个接口定义了整个数据抓取生命周期的三个阶段。Mapper组件可以进行多次
嵌套，就像流水线一样，不同的Mapper负责自己对应的组装任务，经过N个Mapper，
完成一个对象的N种属性组装，当然，如果你不需要某些属性，你完全可以跳过某些mapper，
这样可以节省许多抓取时间。在参数传递方面，模块在处理参数之前会对参数进行深度复制，
确保不会出现多线程同步问题，模块内部参数严格定义为只读。变量只局限在方法范围内，
完全避免了线程间数据共享。

![](https://github.com/decaywood/XueQiuSuperSpider/blob/master/info/structure.png)

##优势

* 稳定

对模块内部数据的严格限制保证了无论是如何频繁的多线程操作都不会出现脏读，写覆盖等令人头
疼的并发问题，模块之间无依赖，非常容易进行单元测试，只要模块都通过严格的单元测试，
无论多么复杂的数据搜集以及分析逻辑，雪球超级爬虫都能稳定的工作，运行后不用死死的
盯着日志，安心睡觉。


* 高性能

所有模块顶层实现了Java8的函数式接口，能够依靠Java8的并行流进行高并发操作，用户
可以轻易配置线程池缓存工作线程数量，充分发挥网络IO资源以及CPU资源。


* 极易改造与拓展

散文式的结构决定了雪球超级爬虫是一个可以进行任意拓展的程序，任何人都可以稍作了解
后贡献自己的代码，你甚至可以爬取同花顺网站的数据然后结合东方财富的一些资料，再混合
雪球网站自己数据综合进行分析，你所做的只是添加几个Collector和Mapper而已，很多基础
的模块我已经提供好了。（是不是和Python有点像）

##贡献模块的一些注意事项

* 参数对象请实现DeepCopy接口

对于你定义了在模块间传递的对象，请实现DeepCopy接口，就像我上面提到的，模块间是
不允许共享对象的，模块约定复制传入的参数。

```java

    public interface DeepCopy <R> {
        R copy();
    }
    
```

* 对于只读的域变量请定义为final

为了防止你对域的误写，请将域定义为final，这样更加保险

* 定义可变变量默认值

对于可变变量，初始化时请使用EmptyObject类定义默认值，如果是Entity，请按规范定义好EmptyXX子类，
遵守此规范能够很好的防止空指针异常。

* 组件无状态

模块必须保证为无状态的，避免出现行为不一致的情况，尤其是分布式协同工作时，无状态的
组件能够有效保证整个程序的正确性以及稳定性。

* 定义默认行为

每个模块提供合理的默认值，减少用户在配置上消耗的精力。

* 完善的单元测试

完成一个模块后，请进行完善的单元测试，包括初始化参数合法性。
输入输出的正确性。

* 具备交换律

特别注意的是，mapper与mapper之间输入与输出参数类型如果相同，请保证遵守交换律，
即 M1.andThen(M2) equals M2.andThen(M1)

* 推荐继承模块模版

每个生命周期对应的接口皆有对应的抽象模板类，他们通过了单元测试，封装了实用的功能，
包括网络请求、Exception捕获，网络IO异常重新请求以及Cookie更新保存等有用的功能，
除非有必要，否则推荐继承模版模块，它们以Abstract开头，支持泛型。

##一些例子

* 统计一阳穿三线个股
```java


    @Test
    public void yiyinsanyang() {
        List<Stock> stocks = TestCaseGenerator.generateStocks();

        StockToStockWithAttributeMapper attributeMapper = new StockToStockWithAttributeMapper();
        StockToStockWithStockTrendMapper trendMapper = new StockToStockWithStockTrendMapper();

        Predicate<Entry<String, Stock>> predicate = x -> {

            if(x.getValue().getStockTrend().getHistory().isEmpty()) return false;
            List<StockTrend.TrendBlock> history = x.getValue().getStockTrend().getHistory();
            StockTrend.TrendBlock block = history.get(history.size() - 1);
            double close = Double.parseDouble(block.getClose());
            double open = Double.parseDouble(block.getOpen());
            double ma5 = Double.parseDouble(block.getMa5());
            double ma10 = Double.parseDouble(block.getMa10());
            double ma30 = Double.parseDouble(block.getMa30());

            double max = Math.max(close, open);
            double min = Math.min(close, open);

            return close > open && max >= MathUtils.max(ma5, ma10, ma30) && min <= MathUtils.min(ma5, ma10, ma30);

        };

        stocks.parallelStream()
                .map(x -> new Entry<>(x.getStockName(), attributeMapper.andThen(trendMapper).apply(x)))
                .filter(predicate)
                .map(Entry::getKey)
                .forEach(System.out::println);

    }    

```


* 简单的根据关键字获取近期新闻

```java 

    @Test
    public void findNewsUcareAbout() {
        List<URL> news = new HuShenNewsRefCollector(HuShenNewsRefCollector.Topic.TOTAL, 2).get();
        List<URL> res = news.parallelStream().filter(new PageKeyFilter("万孚生物", false)).collect(Collectors.toList());

        List<URL> regexRes = news.parallelStream().filter(new PageKeyFilter("万孚生物", true)).collect(Collectors.toList());
        for (URL re : regexRes) {
            System.out.println("Regex : " + re);
        }
        for (URL re : res) {
            System.out.println("nonRegex : " + re);
        }
    }

```

* 游资追踪

```java

     @Test
     public void LongHuBangTracking() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2015, Calendar.NOVEMBER, 20);
        Date from = calendar.getTime();
        calendar.set(2015, Calendar.DECEMBER, 1);
        Date to = calendar.getTime();
        DateRangeCollector collector = new DateRangeCollector(from, to);
        DateToLongHuBangStockMapper mapper = new DateToLongHuBangStockMapper();
        StockToLongHuBangMapper mapper1 = new StockToLongHuBangMapper();
        List<LongHuBangInfo> s = collector.get()
                .parallelStream()
                .map(mapper)
                .flatMap(List::stream).map(mapper1)
                .filter(x -> x.bizsunitInBuyList("中信证券股份有限公司上海溧阳路证券营业部"))
                .sorted(Comparator.comparing(LongHuBangInfo::getDate))
                .collect(Collectors.toList());
        for (LongHuBangInfo info : s) {
            System.out.println(info.getDate() + " -> " + info.getStock().getStockName());
        }

     }
```




* 统计股票5000粉以上大V个数，并以行业分类股票

```java

     @Test
     public void getStocksWithVipFollowersCount() {
        CommissionIndustryCollector collector = new CommissionIndustryCollector();//搜集所有行业
        IndustryToStocksMapper mapper = new IndustryToStocksMapper();//搜集每个行业所有股票
        StockToVIPFollowerCountMapper mapper1 = new StockToVIPFollowerCountMapper(5000, 20);//搜集每个股票的粉丝
        UserInfoToDBConsumer consumer = new UserInfoToDBConsumer();//写入数据库
        System.setProperty("java.util.concurrent.ForkJoinPool.common.parallelism", "20");//设置线程数量
        
        List<Entry<Stock, Integer>> res = collector.get()
                .parallelStream() //并行流
                .map(mapper)
                .flatMap(Collection::stream)
                .map(x -> new Entry<>(x, mapper1.apply(x)))
                .peek(consumer)
                .collect(Collectors.toList());
        for (Entry<Stock, Integer> re : res) {
            System.out.println(re.getKey().getStockName() + " -> 5000粉丝以上大V个数  " + re.getValue());
        }
     }
```

* 最赚钱组合最新持仓以及收益走势、大盘走势

```java

     @Test
     public void MostProfitableCubeDetail() {
        
        Calendar calendar = Calendar.getInstance();
        calendar.set(2015, Calendar.OCTOBER, 20);
        Date from = calendar.getTime();
        calendar.set(2015, Calendar.NOVEMBER, 25);
        Date to = calendar.getTime();
        MostProfitableCubeCollector cubeCollector = new MostProfitableCubeCollector( MostProfitableCubeCollector.Market.CN,
                MostProfitableCubeCollector.ORDER_BY.DAILY);
        CubeToCubeWithLastBalancingMapper mapper = new CubeToCubeWithLastBalancingMapper();
        CubeToCubeWithTrendMapper mapper1 = new CubeToCubeWithTrendMapper(from, to);
        List<Cube> cubes = cubeCollector
                 .get()
                 .parallelStream()
                 .map(mapper.andThen(mapper1))
                 .collect(Collectors.toList());
        for (Cube cube : cubes) {
            System.out.print(cube.getName() + " 总收益: " + cube.getTotal_gain());
            System.out.println(" 最新持仓 " + cube.getRebalancing().getHistory().get(1).toString());
        }
     }
     
```

* 获取热股榜股票信息

```java

     @Test
     public void HotRankStockDetail() {
        StockScopeHotRankCollector collector = new StockScopeHotRankCollector(StockScopeHotRankCollector.Scope.US_WITHIN_24_HOUR);
        StockToStockWithAttributeMapper mapper1 = new StockToStockWithAttributeMapper();
        StockToStockWithStockTrendMapper mapper2 = new StockToStockWithStockTrendMapper(StockTrend.Period.ONE_DAY);
        List<Stock> stocks = collector.get().parallelStream().map(mapper1.andThen(mapper2)).collect(Collectors.toList());
        for (Stock stock : stocks) {
            System.out.print(stock.getStockName() + " -> ");
            System.out.print(stock.getAmplitude() + " " + stock.getOpen() + " " + stock.getHigh() + " and so on...");
            System.out.println(" trend size: " + stock.getStockTrend().getHistory().size());
        }
     }

```

* 获得某个行业所有股票的详细信息和历史走势 比如畜牧业

```java

     public void IndustryStockDetail() {
        
        CommissionIndustryCollector collector = new CommissionIndustryCollector();
        IndustryToStocksMapper mapper = new IndustryToStocksMapper();
        StockToStockWithAttributeMapper mapper1 = new StockToStockWithAttributeMapper();
        StockToStockWithStockTrendMapper mapper2 = new StockToStockWithStockTrendMapper();
        Map<Industry, List<Stock>> res = collector.get()
                .parallelStream()
                .filter(x -> x.getIndustryName().equals("畜牧业"))
                .map(mapper)
                .flatMap(Collection::stream)
                .map(mapper1.andThen(mapper2))
                .collect(Collectors.groupingBy(Stock::getIndustry));
        
        for (Map.Entry<Industry, List<Stock>> entry : res.entrySet()) {
            for (Stock stock : entry.getValue()) {
                System.out.print(entry.getKey().getIndustryName() + " -> " + stock.getStockName() + " -> ");
                System.out.print(stock.getAmount() + " " + stock.getChange() + " " + stock.getDividend() + " and so on...");
                System.out.println(" trend size: " + stock.getStockTrend().getHistory().size());
           }
       }
     }
```

* 按行业分类获取所有股票

```java

     @Test
     public void IndustryStockInfo() {
      
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


### LICENSE <a href="https://github.com/decaywood/XueQiuSuperSpider/blob/master/LICENSE"><img src="https://img.shields.io/badge/license-MIT-green.svg?style=flat"></a>


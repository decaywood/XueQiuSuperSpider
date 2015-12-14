package collectTest;

import org.decaywood.collector.StockSlectorBaseCollector;
import org.decaywood.entity.Stock;
import org.decaywood.entity.selectorQuota.BasicQuota;
import org.decaywood.entity.selectorQuota.MarketQuotationsQuota;
import org.decaywood.entity.selectorQuota.XueQiuQuota;
import org.junit.Assert;
import org.junit.Test;

import java.rmi.RemoteException;
import java.util.List;

/**
 * @author: decaywood
 * @date: 2015/11/28 21:05.
 */
public class StockSlectorBaseCollectorTest {

    @Test
    public void testFunc() throws RemoteException {
        StockSlectorBaseCollector collector = new StockSlectorBaseCollector();
        List<Stock> stocks = collector.get();
        Assert.assertFalse(stocks.isEmpty());
    }


    @Test
    public void integrateTest() throws RemoteException {
        StockSlectorBaseCollector collector = new StockSlectorBaseCollector();
        BasicQuota basicQuota = new BasicQuota();
        basicQuota.setMc(0, 100); //总市值(亿)
        basicQuota.setFmc(50, 100);//流通市值(亿)
        XueQiuQuota xueQiuQuota = new XueQiuQuota();
        xueQiuQuota.setFollow(103487, 183429);//关注人数
        MarketQuotationsQuota marketQuotationsQuota = new MarketQuotationsQuota();
        marketQuotationsQuota.setPct1m(-1.60, 40.17);//一个月涨幅
        collector.addQuotaChainNode(basicQuota);
        collector.addQuotaChainNode(xueQiuQuota);
        collector.addQuotaChainNode(marketQuotationsQuota);
        List<Stock> stocks = collector.get();
        for (Stock stock : stocks) {
            System.out.println(stock.getStockName() + "  " + stock.getStockNo());
        }
    }


    @Test
    public void testMarketQuotationsQuota() throws RemoteException {
        StockSlectorBaseCollector collector = new StockSlectorBaseCollector();
        MarketQuotationsQuota quota = new MarketQuotationsQuota();
        quota.setCurrent(0, 21.51f); // 当前价 0 ~ 21.51
        quota.setChgpct1m(52.95f, 70.03f); //一个月内振幅 52.95% ~ 70.03%
        quota.setPct20(-35.78f, 11.69f); // 前20日涨跌幅 -35.78% ~ 11.69%
        collector.addQuotaChainNode(quota);
        List<Stock> stocks = collector.get();
        for (Stock stock : stocks) {
            System.out.println(stock.getStockName() + "  " + stock.getStockNo());
        }
    }



    @Test
    public void testBasicQuota1() throws RemoteException {
        StockSlectorBaseCollector collector = new StockSlectorBaseCollector();
        BasicQuota quota = new BasicQuota();
        quota.setBps(1.84, 4.22); //每股净资产
        quota.setDy(1.39f, 2.94f); //股息率
        quota.setEps(-1.23f, 0.77); //每股收益
        quota.setFmc(0, 1369.86);//流通值
        collector.addQuotaChainNode(quota);
        List<Stock> stocks = collector.get();
        for (Stock stock : stocks) {
            System.out.println(stock.getStockName() + "  " + stock.getStockNo());
        }
    }

    @Test
    public void testBasicQuota2() throws RemoteException {
        StockSlectorBaseCollector collector = new StockSlectorBaseCollector();
        BasicQuota quota = new BasicQuota();
        quota.setMc(217.65, 1088.23); //总市值(亿)
        quota.setPb(0, 269.33); //市净率(倍)
        quota.setNetprofit(248104.21, 1900213.48);
        quota.setPelyr(50, 2263.78);//静态市盈率(倍)
        collector.addQuotaChainNode(quota);
        List<Stock> stocks = collector.get();
        for (Stock stock : stocks) {
            System.out.println(stock.getStockName() + "  " + stock.getStockNo());
        }
    }

    @Test
    public void testBasicQuota3() throws RemoteException {
        StockSlectorBaseCollector collector = new StockSlectorBaseCollector();
        XueQiuQuota quota = new XueQiuQuota();
        collector.addQuotaChainNode(quota);
        quota.setDeal(0, 637.26); //累计交易分享数
        quota.setDeal7d(7.68, 12.48); //一周新增交易分享数
        quota.setDeal7dpct(13.70, 18.03); //一周交易分享增长率(%)
        List<Stock> stocks = collector.get();
        for (Stock stock : stocks) {
            System.out.println(stock.getStockName() + "  " + stock.getStockNo());
        }
    }

    @Test
    public void testXueQiuQuota1() throws RemoteException {
        StockSlectorBaseCollector collector = new StockSlectorBaseCollector();
        XueQiuQuota quota = new XueQiuQuota();
        quota.setFollow(348016.52, 705405); //累计关注人数
        quota.setFollow7d(1163.05, 1867.01); //一周新增关注
        quota.setFollow7dpct(0.03, 3.44); //一周关注增长率
        collector.addQuotaChainNode(quota);
        List<Stock> stocks = collector.get();
        for (Stock stock : stocks) {
            System.out.println(stock.getStockName() + "  " + stock.getStockNo());
        }


    }

    @Test
    public void testXueQiuQuota2() throws RemoteException {
        StockSlectorBaseCollector collector = new StockSlectorBaseCollector();
        XueQiuQuota quota = new XueQiuQuota();
        collector.addQuotaChainNode(quota);
        quota.setTweet(5125.12, 17895.42); //总讨论数
        quota.setTweet7d(0, 334.88); //一周讨论数
        quota.setTweet7dpct(4.01, 13.71); //一周讨论增长率
        List<Stock> stocks = collector.get();
        for (Stock stock : stocks) {
            System.out.println(stock.getStockName() + "  " + stock.getStockNo());
        }
    }

    @Test
    public void testXueQiuQuota3() throws RemoteException {
        StockSlectorBaseCollector collector = new StockSlectorBaseCollector();
        XueQiuQuota quota = new XueQiuQuota();
        collector.addQuotaChainNode(quota);
        quota.setDeal(0, 637.26); //累计交易分享数
        quota.setDeal7d(7.68, 12.48); //一周新增交易分享数
        quota.setDeal7dpct(13.70, 18.03); //一周交易分享增长率(%)
        List<Stock> stocks = collector.get();
        for (Stock stock : stocks) {
            System.out.println(stock.getStockName() + "  " + stock.getStockNo());
        }
    }

}

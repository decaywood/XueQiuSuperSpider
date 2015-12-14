import org.decaywood.collector.HuShenNewsRefCollector;
import org.decaywood.entity.Stock;
import org.decaywood.mapper.dateFirst.DateToLongHuBangStockMapper;
import org.junit.Test;

import java.net.URL;
import java.rmi.RemoteException;
import java.util.Calendar;
import java.util.List;

/**
 * @author: decaywood
 * @date: 2015/12/11 15:38
 */
public class RMITest {


    @Test
    public void RMICollectorTest() throws RemoteException {

        HuShenNewsRefCollector slave = new HuShenNewsRefCollector(HuShenNewsRefCollector.Topic.TOTAL, 1);
        slave.asRMISlave();

        HuShenNewsRefCollector master = new HuShenNewsRefCollector(HuShenNewsRefCollector.Topic.TOTAL, 1);
        master.setRMIOnly(true); //仅通过远程调用计算结果
        master.asRMIMaster();//RMI客户端
        List<URL> urls = master.get();
        for (URL url : urls) {
            System.out.println(url);
        }
    }


    //远程调用组件功能
    @Test
    public void RMIMapperTest() throws RemoteException {

        /*
            slave组件（远程服务器部署）
         */
        DateToLongHuBangStockMapper slave = new DateToLongHuBangStockMapper();
        slave.asRMISlave();

         /*
            master组件（负责控制）
         */
        DateToLongHuBangStockMapper master = new DateToLongHuBangStockMapper();
        master.setRMIOnly(true);
        master.asRMIMaster();
        Calendar calendar = Calendar.getInstance();
        calendar.set(2015, Calendar.DECEMBER, 11);
        List<Stock> stocks = master.apply(calendar.getTime());
        for (Stock stock : stocks) {
            System.out.println(stock.getStockName());
        }

    }


}

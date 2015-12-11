import org.decaywood.collector.HuShenNewsRefCollector;
import org.decaywood.timeWaitingStrategy.DefaultTimeWaitingStrategy;
import org.decaywood.timeWaitingStrategy.TimeWaitingStrategy;
import org.junit.Test;

import java.net.URL;
import java.util.List;

/**
 * @author: decaywood
 * @date: 2015/12/11 15:38
 */
public class RMITest {

    @Test
    public void RMICollectorClientTest() {
        TimeWaitingStrategy strategy = new DefaultTimeWaitingStrategy(1,1,0);
        HuShenNewsRefCollector collector = new HuShenNewsRefCollector(
                strategy,
                HuShenNewsRefCollector.Topic.TOTAL, 1);
        collector.asRMIClient();
        List<URL> urls = collector.get();
        for (URL url : urls) {
            System.out.println(url);
        }
    }

    @Test
    public void RMICollectorServerTest() {
        HuShenNewsRefCollector collector = new HuShenNewsRefCollector(HuShenNewsRefCollector.Topic.TOTAL, 1);
        collector.asRMIClient();
        List<URL> urls = collector.get();
        for (URL url : urls) {
            System.out.println(url);
        }
    }
}

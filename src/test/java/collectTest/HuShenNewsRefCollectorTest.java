package collectTest;

import org.decaywood.collector.HuShenNewsRefCollector;
import org.junit.Assert;
import org.junit.Test;

import java.net.URL;
import java.rmi.RemoteException;
import java.util.List;

/**
 * @author: decaywood
 * @date: 2015/12/2 22:18.
 */
public class HuShenNewsRefCollectorTest {

    @Test
    public void testCorrectArg() throws RemoteException {
        new HuShenNewsRefCollector(HuShenNewsRefCollector.Topic.TOTAL, 3);
    }


    @Test(expected = IllegalArgumentException.class)
    public void testWrongArgument2() throws RemoteException {
        new HuShenNewsRefCollector(HuShenNewsRefCollector.Topic.TOTAL, 0);
    }

    @Test
    public void testFunc() throws RemoteException {
        List<URL> res = new HuShenNewsRefCollector().get();
        Assert.assertFalse(res.isEmpty());
    }
}

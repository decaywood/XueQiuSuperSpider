package mapperTest;

import org.decaywood.collector.HuShenNewsRefCollector;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * @author: decaywood
 * @date: 2015/12/2 22:18.
 */
public class HuShenNewsRefCollectorTest {

    @Test
    public void testCorrectArg() {
        new HuShenNewsRefCollector(5, 3);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testWrongArgument1() {
        new HuShenNewsRefCollector(4, 3);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testWrongArgument2() {
        new HuShenNewsRefCollector(5, 0);
    }

    @Test
    public void testFunc() {
        List<String> res = new HuShenNewsRefCollector().get();
        for (String re : res) {
            System.out.println(re);
        }
        Assert.assertFalse(res.isEmpty());
    }
}

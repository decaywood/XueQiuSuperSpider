package filterTest;

import org.decaywood.filter.PageKeyFilter;
import org.junit.Test;

import java.net.URL;

/**
 * @author: decaywood
 * @date: 2015/12/3 10:26
 */
public class PageKeyFilterTest {


    @Test
    public void testFunc() throws Exception {
        URL url = new URL("http://xueqiu.com/2306062563/60965010");
        PageKeyFilter filter = new PageKeyFilter("协鑫集成", false);
        boolean res = filter.test(url);
        System.out.println(res);
    }
}

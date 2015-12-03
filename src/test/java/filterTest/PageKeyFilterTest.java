package filterTest;

import org.decaywood.filter.PageKeyFilter;
import org.junit.Test;

import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author: decaywood
 * @date: 2015/12/3 10:26
 */
public class PageKeyFilterTest {


    @Test
    public void testFunc() throws Exception {
        URL url = new URL("http://xueqiu.com/2306062563/60965010");
        PageKeyFilter filter = new PageKeyFilter("协鑫集成", true);
        boolean res = filter.test(url);
        System.out.println(res);
    }

    @Test
    public void regexTest() {
        Pattern pattern = Pattern.compile("哥");
        Matcher matcher = pattern.matcher("阿迪啊无法无法阿哥挨饿不是人");
        boolean res = matcher.find();
        System.out.println(res);
    }

}

package utilTest;

import org.decaywood.utils.EmptyObject;
import org.decaywood.utils.StringUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author: decaywood
 * @date: 2015/11/26 10:23
 */
public class StringUtilsTest {

    @Test
    public void digitCheckTest() {
        /*
            '-' is not acceptable
         */
        String case1 = "125125125135";
        String case2 = "-1245124124"; // not acceptable
        String case3 = "afwaw12412aw";
        String case4 = "12124w124124";
        String case5 = "";

        Assert.assertTrue(StringUtils.isNumeric(case1));
        Assert.assertFalse(StringUtils.isNumeric(case2));
        Assert.assertFalse(StringUtils.isNumeric(case3));
        Assert.assertFalse(StringUtils.isNumeric(case4));
        Assert.assertFalse(StringUtils.isNumeric(case5));
        Assert.assertFalse(StringUtils.isNumeric(null));

    }

    @Test
    public void notNullOrEmptyCheckTest() {
        Assert.assertTrue(StringUtils.nullOrEmpty("xx", null, "xxxx"));
        Assert.assertTrue(StringUtils.nullOrEmpty("xx", null, EmptyObject.emptyString));
        Assert.assertTrue(StringUtils.nullOrEmpty("xx", "", "xx"));
        Assert.assertFalse(StringUtils.nullOrEmpty("xx", "xx", "xxx"));
    }

    @Test
    public void unicodeTest() {
        Assert.assertEquals("雪球超级爬虫", StringUtils.unicode2String(StringUtils.string2Unicode("雪球超级爬虫")));
    }

}

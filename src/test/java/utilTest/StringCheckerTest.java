package utilTest;

import org.decaywood.utils.StringChecker;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author: decaywood
 * @date: 2015/11/26 10:23
 */
public class StringCheckerTest {

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

        Assert.assertTrue(StringChecker.isNumeric(case1));
        Assert.assertFalse(StringChecker.isNumeric(case2));
        Assert.assertFalse(StringChecker.isNumeric(case3));
        Assert.assertFalse(StringChecker.isNumeric(case4));
        Assert.assertFalse(StringChecker.isNumeric(case5));
        Assert.assertFalse(StringChecker.isNumeric(null));

    }

}

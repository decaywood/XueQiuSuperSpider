package utilTest;

import org.decaywood.utils.FileLoader;
import org.junit.Test;

/**
 * @author: decaywood
 * @date: 2015/12/2 10:52
 */
public class FileLoaderTest {

    @Test
    public void testFileLoader() {
        FileLoader.updateCookie("I am a cookie", "baidu");
    }

}

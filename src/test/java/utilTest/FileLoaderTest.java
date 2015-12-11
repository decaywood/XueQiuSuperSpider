package utilTest;

import org.decaywood.utils.FileLoader;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;

/**
 * @author: decaywood
 * @date: 2015/12/2 10:52
 */
public class FileLoaderTest {

    @Test
    public void testFileLoader() {
        FileLoader.updateCookie("I am a cookie", "baidu");
        File file = FileLoader.loadFile("config.sys");
        Assert.assertTrue(file.exists());
    }


}

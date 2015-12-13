import org.decaywood.GlobalSystemConfigLoader;
import org.junit.Test;

/**
 * @author: decaywood
 * @date: 2015/12/11 13:33
 */
public class GlobalSystemConfigLoaderTest {

    @Test
    public void test() {
        GlobalSystemConfigLoader.loadConfig();
//        Assert.assertEquals("192.168.1.155:7779", GlobalSystemConfigLoader.getRMIConfig("server_rcv_ip"));
    }

}

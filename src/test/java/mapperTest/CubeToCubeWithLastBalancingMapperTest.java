package mapperTest;

import org.decaywood.entity.Cube;
import org.decaywood.mapper.cubeFirst.CubeToCubeWithLastBalancingMapper;
import org.junit.Assert;
import org.junit.Test;

import java.rmi.RemoteException;

/**
 * @author: decaywood
 * @date: 2015/11/26 16:08
 */
public class CubeToCubeWithLastBalancingMapperTest {

    @Test(expected = IllegalArgumentException.class)
    public void testWrongArgument() throws RemoteException {

        CubeToCubeWithLastBalancingMapper cubeCubeFirst =
                new CubeToCubeWithLastBalancingMapper(-1);
        Cube cube = new Cube("446272", "港股低价倍增", "ZH221124");
        Cube mapped = cubeCubeFirst.apply(cube);
    }

    @Test
    public void testFunction() throws RemoteException {

        CubeToCubeWithLastBalancingMapper cubeCubeFirst =
                new CubeToCubeWithLastBalancingMapper(5);
        Cube cube = new Cube("446272", "港股低价倍增", "ZH221124");
        Cube mapped = cubeCubeFirst.apply(cube);
        Assert.assertNotNull(mapped.getRebalancing());
        Assert.assertFalse(mapped.getRebalancing().getHistory().isEmpty());

    }

}

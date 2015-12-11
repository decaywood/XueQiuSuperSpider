package mapperTest;

import org.decaywood.entity.Cube;
import org.decaywood.mapper.cubeFirst.CubeToCubeWithTrendMapper;
import org.junit.Assert;
import org.junit.Test;

import java.rmi.RemoteException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author: decaywood
 * @date: 2015/11/26 11:15
 */
public class CubeToCubeWithTrendMapperTest {

    @Test(expected = IllegalArgumentException.class)
    public void testWrongArgument() throws RemoteException {

        Calendar calendar = Calendar.getInstance();

        calendar.set(2014, Calendar.AUGUST, 5);
        Date since = calendar.getTime();

        calendar.set(2015, Calendar.MAY, 28);
        Date until = calendar.getTime();

        new CubeToCubeWithTrendMapper(until, since);

    }

    @Test
    public void testFunction() throws RemoteException {

        Calendar calendar = Calendar.getInstance();

        calendar.set(2014, Calendar.AUGUST, 5);
        Date since = calendar.getTime();

        calendar.set(2015, Calendar.MAY, 28);
        Date until = calendar.getTime();

        CubeToCubeWithTrendMapper cubeCubeFirst = new CubeToCubeWithTrendMapper(since, until);

        List<Cube> cubes = TestCaseGenerator.generateCube();

        cubes.stream()
                .map(cubeCubeFirst)
                .peek(Assert::assertNotNull)
                .map(Cube::getCubeTrend)
                .forEach(cubeTrend -> Assert.assertNotNull(cubeTrend.getHistory()));

        cubes.stream()
                .map(cubeCubeFirst)
                .peek(Assert::assertNotNull)
                .map(Cube::getMarketIndexTrend)
                .forEach(cubeTrend -> Assert.assertNotNull(cubeTrend.getHistory()));

    }

}

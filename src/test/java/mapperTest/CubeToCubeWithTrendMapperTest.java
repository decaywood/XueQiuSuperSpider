package mapperTest;

import org.decaywood.entity.Cube;
import org.decaywood.mapper.CubeFirst;
import org.decaywood.mapper.cubeFirst.CubeToCubeWithTrendMapper;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author: decaywood
 * @date: 2015/11/26 11:15
 */
public class CubeToCubeWithTrendMapperTest {

    @Test(expected = IllegalArgumentException.class)
    public void testWrongArgument() {

        Calendar calendar = Calendar.getInstance();

        calendar.set(2014, Calendar.AUGUST, 5);
        Date since = calendar.getTime();

        calendar.set(2015, Calendar.MAY, 28);
        Date until = calendar.getTime();

        new CubeToCubeWithTrendMapper(until, since);

    }

    @Test
    public void testFunction() {

        Calendar calendar = Calendar.getInstance();

        calendar.set(2014, Calendar.AUGUST, 5);
        Date since = calendar.getTime();

        calendar.set(2015, Calendar.MAY, 28);
        Date until = calendar.getTime();

        CubeFirst<Cube> cubeCubeFirst = new CubeToCubeWithTrendMapper(since, until);

        List<Cube> cubes = new ArrayList<>();
        cubes.add(new Cube("xxx", "xxx", "ZH128412", "xxx", "xxx", "xxx", "xxx")); //沪深组合
        cubes.add(new Cube("xxx", "xxx", "ZH102164", "xxx", "xxx", "xxx", "xxx")); //港股组合
        cubes.add(new Cube("xxx", "xxx", "ZH739627", "xxx", "xxx", "xxx", "xxx")); //美股组合

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

package mapperTest;

import org.decaywood.entity.Industry;
import org.decaywood.mapper.industryFirst.IndustryToStocksMapper;
import org.decaywood.utils.EmptyObject;
import org.junit.Assert;
import org.junit.Test;

import java.rmi.RemoteException;
import java.util.List;

/**
 * @author: decaywood
 * @date: 2015/11/25 19:04
 */
public class IndustryToStocksMapperTest {


    @Test
    public void testFunction() throws RemoteException {
        List<Industry> industries = TestCaseGenerator.generateIndustries();
        IndustryToStocksMapper mapper = new IndustryToStocksMapper();
        Assert.assertTrue(industries.stream()
                .map(mapper)
                .noneMatch(List::isEmpty));
    }

    @Test
    public void testNull() throws RemoteException {
        IndustryToStocksMapper mapper = new IndustryToStocksMapper();
        Assert.assertNotNull(mapper.apply(null));
        Assert.assertNotNull(mapper.apply(EmptyObject.emptyIndustry));
        Assert.assertTrue(mapper.apply(null).isEmpty());
        Assert.assertTrue(mapper.apply(EmptyObject.emptyIndustry).isEmpty());
    }



}

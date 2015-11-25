package mapperTest;

import org.decaywood.entity.Industry;
import org.decaywood.mapper.industryFirst.IndustryToIndustryWithStocksMapper;
import org.decaywood.utils.EmptyObject;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * @author: decaywood
 * @date: 2015/11/25 19:04
 */
public class IndustryToIndustryWithStocksMapperTest {


    @Test
    public void testFunction() {
        List<Industry> industries = TestCaseGenerator.generateIndustries();
        IndustryToIndustryWithStocksMapper mapper = new IndustryToIndustryWithStocksMapper();
        industries.stream()
                .map(mapper.andThen(Industry::getStocks).andThen(List::isEmpty))
                .forEach(Assert::assertFalse);
    }

    @Test
    public void testNull() {
        IndustryToIndustryWithStocksMapper mapper = new IndustryToIndustryWithStocksMapper();
        Assert.assertEquals(EmptyObject.emptyIndustry, mapper.apply(null));
        Assert.assertEquals(EmptyObject.emptyIndustry, mapper.apply(EmptyObject.emptyIndustry));
    }



}

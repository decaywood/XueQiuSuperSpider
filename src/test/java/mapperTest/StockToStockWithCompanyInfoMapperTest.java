package mapperTest;

import org.decaywood.entity.CompanyInfo;
import org.decaywood.entity.Stock;
import org.decaywood.mapper.stockFirst.StockToStockWithCompanyInfoMapper;
import org.decaywood.utils.StringUtils;
import org.junit.Assert;
import org.junit.Test;

import java.rmi.RemoteException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: decaywood
 * @date: 2015/12/4 16:00
 */
public class StockToStockWithCompanyInfoMapperTest {


    @Test
    public void testFunc() throws RemoteException {

        StockToStockWithCompanyInfoMapper mapper = new StockToStockWithCompanyInfoMapper();
        List<Stock> stocks = TestCaseGenerator.generateStocks();
        List<CompanyInfo> companyInfos = stocks.stream()
                .map(mapper.andThen(Stock::getCompanyInfo))
                .collect(Collectors.toList());
        for (CompanyInfo companyInfo : companyInfos) {
            Assert.assertFalse(StringUtils.nullOrEmpty(companyInfo.getBizscope()));
            Assert.assertFalse(StringUtils.nullOrEmpty(companyInfo.getCompsname()));
            Assert.assertFalse(StringUtils.nullOrEmpty(companyInfo.getFounddate()));
            Assert.assertFalse(StringUtils.nullOrEmpty(companyInfo.getMajorbiz()));
            Assert.assertFalse(StringUtils.nullOrEmpty(companyInfo.getOrgtype()));
            Assert.assertFalse(StringUtils.nullOrEmpty(companyInfo.getRegion()));
            Assert.assertFalse(companyInfo.getTqCompIndustryList().isEmpty());
        }

    }


}

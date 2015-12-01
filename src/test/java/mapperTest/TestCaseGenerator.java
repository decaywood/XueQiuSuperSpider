package mapperTest;

import org.decaywood.entity.Cube;
import org.decaywood.entity.Industry;
import org.decaywood.entity.Stock;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: decaywood
 * @date: 2015/11/25 19:04
 */
public class TestCaseGenerator {

    public static List<Industry> generateIndustries() {
        List<Industry> industries = new ArrayList<>();
        industries.add(new Industry("有色金属冶炼和压延加工业","#exchange=CN&plate=1_3_66&firstName=1&secondName=1_3&level2code=C32"));
        industries.add(new Industry("渔业","#exchange=CN&plate=1_3_67&firstName=1&secondName=1_3&level2code=A04"));
        industries.add(new Industry("医药制造业","#exchange=CN&plate=1_3_68&firstName=1&secondName=1_3&level2code=C27"));
        industries.add(new Industry("资本市场服务","#exchange=CN&plate=1_3_69&firstName=1&secondName=1_3&level2code=J67"));
        return industries;
    }


    public static List<Stock> generateStocks() {
        List<Stock> stocks = new ArrayList<>();
        stocks.add(new Stock("银之杰", "SZ300085"));
        stocks.add(new Stock("利德曼","SZ300289"));
        stocks.add(new Stock("国元证券","SZ000728"));
        return stocks;
    }

    public static List<Cube> generateCube() {
        List<Cube> cubes = new ArrayList<>();
        cubes.add(new Cube("xxx", "xxx", "ZH128412")); //沪深组合
        cubes.add(new Cube("xxx", "xxx", "ZH102164")); //港股组合
        cubes.add(new Cube("xxx", "xxx", "ZH739627")); //美股组合
        return cubes;
    }

}

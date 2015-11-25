package org.decaywood.mapper.industryFirst;

import com.fasterxml.jackson.databind.JsonNode;
import org.decaywood.entity.Industry;
import org.decaywood.entity.Stock;
import org.decaywood.mapper.AbstractMapper;
import org.decaywood.mapper.IndustryFirst;
import org.decaywood.timeWaitingStrategy.TimeWaitingStrategy;
import org.decaywood.utils.EmptyObject;
import org.decaywood.utils.HttpRequestHelper;
import org.decaywood.utils.RequestParaBuilder;
import org.decaywood.utils.URLMapper;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: decaywood
 * @date: 2015/11/23 14:04
 */
public class IndustryToIndustryWithStocksMapper extends AbstractMapper<Industry, Industry>
implements IndustryFirst<Industry> {


    public IndustryToIndustryWithStocksMapper(TimeWaitingStrategy strategy) {
        super(strategy);
    }

    public IndustryToIndustryWithStocksMapper() {
        this(null);
    }

    @Override
    public Industry mapLogic(Industry industry) throws Exception {

        if(industry == null || industry == EmptyObject.emptyIndustry) return EmptyObject.emptyIndustry;

        Industry industryCopy = industry.copy();
        String target = URLMapper.INDUSTRY_JSON.toString();
        RequestParaBuilder builder = new RequestParaBuilder(target);
        builder.addParameter("page", 1)
                .addParameter("size", 500)
                .addParameter("order", "desc")
                .addParameter("orderBy", "percent");
        String info = industryCopy.getIndustryInfo();
        if (info.startsWith("#")) info = info.substring(1);
        for (String s : info.split("&")) {
            String[] keyAndVal = s.split("=");
            builder.addParameter(keyAndVal[0], keyAndVal[1]);
        }
        URL url = new URL(builder.build());
        String json = new HttpRequestHelper()
                .addToHeader("Referer", URLMapper.MAIN_PAGE.toString())
                .request(url);
        return parserJson(industryCopy, json);

    }


    private Industry parserJson(Industry industry, String jsonContent) {

        List<Stock> stocks = new ArrayList<>();
        try {

            JsonNode root = mapper.readTree(jsonContent);
            JsonNode node = root.get("data");
            for (JsonNode jsonNode : node) {
                Stock stock = new Stock(jsonNode.get("name").asText(), jsonNode.get("symbol").asText());
                stocks.add(stock);
            }
            industry.setStocks(stocks);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return industry;

    }


}

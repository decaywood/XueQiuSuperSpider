package mapper;

import com.fasterxml.jackson.databind.JsonNode;
import entity.Entry;
import entity.Industry;
import entity.Stock;
import timeWaitingStrategy.TimeWaitingStrategy;
import utils.HttpRequestHelper;
import utils.RequestParaBuilder;
import utils.URLMapper;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: decaywood
 * @date: 2015/11/23 14:04
 */
public class IndustryToIndustryWithStockEntryMapper extends AbstractMapper<Industry, List<Entry<Industry, Stock>>> {


    public IndustryToIndustryWithStockEntryMapper(TimeWaitingStrategy strategy) {
        super(strategy);
    }

    public IndustryToIndustryWithStockEntryMapper() {
        this(null);
    }

    @Override
    public List<Entry<Industry, Stock>> mapLogic(Industry industry) throws Exception {

        String target = URLMapper.INDUSTRY_JSON.toString();
        RequestParaBuilder builder = new RequestParaBuilder(target);
        builder.addParameter("page", 1)
                .addParameter("size", 500)
                .addParameter("order", "desc")
                .addParameter("orderBy", "percent");
        URL url = new URL(builder.build());
        String info = industry.getIndustryInfo();
        if(info.startsWith("#")) info = info.substring(1);
        for (String s : info.split("&")) {
            String[] keyAndVal = s.split("=");
            builder.addParameter(keyAndVal[0], keyAndVal[1]);
        }
        String json = new HttpRequestHelper()
                .addToHeader("Referer", URLMapper.COMPREHENSIVE_PAGE.toString())
                .request(url);
        return parserJson(industry, json);

    }


    private List<Entry<Industry, Stock>> parserJson(Industry industry, String jsonContent) {
        List<Entry<Industry, Stock>> entries = new ArrayList<>();
        try {

            JsonNode root = mapper.readTree(jsonContent);
            JsonNode node = root.get("data");
            for (JsonNode jsonNode : node) {
                Stock stock = new Stock(jsonNode.get("name").asText(), jsonNode.get("symbol").asText());
                entries.add(new Entry<>(industry, stock));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return entries;
    }


}

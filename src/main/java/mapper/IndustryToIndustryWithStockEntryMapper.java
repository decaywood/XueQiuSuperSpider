package mapper;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import entity.Entry;
import entity.Industry;
import entity.Stock;
import utils.HttpRequestHelper;
import utils.RequestParaBuilder;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * @author: decaywood
 * @date: 2015/11/23 14:04
 */
public class IndustryToIndustryWithStockEntryMapper implements Function<Industry, List<Entry<Industry, Stock>>> {

    ObjectMapper mapper;

    public IndustryToIndustryWithStockEntryMapper() {
        this.mapper = new ObjectMapper();
    }

    @Override
    public List<Entry<Industry, Stock>> apply(Industry industry) {
        return doMap(industry);
    }


    private List<Entry<Industry, Stock>> doMap(Industry industry) {

        String target = "http://xueqiu.com/industry/quote_order.json";
        RequestParaBuilder builder = new RequestParaBuilder(target);
        builder.addParameter("page", 1)
                .addParameter("size", 500)
                .addParameter("order", "desc")
                .addParameter("orderBy", "percent");
        String info = industry.getIndustryInfo();
        if(info.startsWith("#")) info = info.substring(1);
        for (String s : info.split("&")) {
            String[] keyAndVal = s.split("=");
            builder.addParameter(keyAndVal[0], keyAndVal[1]);
        }
        String json = null;

        try {
            URL url = new URL(builder.build());
            HttpRequestHelper helper = new HttpRequestHelper()
                    .addToHeader("Referer", "http://xueqiu.com/hq");
            long sleepTimeMill = 500;
            while (true) {

                try {
                    json = helper.request(url);
                    break;
                } catch(IOException e) {
                    try {
                        Thread.sleep(sleepTimeMill);
                        sleepTimeMill *= 2;
                        e.printStackTrace();
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
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

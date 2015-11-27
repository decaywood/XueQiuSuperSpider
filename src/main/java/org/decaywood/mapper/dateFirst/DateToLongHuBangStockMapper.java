package org.decaywood.mapper.dateFirst;

import com.fasterxml.jackson.databind.JsonNode;
import org.decaywood.entity.Stock;
import org.decaywood.mapper.AbstractMapper;
import org.decaywood.timeWaitingStrategy.TimeWaitingStrategy;
import org.decaywood.utils.RequestParaBuilder;
import org.decaywood.utils.URLMapper;

import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author: decaywood
 * @date: 2015/11/27 10:48
 */
public class DateToLongHuBangStockMapper extends AbstractMapper<Date, List<Stock>> {

    public DateToLongHuBangStockMapper() {
        this(null);
    }

    public DateToLongHuBangStockMapper(TimeWaitingStrategy strategy) {
        super(strategy);
    }

    @Override
    public List<Stock> mapLogic(Date date) throws Exception {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        int diff = 1 - Calendar.JANUARY;

        String dateParam = new StringBuilder().append(format(calendar.get(Calendar.YEAR)))
                .append(format(calendar.get(Calendar.MONTH) + diff))
                .append(format(calendar.get(Calendar.DATE))).toString();

        String target = URLMapper.LONGHUBANG_JSON.toString();
        RequestParaBuilder builder = new RequestParaBuilder(target)
                .addParameter("date", dateParam);
        URL url = new URL(builder.build());
        String json = request(url);
        JsonNode node = mapper.readTree(json);
        return processNode(node, date);

    }

    private List<Stock> processNode(JsonNode node, Date date) {
        List<Stock> stocks = new ArrayList<>();
        for (JsonNode jsonNode : node.get("list")) {

            String name = jsonNode.get("name").asText();
            String symbol = jsonNode.get("symbol").asText();
            Stock stock = new Stock(name, symbol);
            stock.setStockQueryDate(date);

            stocks.add(stock);

        }
        return stocks;
    }

    private String format(int val) {
        return String.format("%02d", val);
    }

}

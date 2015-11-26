package org.decaywood.collector;

import com.fasterxml.jackson.databind.JsonNode;
import org.decaywood.entity.Cube;
import org.decaywood.timeWaitingStrategy.TimeWaitingStrategy;
import org.decaywood.utils.HttpRequestHelper;
import org.decaywood.utils.RequestParaBuilder;
import org.decaywood.utils.URLMapper;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: decaywood
 * @date: 2015/11/25 21:45.
 */
public class MostProfitableCubeCollector extends  AbstractCollector<List<Cube>> {

    public enum Market {
        CN("cn"),
        US("us"),
        HK("hk");

        private String val;

        Market(String val) {
            this.val = val;
        }

        @Override
        public String toString() {
            return val;
        }
    }

    public enum ORDER_BY {
        DAILY("daily_gain"),
        MONTHLY("monthly_gain"),
        YEARLY("annualized_gain_rate");

        private String val;

        ORDER_BY(String val) {
            this.val = val;
        }

        @Override
        public String toString() {
            return val;
        }
    }

    public static final int CUBE_SIZE_SHRESHOLD = 400;
    private Market market;
    private ORDER_BY order_by;
    private int topK;

    public MostProfitableCubeCollector() {
        this(Market.CN);
    }

    public MostProfitableCubeCollector(Market market) {
        this(market, ORDER_BY.MONTHLY);
    }

    public MostProfitableCubeCollector(Market market, ORDER_BY order_by) {
        this(null, market, order_by, 10);
    }

    public MostProfitableCubeCollector(TimeWaitingStrategy strategy, Market market, ORDER_BY order_by, int topK) {
        super(strategy);

        this.market = market == null ? Market.CN : market;
        this.order_by = order_by == null ? ORDER_BY.MONTHLY : order_by;

        if(topK <= 0) throw new IllegalArgumentException();
        this.topK = Math.min(topK, CUBE_SIZE_SHRESHOLD);

    }

    @Override
    public List<Cube> collectLogic() throws Exception {
        String target = URLMapper.CUBES_RANK_JSON.toString();
        RequestParaBuilder builder = new RequestParaBuilder(target)
                .addParameter("category", 12)
                .addParameter("count", topK)
                .addParameter("market", market.toString())
                .addParameter("profit", order_by.toString());

        URL url = new URL(builder.build());
        String json = new HttpRequestHelper().request(url);
        JsonNode node = mapper.readTree(json);
        return processNode(node);
    }


    private List<Cube> processNode(JsonNode node) {

        JsonNode list = node.get("list");
        List<Cube> cubes = new ArrayList<>();
        for (JsonNode jsonNode : list) {

            String id = jsonNode.get("id").asText();
            String name = jsonNode.get("name").asText();
            String symbol = jsonNode.get("symbol").asText();
            String daily_gain = jsonNode.get("daily_gain").asText();
            String monthly_gain = jsonNode.get("monthly_gain").asText();
            String annualized_gain_rate = jsonNode.get("annualized_gain_rate").asText();
            String total_gain = jsonNode.get("total_gain").asText();
            Cube cube = new Cube(id, name, symbol);
            cube.setDaily_gain(daily_gain);
            cube.setMonthly_gain(monthly_gain);
            cube.setAnnualized_gain_rate(annualized_gain_rate);
            cube.setTotal_gain(total_gain);
            cubes.add(cube);

        }
        return cubes;

    }

    public static void main(String[] args) {
        new MostProfitableCubeCollector().get();
    }


    public Market getMarket() {
        return market;
    }

    public ORDER_BY getOrder_by() {
        return order_by;
    }
}

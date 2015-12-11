package org.decaywood.collector;

import com.fasterxml.jackson.databind.JsonNode;
import org.decaywood.entity.Cube;
import org.decaywood.timeWaitingStrategy.TimeWaitingStrategy;
import org.decaywood.utils.RequestParaBuilder;
import org.decaywood.utils.URLMapper;

import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: decaywood
 * @date: 2015/11/25 21:45.
 */

/**
 * 雪球最赚钱组合（Cube）收集器
 */
public class MostProfitableCubeCollector extends  AbstractCollector<List<Cube>> {

    /**
     * 组合所在股市
     */
    public enum Market {
        CN("cn"),//沪深组合
        US("us"),//美股组合
        HK("hk");//港股组合

        private String val;

        Market(String val) {
            this.val = val;
        }

        @Override
        public String toString() {
            return val;
        }
    }

    /**
     * 收益排序规则
     */
    public enum ORDER_BY {
        DAILY("daily_gain"),//按日收益排序
        MONTHLY("monthly_gain"),//按月收益排序
        YEARLY("annualized_gain_rate");//按年收益

        private String val;

        ORDER_BY(String val) {
            this.val = val;
        }

        @Override
        public String toString() {
            return val;
        }
    }


    public static final int CUBE_SIZE_SHRESHOLD = 400; //topK阈值
    private Market market;
    private ORDER_BY order_by;
    private int topK;

    public MostProfitableCubeCollector() throws RemoteException {
        this(Market.CN);
    }

    public MostProfitableCubeCollector(Market market) throws RemoteException {
        this(market, ORDER_BY.MONTHLY);
    }

    public MostProfitableCubeCollector(Market market, ORDER_BY order_by) throws RemoteException {
        this(null, market, order_by, 10);
    }


    /**
     *
     * @param strategy 超时等待策略（null则设置为默认等待策略）
     * @param market 组合所在市场
     * @param order_by 收益排序规则
     * @param topK 排名前K的组合
     */
    public MostProfitableCubeCollector(TimeWaitingStrategy strategy, Market market, ORDER_BY order_by, int topK) throws RemoteException {
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
        String json = request(url);
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

    public Market getMarket() {
        return market;
    }

    public ORDER_BY getOrder_by() {
        return order_by;
    }
}

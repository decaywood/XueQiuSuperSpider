package org.decaywood.mapper.cubeFirst;

import com.fasterxml.jackson.databind.JsonNode;
import org.decaywood.entity.Cube;
import org.decaywood.entity.trend.Rebalancing;
import org.decaywood.mapper.AbstractMapper;
import org.decaywood.timeWaitingStrategy.TimeWaitingStrategy;
import org.decaywood.utils.EmptyObject;
import org.decaywood.utils.RequestParaBuilder;
import org.decaywood.utils.URLMapper;

import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: decaywood
 * @date: 2015/11/26 15:56
 */


/**
 * 股票组合最近持仓装配器
 */
public class CubeToCubeWithLastBalancingMapper extends AbstractMapper<Cube, Cube> {



    private static final int COUNT_THRESHOLD = 50;

    private final int count;

    public CubeToCubeWithLastBalancingMapper() throws RemoteException {
        this(null, 10);
    }

    public CubeToCubeWithLastBalancingMapper(int i) throws RemoteException {
        this(null, i);
    }

    /**
     *
     * @param strategy 超时等待策略（null则设置为默认等待策略）
     * @param count 调仓记录数
     */
    public CubeToCubeWithLastBalancingMapper(TimeWaitingStrategy strategy, int count) throws RemoteException {
        super(strategy);
        if(count <= 0) throw new IllegalArgumentException();
        this.count = Math.min(COUNT_THRESHOLD, count);
    }

    @Override
    public Cube mapLogic(Cube cube) throws Exception {

        if(cube == null || cube == EmptyObject.emptyCube) return EmptyObject.emptyCube;

        String target = URLMapper.CUBE_REBALANCING_JSON.toString();
        RequestParaBuilder builder = new RequestParaBuilder(target)
                .addParameter("cube_symbol", cube.getSymbol())
                .addParameter("count", count)
                .addParameter("page", 1);
        URL url = new URL(builder.build());
        String json = request(url);

        JsonNode node = mapper.readTree(json);
        processCube(cube, node);
        return cube;

    }

    private void processCube(Cube cube, JsonNode node) {
        JsonNode list = node.get("list");

        List<Rebalancing.TrendBlock> history = new ArrayList<>();
        for (JsonNode jsonNode : list) {
            for (JsonNode jn : jsonNode.get("rebalancing_histories")) {
                String stock_name = jn.get("stock_name").asText();
                String stock_symbol = jn.get("stock_symbol").asText();
                String created_at = jn.get("created_at").asText();
                String prev_price = jn.get("prev_price").asText();
                String price = jn.get("price").asText();
                String prev_weight = jn.get("prev_weight").asText();
                String target_weight = jn.get("target_weight").asText();
                String weight = jn.get("weight").asText();
                String rebalancing_id = jn.get("rebalancing_id").asText();
                Rebalancing.TrendBlock trendBlock = new Rebalancing.TrendBlock(
                        stock_name,
                        stock_symbol,
                        created_at,
                        prev_price,
                        price,
                        prev_weight,
                        target_weight,
                        weight,
                        rebalancing_id);
                history.add(trendBlock);
            }

        }
        Rebalancing rebalancing = new Rebalancing(history);
        cube.setRebalancing(rebalancing);
    }
}

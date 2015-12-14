package org.decaywood.mapper.cubeFirst;

import com.fasterxml.jackson.databind.JsonNode;
import org.decaywood.entity.Cube;
import org.decaywood.entity.trend.CubeTrend;
import org.decaywood.entity.trend.MarketIndexTrend;
import org.decaywood.mapper.AbstractMapper;
import org.decaywood.timeWaitingStrategy.TimeWaitingStrategy;
import org.decaywood.utils.EmptyObject;
import org.decaywood.utils.RequestParaBuilder;
import org.decaywood.utils.URLMapper;

import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author: decaywood
 * @date: 2015/11/26 9:48
 */

/**
 * 股票组合历史走势装配器
 */
public class CubeToCubeWithTrendMapper extends AbstractMapper<Cube, Cube> {

    private final long since;
    private final long until;

    public CubeToCubeWithTrendMapper(Date since, Date until) throws RemoteException {
        this(null, since, until);
    }


    /**
     *
     * @param strategy 超时等待策略（null则设置为默认等待策略）
     * @param since 走线计算起始时间
     * @param until 走线计算结束时间
     */
    public CubeToCubeWithTrendMapper(
            TimeWaitingStrategy strategy,
            Date since,
            Date until) throws RemoteException {

        super(strategy);
        if(since == null || until == null || since.after(until))
            throw new IllegalArgumentException("Null Pointer");

        this.since = since.getTime();
        this.until = until.getTime();

    }


    @Override
    public Cube mapLogic(Cube cube) throws Exception {
        if(cube == null || cube == EmptyObject.emptyCube) return EmptyObject.emptyCube;

        String target = URLMapper.CUBE_TREND_JSON.toString();
        RequestParaBuilder builder = new RequestParaBuilder(target)
                .addParameter("cube_symbol", cube.getSymbol())
                .addParameter("since", since)
                .addParameter("until", until);

        URL url = new URL(builder.build());

        String json = request(url);

        JsonNode node = mapper.readTree(json);
        processCube(cube, node);
        return cube;

    }

    private void processCube(Cube cube, JsonNode node) {

        JsonNode cubeNode = node.get(0);
        JsonNode SH300Node = node.get(1);

        CubeTrend cubeTrend = processCubeNode(cubeNode);
        MarketIndexTrend marketIndexTrend = processSH00Node(SH300Node);
        cube.setCubeTrend(cubeTrend);
        cube.setMarketIndexTrend(marketIndexTrend);

    }

    private CubeTrend processCubeNode(JsonNode node) {

        JsonNode trendNode = node.get("list");
        List<CubeTrend.TrendBlock> blocks = new ArrayList<>();

        for (JsonNode jsonNode : trendNode) {
            String time = jsonNode.get("time").asText();
            String date = jsonNode.get("date").asText();
            String value = jsonNode.get("value").asText();
            String percent = jsonNode.get("percent").asText();
            CubeTrend.TrendBlock trendBlock = new CubeTrend.TrendBlock(
                    time,
                    date,
                    value,
                    percent);
            blocks.add(trendBlock);
        }

        if(blocks.isEmpty()) return EmptyObject.emptyCubeTrend;

        return new CubeTrend(
                node.get("symbol").asText(),
                node.get("name").asText(),
                blocks.get(0).getTime(),
                blocks.get(blocks.size() - 1).getTime(),
                blocks);

    }

    private MarketIndexTrend processSH00Node(JsonNode node) {

        JsonNode trendNode = node.get("list");
        List<MarketIndexTrend.TrendBlock> blocks = new ArrayList<>();

        for (JsonNode jsonNode : trendNode) {
            String time = jsonNode.get("time").asText();
            String date = jsonNode.get("date").asText();
            String value = jsonNode.get("value").asText();
            String percent = jsonNode.get("percent").asText();
            MarketIndexTrend.TrendBlock trendBlock = new MarketIndexTrend.TrendBlock(
                    time,
                    date,
                    value,
                    percent);
            blocks.add(trendBlock);
        }

        if(blocks.isEmpty()) return EmptyObject.emptyMarketIndexTrend;

        return new MarketIndexTrend(
                node.get("symbol").asText(),
                node.get("name").asText(),
                blocks.get(0).getTime(),
                blocks.get(blocks.size() - 1).getTime(),
                blocks);
    }



}

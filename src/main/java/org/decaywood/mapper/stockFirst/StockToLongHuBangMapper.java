package org.decaywood.mapper.stockFirst;

import com.fasterxml.jackson.databind.JsonNode;
import org.decaywood.entity.LongHuBangInfo;
import org.decaywood.entity.Stock;
import org.decaywood.mapper.AbstractMapper;
import org.decaywood.timeWaitingStrategy.TimeWaitingStrategy;
import org.decaywood.utils.DateParser;
import org.decaywood.utils.EmptyObject;
import org.decaywood.utils.RequestParaBuilder;
import org.decaywood.utils.URLMapper;

import java.net.URL;
import java.rmi.RemoteException;
import java.util.*;

/**
 * @author: decaywood
 * @date: 2015/11/27 14:40
 */

/**
 * 股票 -> 龙虎榜数据 映射器
 */
public class StockToLongHuBangMapper extends AbstractMapper <Stock, LongHuBangInfo> {


    public StockToLongHuBangMapper() throws RemoteException {
        this(null);
    }


    /**
     * @param strategy 超时等待策略（null则设置为默认等待策略）
     */
    public StockToLongHuBangMapper(TimeWaitingStrategy strategy) throws RemoteException {
        super(strategy);
    }

    @Override
    public LongHuBangInfo mapLogic(Stock stock) throws Exception {

        if(stock == null || stock == EmptyObject.emptyStock) return EmptyObject.emptyLongHuBangInfo;

        Date date = stock.getStockQueryDate();

        if (date == EmptyObject.emptyDate)
            throw new IllegalArgumentException("lost parameter: stockQueryDate");

        String dateParam = DateParser.getTimePrefix(date, false);

        String target = URLMapper.LONGHUBANG_JSON.toString();
        RequestParaBuilder builder = new RequestParaBuilder(target)
                .addParameter("date", dateParam)
                .addParameter("symbol", stock.getStockNo());
        URL url = new URL(builder.build());

        String json = request(url);
        JsonNode node = mapper.readTree(json);
        return processNode(stock, node);

    }

    private LongHuBangInfo processNode(Stock stock, JsonNode node) {

        JsonNode detail = node.get("detail");
        JsonNode buyListNode = detail.get("tqQtBizunittrdinfoBuyList");
        JsonNode saleListNode = detail.get("tqQtBizunittrdinfoSaleList");

        Set<LongHuBangInfo.BizsunitInfo> buyList = new HashSet<>();
        Set<LongHuBangInfo.BizsunitInfo> saleList = new HashSet<>();

        for (JsonNode jsonNode : buyListNode) {
            LongHuBangInfo.BizsunitInfo info = composeInfo(jsonNode);
            buyList.add(info);
        }

        for (JsonNode jsonNode : saleListNode) {
            LongHuBangInfo.BizsunitInfo info = composeInfo(jsonNode);
            saleList.add(info);
        }

        return new LongHuBangInfo(stock, stock.getStockQueryDate(), buyList, saleList);
    }

    private LongHuBangInfo.BizsunitInfo composeInfo(JsonNode jsonNode) {

        String bizsunitcode = jsonNode.get("bizsunitcode").asText();
        String bizsunitname = jsonNode.get("bizsunitname").asText();
        String buyamt = jsonNode.get("buyamt").asText();
        String saleamt = jsonNode.get("saleamt").asText();
        String tradedate = jsonNode.get("tradedate").asText();

        return new LongHuBangInfo.BizsunitInfo(bizsunitcode, bizsunitname, buyamt, saleamt, tradedate);
    }


}

package org.decaywood.mapper.stockFirst;

import com.fasterxml.jackson.databind.JsonNode;
import org.decaywood.entity.Stock;
import org.decaywood.mapper.AbstractMapper;
import org.decaywood.timeWaitingStrategy.TimeWaitingStrategy;
import org.decaywood.utils.EmptyObject;
import org.decaywood.utils.RequestParaBuilder;
import org.decaywood.utils.URLMapper;

import java.net.URL;
import java.rmi.RemoteException;

/**
 * @author: decaywood
 * @date: 2015/11/23 21:25
 */

/**
 * 股票属性装配器
 */
public class StockToStockWithAttributeMapper extends AbstractMapper<Stock, Stock> {


    /**
     * @param strategy 超时等待策略（null则设置为默认等待策略）
     */
    public StockToStockWithAttributeMapper(TimeWaitingStrategy strategy) throws RemoteException {
        super(strategy);
    }

    public StockToStockWithAttributeMapper() throws RemoteException {
        this(null);
    }


    @Override
    public Stock mapLogic(Stock stock) throws Exception {

        if(stock == null || stock == EmptyObject.emptyStock) return EmptyObject.emptyStock;

        String target = URLMapper.STOCK_JSON.toString();
        RequestParaBuilder builder = new RequestParaBuilder(target)
                .addParameter("code", stock.getStockNo());
        URL url = new URL(builder.build());
        String json = request(url);
        JsonNode node = mapper.readTree(json);
        node = node.get(stock.getStockNo());

        stock.setTime(node.get("time").asText());
        stock.setCurrency_unit(node.get("currency_unit").asText());
        stock.setCurrent(node.get("current").asText());
        stock.setVolume(node.get("volume").asText());
        stock.setPercentage(node.get("percentage").asText());
        stock.setChange(node.get("change").asText());
        stock.setOpen(node.get("open").asText());
        stock.setHigh(node.get("high").asText());
        stock.setLow(node.get("low").asText());
        stock.setAmplitude(node.get("amplitude").asText());
        stock.setFall_stop(node.get("fall_stop").asText());
        stock.setRise_stop(node.get("rise_stop").asText());
        stock.setClose(node.get("close").asText());
        stock.setLast_close(node.get("last_close").asText());
        stock.setHigh52Week(node.get("high52week").asText());
        stock.setLow52week(node.get("low52week").asText());
        stock.setMarketCapital(node.get("marketCapital").asText());
        stock.setFloat_market_capital(node.get("float_market_capital").asText());
        stock.setFloat_shares(node.get("float_shares").asText());
        stock.setTotalShares(node.get("totalShares").asText());
        stock.setEps(node.get("eps").asText());
        stock.setNet_assets(node.get("net_assets").asText());
        stock.setPe_ttm(node.get("pe_ttm").asText());
        stock.setPe_lyr(node.get("pe_lyr").asText());
        stock.setDividend(node.get("dividend").asText());
        stock.setPsr(node.get("psr").asText());
        stock.setTurnover_rate(node.get("turnover_rate").asText());
        stock.setAmount(node.get("amount").asText());
        return stock;

    }




}

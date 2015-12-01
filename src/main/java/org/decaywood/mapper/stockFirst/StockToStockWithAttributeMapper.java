package org.decaywood.mapper.stockFirst;

import com.fasterxml.jackson.databind.JsonNode;
import org.decaywood.entity.Stock;
import org.decaywood.mapper.AbstractMapper;
import org.decaywood.timeWaitingStrategy.TimeWaitingStrategy;
import org.decaywood.utils.EmptyObject;
import org.decaywood.utils.RequestParaBuilder;
import org.decaywood.utils.URLMapper;

import java.net.URL;

/**
 * @author: decaywood
 * @date: 2015/11/23 21:25
 */
public class StockToStockWithAttributeMapper extends AbstractMapper<Stock, Stock> {


    public StockToStockWithAttributeMapper(TimeWaitingStrategy strategy) {
        super(strategy);
    }

    public StockToStockWithAttributeMapper() {
        this(null);
    }


    @Override
    public Stock mapLogic(Stock stock) throws Exception {

        if(stock == null || stock == EmptyObject.emptyStock) return EmptyObject.emptyStock;
        Stock copyStock = stock.copy();

        String target = URLMapper.STOCK_JSON.toString();
        RequestParaBuilder builder = new RequestParaBuilder(target)
                .addParameter("code", copyStock.getStockNo());
        URL url = new URL(builder.build());
        String json = request(url);
        JsonNode node = mapper.readTree(json);
        node = node.get(copyStock.getStockNo());

        copyStock.setTime(node.get("time").asText());
        copyStock.setCurrency_unit(node.get("currency_unit").asText());
        copyStock.setCurrent(node.get("current").asText());
        copyStock.setVolume(node.get("volume").asText());
        copyStock.setPercentage(node.get("percentage").asText());
        copyStock.setChange(node.get("change").asText());
        copyStock.setOpen(node.get("open").asText());
        copyStock.setHigh(node.get("high").asText());
        copyStock.setLow(node.get("low").asText());
        copyStock.setAmplitude(node.get("amplitude").asText());
        copyStock.setFall_stop(node.get("fall_stop").asText());
        copyStock.setRise_stop(node.get("rise_stop").asText());
        copyStock.setClose(node.get("close").asText());
        copyStock.setLast_close(node.get("last_close").asText());
        copyStock.setHigh52Week(node.get("high52week").asText());
        copyStock.setLow52week(node.get("low52week").asText());
        copyStock.setMarketCapital(node.get("marketCapital").asText());
        copyStock.setFloat_market_capital(node.get("float_market_capital").asText());
        copyStock.setFloat_shares(node.get("float_shares").asText());
        copyStock.setTotalShares(node.get("totalShares").asText());
        copyStock.setEps(node.get("eps").asText());
        copyStock.setNet_assets(node.get("net_assets").asText());
        copyStock.setPe_ttm(node.get("pe_ttm").asText());
        copyStock.setPe_lyr(node.get("pe_lyr").asText());
        copyStock.setDividend(node.get("dividend").asText());
        copyStock.setPsr(node.get("psr").asText());
        copyStock.setTurnover_rate(node.get("turnover_rate").asText());
        copyStock.setAmount(node.get("amount").asText());
        return copyStock;

    }




}

package mapper;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import entity.Stock;
import utils.HttpRequestHelper;
import utils.RequestParaBuilder;

import java.net.URL;
import java.util.function.Function;

/**
 * @author: decaywood
 * @date: 2015/11/23 21:25
 */
public class StockToStockWithAttributeMapper implements Function<Stock, Stock> {


    private ObjectMapper mapper;

    public StockToStockWithAttributeMapper() {
        this.mapper = new ObjectMapper();
    }

    @Override
    public Stock apply(Stock stock) {
        stock = stock.deepCopy();
        String target = "http://xueqiu.com/v4/stock/quote.json";
        RequestParaBuilder builder = new RequestParaBuilder(target)
                .addParameter("code", stock.getStockNo());
        try {
            URL url = new URL(builder.build());
            String json = new HttpRequestHelper()
                    .addToHeader("Referer", "http://xueqiu.com/S/SZ002137")
                    .request(url);
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

        } catch (Exception e) {
            e.printStackTrace();
        }

        return stock;
    }


}

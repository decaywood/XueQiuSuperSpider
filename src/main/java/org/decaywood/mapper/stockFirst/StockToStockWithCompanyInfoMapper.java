package org.decaywood.mapper.stockFirst;

import com.fasterxml.jackson.databind.JsonNode;
import org.decaywood.entity.CompanyInfo;
import org.decaywood.entity.Industry;
import org.decaywood.entity.Stock;
import org.decaywood.mapper.AbstractMapper;
import org.decaywood.timeWaitingStrategy.TimeWaitingStrategy;
import org.decaywood.utils.EmptyObject;
import org.decaywood.utils.RequestParaBuilder;
import org.decaywood.utils.URLMapper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: decaywood
 * @date: 2015/11/30 16:13
 */

/**
 * 股票公司信息装配器
 */
public class StockToStockWithCompanyInfoMapper extends AbstractMapper <Stock, Stock> {

    private Map<String, Industry> industryMap;
    private volatile boolean initializing;

    public StockToStockWithCompanyInfoMapper() throws RemoteException {
        this(null);
    }

    /**
     * @param strategy 超时等待策略（null则设置为默认等待策略）
     */
    public StockToStockWithCompanyInfoMapper(TimeWaitingStrategy strategy) throws RemoteException {
        super(strategy);
    }

    private void initMap() throws Exception {

        industryMap = new HashMap<>();

        String target = URLMapper.COMPREHENSIVE_PAGE.toString();

        String content = request(new URL(target));
        Document doc = Jsoup.parse(content);
        Elements element = doc.getElementsByClass("second-nav")
                .get(1).children()
                .get(3).children()
                .get(3).children()
                .select("a");
        StringBuilder builder = new StringBuilder();
        for (Element ele : element) {
            if (!ele.hasAttr("title") || !ele.hasAttr("href")) continue;
            builder.append(ele.attr("href"));
            industryMap.put(ele.attr("title"),  new Industry(ele.attr("title"), builder.toString()));
            builder.delete(0, builder.length());
        }
    }

    @Override
    public Stock mapLogic(Stock stock) throws Exception {

        if(stock == null || stock == EmptyObject.emptyStock) return EmptyObject.emptyStock;

        if(industryMap == null && !initializing) {
            initializing = true;
            initMap();
        } else while (industryMap == null) {
            Thread.sleep(50);
        }

        String target = URLMapper.STOCK_INDUSTRY_JSON.toString();
        RequestParaBuilder builder = new RequestParaBuilder(target)
                .addParameter("symbol", stock.getStockNo());

        URL url = new URL(builder.build());
        String json = request(url);

        JsonNode node = mapper.readTree(json).get("tqCompInfo");
        processStock(stock, node);
        return stock;

    }

    private void processStock(Stock stock, JsonNode node) {
        String compsname = node.get("compsname").asText();
        String orgtype = node.get("orgtype").asText();
        String founddate = node.get("founddate").asText();
        String bizscope = node.get("bizscope").asText();
        String majorbiz = node.get("majorbiz").asText();
        String region = node.get("region").asText();

        List<Industry> industries = new ArrayList<>();
        JsonNode subNode = node.get("tqCompIndustryList");

        for (JsonNode jsonNode : subNode) {
            String industryName = jsonNode.get("level2name").asText();
            industries.add(industryMap.get(industryName).copy());
        }

        CompanyInfo companyInfo = new CompanyInfo(
                compsname, orgtype, founddate, bizscope, majorbiz, region, industries);

        stock.setCompanyInfo(companyInfo);

    }

}

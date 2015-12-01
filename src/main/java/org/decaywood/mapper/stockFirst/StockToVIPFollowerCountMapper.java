package org.decaywood.mapper.stockFirst;

import com.fasterxml.jackson.databind.JsonNode;
import org.decaywood.entity.Stock;
import org.decaywood.mapper.AbstractMapper;
import org.decaywood.timeWaitingStrategy.TimeWaitingStrategy;
import org.decaywood.utils.EmptyObject;
import org.decaywood.utils.URLMapper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.net.URL;

/**
 * @author: decaywood
 * @date: 2015/11/30 16:39
 */

//速度很慢，慎用
public class StockToVIPFollowerCountMapper extends AbstractMapper <Stock, Integer> {

    private static final String REQUEST_PREFIX = URLMapper.MAIN_PAGE + "/S/";
    private static final String REQUEST_SUFFIX = "/follows?page=";


    private int VIPFriendsCountShreshold;
    private int latestK_NewFollowers;

    public StockToVIPFollowerCountMapper() {
        this(10000, 20);
    }

    public StockToVIPFollowerCountMapper(int VIPFriendsCountShreshold, int latestK_NewFollowers) {
        this(null, VIPFriendsCountShreshold, latestK_NewFollowers);
    }

    public StockToVIPFollowerCountMapper(TimeWaitingStrategy strategy,
                                         int VIPFriendsCountShreshold,
                                         int latestK_NewFollowers) {
        super(strategy);
        if(VIPFriendsCountShreshold < 0 || latestK_NewFollowers < 0) throw new IllegalArgumentException();
        this.VIPFriendsCountShreshold = VIPFriendsCountShreshold;
        this.latestK_NewFollowers = latestK_NewFollowers;
    }

    @Override
    public Integer mapLogic(Stock stock) throws Exception {

        if(stock == null || stock == EmptyObject.emptyStock) return 0;
        Stock copyStock = stock.copy();

        String stockNo = copyStock.getStockNo();

        int count = 0;

        for (int i = 1; i < latestK_NewFollowers; i++) {

            String reqUrl = REQUEST_PREFIX + stockNo + REQUEST_SUFFIX + i;
            URL url = new URL(reqUrl);

            String content;
            while (true) {
                try {

                    content = request(url);
                    break;

                } catch (Exception e) {
                    System.out.println("Mapper: Network busy Retrying");
                }
            }

            JsonNode node = parseHtmlToJsonNode(content).get("followers");

            if(node.size() == 0) break;

            for (JsonNode jsonNode : node) {
                int followersCount = jsonNode.get("followers_count").asInt();
                if(followersCount > VIPFriendsCountShreshold) count++;
            }

        }

        return count;
    }


    private JsonNode parseHtmlToJsonNode(String content) throws IOException {

        Document doc = Jsoup.parse(content);
        String indexer1 = "follows=";
        String indexer2 = ";seajs.use";
        StringBuilder builder = new StringBuilder(
                doc.getElementsByTag("script")
                .get(15)
                .dataNodes()
                .get(0)
                .attr("data"));
        int index = builder.indexOf(indexer1);
        builder.delete(0, index + indexer1.length());
        index = builder.indexOf(indexer2);
        builder.delete(index, builder.length());
        return mapper.readTree(builder.toString());

    }
}

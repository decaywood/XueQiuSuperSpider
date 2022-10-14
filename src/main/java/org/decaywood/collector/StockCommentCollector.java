package org.decaywood.collector;

import com.fasterxml.jackson.databind.JsonNode;
import org.decaywood.entity.PostInfo;
import org.decaywood.utils.JsonParser;
import org.decaywood.utils.RequestParaBuilder;
import org.decaywood.utils.StringUtils;
import org.decaywood.utils.URLMapper;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * 某只标的下的用户评论收集器
 * @author decaywood
 * @date 2020/10/7 16:04
 */
public class StockCommentCollector extends AbstractCollector<List<PostInfo>> {

    public enum SortType {
        // 最新时间
        time,
        // 热度排名
        alpha
    }

    private String symbol;

    private int pageSize;

    private int page;

    private String sort;

    public StockCommentCollector(String symbol, SortType type, int page, int pageSize) {
        super(null);
        this.symbol = symbol;
        this.page = page;
        this.pageSize = pageSize;
        this.sort = type.toString();
    }

    @Override
    protected List<PostInfo> collectLogic() throws Exception {
        if (StringUtils.isNull(symbol) || page < 1 || pageSize <= 0) {
            throw new IllegalArgumentException("symbol is null or page size is zero");
        }
        String target = URLMapper.STOCK_MAIN_PAGE.toString();
        RequestParaBuilder builder = new RequestParaBuilder(target);
        builder.addParameter("count", pageSize);
        builder.addParameter("symbol", symbol);
        builder.addParameter("sort", sort);
        builder.addParameter("page", page);
        URL url = new URL(builder.build());
        String json = requestWithoutGzip(url);
        JsonNode node = mapper.readTree(json);
        List<PostInfo> postInfos = new ArrayList<>();
        for (JsonNode jsonNode : node.get("list")) {
            PostInfo postInfo = JsonParser.parse(PostInfo::new, jsonNode);
            postInfos.add(postInfo);
        }
        return postInfos;
    }

}

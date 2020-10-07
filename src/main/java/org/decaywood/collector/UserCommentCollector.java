package org.decaywood.collector;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.decaywood.entity.Comment;
import org.decaywood.utils.JsonParser;
import org.decaywood.utils.RequestParaBuilder;
import org.decaywood.utils.StringUtils;
import org.decaywood.utils.URLMapper;
import org.springframework.beans.BeanUtils;

import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

/**
 * 某只标的下的用户评论收集器
 * @author decaywood (zyx@webull.com)
 * @date 2020/10/7 16:04
 */
public class UserCommentCollector extends AbstractCollector<List<Comment>> {

    private String symbol;

    private int pageSize;

    public UserCommentCollector() {
        super(null);
    }

    @Override
    protected List<Comment> collectLogic() throws Exception {
        if (StringUtils.isNull(symbol) || pageSize <= 0) {
            throw new IllegalArgumentException("symbol is null or page size is zero");
        }
        String target = URLMapper.STOCK_MAIN_PAGE.toString();
        RequestParaBuilder builder = new RequestParaBuilder(target);
        builder.addParameter("count", pageSize);
        builder.addParameter("symbol", symbol);
        URL url = new URL(builder.build());
        String json = requestWithoutGzip(url);
        JsonNode node = mapper.readTree(json);
        List<Comment> comments = new ArrayList<>();
        for (JsonNode jsonNode : node.get("list")) {
            Comment comment = JsonParser.parse(Comment::new, jsonNode);
            comments.add(comment);
        }
        return comments;
    }

    /**
     * 设置股票代码
     * @param stockCode 例如君实生物 SH688180
     * @return
     */
    public UserCommentCollector setSymbol(String stockCode) {
        this.symbol = stockCode;
        return this;
    }

    public UserCommentCollector setPageSize(int pageSize) {
        this.pageSize = pageSize;
        return this;
    }
}

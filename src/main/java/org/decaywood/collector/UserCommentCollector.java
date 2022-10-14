package org.decaywood.collector;

import com.fasterxml.jackson.databind.JsonNode;
import org.decaywood.entity.PostInfo;
import org.decaywood.utils.JsonParser;
import org.decaywood.utils.RequestParaBuilder;
import org.decaywood.utils.URLMapper;
import org.springframework.util.StringUtils;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author decaywood
 * @date 2020/10/9 12:39
 */
public class UserCommentCollector extends AbstractCollector<List<PostInfo>>  {

    private String userId;

    /** 评论页码 */
    private int fromPage;
    /** 评论页码 */
    private int toPage;

    private int pageSize;

    public UserCommentCollector(String userId, int fromPage, int toPage, int pageSize) {
        super(null);
        this.userId = userId;
        this.fromPage = fromPage;
        this.toPage = toPage;
        this.pageSize = pageSize;
    }

    @Override
    protected List<PostInfo> collectLogic() throws Exception {
        if (StringUtils.isEmpty(userId) || fromPage < 1 || fromPage > toPage) {
            throw new IllegalArgumentException("userId is null or page size illegal");
        }
        String target = URLMapper.USER_COMMENT_JSON.toString();
        List<PostInfo> postInfos = new ArrayList<>();
        for (int i = fromPage; i <= toPage; i++) {
            RequestParaBuilder builder = new RequestParaBuilder(target);
            builder.addParameter("page", i);
            builder.addParameter("user_id", userId);
            builder.addParameter("count", pageSize);
            URL url = new URL(builder.build());
            String json = request(url);
            JsonNode node = mapper.readTree(json);
            toPage = Math.min(toPage, Integer.parseInt(node.get("maxPage").asText()));
            for (JsonNode jsonNode : node.get("statuses")) {
                PostInfo postInfo = JsonParser.parse(PostInfo::new, jsonNode);
                postInfos.add(postInfo);
            }
        }
        return postInfos;
    }

}

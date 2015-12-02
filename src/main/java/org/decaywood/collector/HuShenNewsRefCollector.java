package org.decaywood.collector;

import com.fasterxml.jackson.databind.JsonNode;
import org.decaywood.timeWaitingStrategy.TimeWaitingStrategy;
import org.decaywood.utils.RequestParaBuilder;
import org.decaywood.utils.URLMapper;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: decaywood
 * @date: 2015/12/2 21:37.
 */
public class HuShenNewsRefCollector extends AbstractCollector<List<String>> {

    private static final int MAX_PAGE_SIZE = 5;

    private int topicType = 5;
    private int pageEndTo = 1;

    public HuShenNewsRefCollector() {
        this(5, 1);
    }

    public HuShenNewsRefCollector(int topicType, int pageEndTo) {
        this(null, topicType, pageEndTo);
    }

    public HuShenNewsRefCollector(TimeWaitingStrategy strategy, int topicType, int pageEndTo) {
        super(strategy);
        if(topicType > 8 || topicType < 5 || pageEndTo < 1)
            throw new IllegalArgumentException();

        this.pageEndTo = Math.min(pageEndTo, MAX_PAGE_SIZE);
        this.topicType = topicType;

    }

    @Override
    public List<String> collectLogic() throws Exception {

        String target = URLMapper.HU_SHEN_NEWS_REF_JSON.toString();

        RequestParaBuilder builder = new RequestParaBuilder(target)
                .addParameter("simple_user", "1")
                .addParameter("topicType", topicType)
                .addParameter("page", pageEndTo);

        URL url = new URL(builder.build());
        String json = request(url);
        JsonNode node = mapper.readTree(json);
        return processNode(node);
    }

    private List<String> processNode(JsonNode node) {

        List<String> res = new ArrayList<>();
        for (JsonNode jsonNode : node) {
            String suffix = jsonNode.get("target").asText();
            String path = URLMapper.MAIN_PAGE.toString() + suffix;
            res.add(path);
        }
        return res;

    }
}

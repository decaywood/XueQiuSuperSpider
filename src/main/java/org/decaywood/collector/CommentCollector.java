package org.decaywood.collector;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.NullNode;
import org.decaywood.entity.Comment;
import org.decaywood.entity.User;
import org.decaywood.timeWaitingStrategy.TimeWaitingStrategy;
import org.decaywood.utils.JsonParser;
import org.decaywood.utils.RequestParaBuilder;
import org.decaywood.utils.URLMapper;
import org.springframework.util.CollectionUtils;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * @author decaywood (zyx@webull.com)
 * @date 2020/11/7 12:59
 */
public class CommentCollector extends AbstractCollector<List<Comment>> {

    private String postId;

    public CommentCollector(String postId) {
        super(null);
        this.postId = postId;
    }

    @Override
    protected List<Comment> collectLogic() throws Exception {
        String target = URLMapper.COMMENTS_INFO_JSON.toString();
        int page = 1;
        List<Comment> res = new ArrayList<>();
        while (true) {
            int cnt = 20;
            RequestParaBuilder builder = new RequestParaBuilder(target)
                    .addParameter("id", postId)
                    .addParameter("count", cnt)
                    .addParameter("page", page)
                    .addParameter("reply", true)
                    .addParameter("split", true);
            URL url = new URL(builder.build());
            String json = request(url);
            JsonNode node = mapper.readTree(json);
            List<Comment> comments = JsonParser.parseArray(Comment::new, (t, n) -> {
                User user = JsonParser.parse(User::new, n.get("user"));
                JsonNode replyComment = n.get("reply_comment");
                if (!(replyComment instanceof NullNode)) {
                    Comment reply_comment = JsonParser.parse(Comment::new, replyComment);
                    User replyUser = JsonParser.parse(User::new, replyComment.get("user"));
                    reply_comment.setUser(replyUser);
                    t.setReply_comment(reply_comment);
                }
                t.setUser(user);
            }, node.get("comments"));
            if (CollectionUtils.isEmpty(comments)) {
                break;
            }
            res.addAll(comments);
            page++;
        }
        return res;
    }
}

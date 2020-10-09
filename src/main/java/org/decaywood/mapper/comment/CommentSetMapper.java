package org.decaywood.mapper.comment;

import com.fasterxml.jackson.databind.JsonNode;
import org.decaywood.collector.StockCommentCollector;
import org.decaywood.entity.Comment;
import org.decaywood.entity.PostInfo;
import org.decaywood.entity.User;
import org.decaywood.mapper.AbstractMapper;
import org.decaywood.utils.JsonParser;
import org.decaywood.utils.RequestParaBuilder;
import org.decaywood.utils.URLMapper;

import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author decaywood (zyx@webull.com)
 * @date 2020/10/7 19:18
 */
public class CommentSetMapper <T extends Comment.CommentSetter> extends AbstractMapper<T, T> {

    private int pageSize;

    public CommentSetMapper() {
        this(10);
    }

    public CommentSetMapper(int pageSize) {
        super(null);
        this.pageSize = pageSize;
    }

    @Override
    protected T mapLogic(T commentSetter) throws Exception {
        String postId = commentSetter.getPostId();
        String target = URLMapper.COMMENTS_INFO_JSON.toString();
        int replyCnt = commentSetter.getReplyCnt();
        int page = 1;
        while (replyCnt > 0) {
            int cnt = Math.min(replyCnt, pageSize);
            RequestParaBuilder builder = new RequestParaBuilder(target)
                    .addParameter("id", postId)
                    .addParameter("count", cnt)
                    .addParameter("page", page);
            URL url = new URL(builder.build());
            String json = request(url);
            JsonNode node = mapper.readTree(json);
            List<Comment> comments = JsonParser.parseArray(Comment::new, (t, n) -> {
                User user = JsonParser.parse(User::new, n.get("user"));
                t.setUser(user);
            }, node.get("comments"));
            commentSetter.addComments(comments);
            page++;
            replyCnt -= cnt;
        }
        return commentSetter;
    }

}

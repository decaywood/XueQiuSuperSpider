package collectTest;

import org.decaywood.collector.StockCommentCollector;
import org.decaywood.entity.Comment;
import org.decaywood.entity.PostInfo;
import org.decaywood.mapper.comment.CommentSetMapper;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author decaywood (zyx@webull.com)
 * @date 2020/10/7 22:05
 */
public class StockCommentCollectorTest {

    @Test
    public void test() {
        List<PostInfo> infos = new StockCommentCollector("SH688180", StockCommentCollector.SortType.alpha, 1, 1).get();
        Assert.assertEquals(1, infos.size());
    }
}

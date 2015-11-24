package collector;

import entity.Industry;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import utils.HttpRequestHelper;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: decaywood
 * @date: 2015/11/23 10:50
 */
public class CommissionIndustryCollector extends AbstractCollector<List<Industry>> {


    @Override
    public List<Industry> collectInternal() throws Exception {
        String target = "http://xueqiu.com/hq";
        List<Industry> res = new ArrayList<>();
        HttpRequestHelper requestHelper = new HttpRequestHelper();
        String content = requestHelper.request(new URL(target));
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
            res.add(new Industry(ele.attr("title"), builder.toString()));
            builder.delete(0, builder.length());
        }
        return res;
    }


}

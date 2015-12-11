package org.decaywood.collector;

import org.decaywood.entity.Industry;
import org.decaywood.timeWaitingStrategy.TimeWaitingStrategy;
import org.decaywood.utils.URLMapper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: decaywood
 * @date: 2015/11/23 10:50
 */

/**
 * 板块收集器，收集证监会所有板块类型编号以及名称
 */
public class CommissionIndustryCollector extends AbstractCollector<List<Industry>> {

    public CommissionIndustryCollector() throws RemoteException {
        this(null);
    }

    /**
     *@param strategy 超时等待策略（null则设置为默认等待策略）
     */
    public CommissionIndustryCollector(TimeWaitingStrategy strategy) throws RemoteException {
        super(strategy);
    }

    @Override
    public List<Industry> collectLogic() throws Exception {

        List<Industry> res = new ArrayList<>();

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
            res.add(new Industry(ele.attr("title"), builder.toString()));
            builder.delete(0, builder.length());
        }

        return res;
    }



}

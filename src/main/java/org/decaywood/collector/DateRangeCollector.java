package org.decaywood.collector;

import org.decaywood.timeWaitingStrategy.TimeWaitingStrategy;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author: decaywood
 * @date: 2015/11/27 0:27
 */

/**
 * 时间范围收集器，获取一段时间的Date
 */
public class DateRangeCollector extends AbstractCollector <List<Date>> {

    private final Date from;
    private final Date to;

    public DateRangeCollector(Date from, Date to) throws RemoteException {
        this(null, from, to);
    }


    /**
     *
     * @param strategy 超时等待策略（null则设置为默认等待策略）
     * @param from 起始时间
     * @param to 结束时间
     */
    public DateRangeCollector(TimeWaitingStrategy strategy, Date from, Date to) throws RemoteException {
        super(strategy);
        if(from == null || to == null || from.after(to)) throw new IllegalArgumentException();
        this.from = from;
        this.to = to;
    }

    @Override
    public List<Date> collectLogic() throws Exception {

        List<Date> dates = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        StringBuilder builder = new StringBuilder();

        for (Date i = from; i.before(to) || i.equals(to); ) {

            dates.add(i);

            calendar.setTime(i);
            builder.delete(0, builder.length());
            calendar.add(Calendar.DATE, 1);
            i = calendar.getTime();

        }

        return dates;

    }

}

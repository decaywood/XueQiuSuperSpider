package collector;

import java.util.function.Supplier;

/**
 * @author: decaywood
 * @date: 2015/11/23 13:51
 */
public abstract class AbstractCollector<T> implements Supplier<T> {

    public abstract T collectInternal() throws Exception;

    @Override
    public T get() {
        T res;
        long sleepTimeMill = 500;
        while (true) {
            try {
                res = collectInternal();
                break;
            }
            catch (Exception e) {
                try {
                    Thread.sleep(sleepTimeMill);
                    sleepTimeMill *= 2;
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
            }
        }
        return res;
    }
}

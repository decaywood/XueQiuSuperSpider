import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @author: decaywood
 * @date: 2015/11/23 20:51
 */
public class Combiner <T, R> {

    Supplier<T> supplier;
    Function<T, R> function;


}

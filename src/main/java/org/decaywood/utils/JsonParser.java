package org.decaywood.utils;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

/**
 * @author decaywood (zyx@webull.com)
 * @date 2020/10/7 17:15
 */
public abstract class JsonParser {

    public static <T> List<T> parseArray(Supplier<T> supplier, BiConsumer<T, JsonNode> consumer, JsonNode jsonNode) {
        List<T> res = new ArrayList<>();
        for (JsonNode node : jsonNode) {
            T parse = parse(supplier, node);
            consumer.accept(parse, node);
            res.add(parse);
        }
        return res;
    }

    public static <T> T parse(Supplier<T> supplier, JsonNode jsonNode) {
        T t = supplier.get();
        ReflectionUtils.doWithFields(t.getClass(), field -> {
            ReflectionUtils.makeAccessible(field);
            String v = Optional.ofNullable(jsonNode.get(field.getName())).map(JsonNode::asText).orElse(null);
            field.set(t, v);
        }, f -> f.getType() == String.class);
        return t;
    }
}

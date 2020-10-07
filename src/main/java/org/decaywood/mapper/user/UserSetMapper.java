package org.decaywood.mapper.user;

import com.fasterxml.jackson.databind.JsonNode;
import org.decaywood.collector.UserCommentCollector;
import org.decaywood.entity.User;
import org.decaywood.mapper.AbstractMapper;
import org.decaywood.utils.JsonParser;
import org.decaywood.utils.RequestParaBuilder;
import org.decaywood.utils.URLMapper;
import org.springframework.util.StringUtils;

import java.net.URL;
import java.rmi.RemoteException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户信息组装器
 * @author decaywood (zyx@webull.com)
 * @date 2020/10/7 17:41
 */
public class UserSetMapper extends AbstractMapper<User.UserSetter, User.UserSetter> {

    public UserSetMapper() {
        super(null);
    }

    @Override
    protected User.UserSetter mapLogic(User.UserSetter obj) throws Exception {
        String userId = obj.getUserId();
        if (StringUtils.isEmpty(userId)) {
            return obj;
        }
        String target = URLMapper.USER_MAIN_PAGE.toString();
        RequestParaBuilder builder = new RequestParaBuilder(target)
                .addParameter("user_id", userId);
        URL url = new URL(builder.build());
        String json = request(url);
        JsonNode node = mapper.readTree(json);
        User user = JsonParser.parse(User::new, node.get("user"));
        obj.setUser(user);
        return obj;
    }

    public static void main(String[] args) {
        List<User.UserSetter> sh688180 = new UserCommentCollector().setSymbol("SH688180").setPageSize(10).get().stream().map(new UserSetMapper()).collect(Collectors.toList());
        System.out.println();
    }
}

package org.decaywood.mapper.user;

import com.fasterxml.jackson.databind.JsonNode;
import org.decaywood.entity.User;
import org.decaywood.mapper.AbstractMapper;
import org.decaywood.utils.JsonParser;
import org.decaywood.utils.RequestParaBuilder;
import org.decaywood.utils.URLMapper;
import org.springframework.util.StringUtils;

import java.net.URL;

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
        String target = URLMapper.USER_INFO_JSON.toString();
        RequestParaBuilder builder = new RequestParaBuilder(target)
                .addParameter("id", userId);
        URL url = new URL(builder.build());
        String json = request(url);
        JsonNode node = mapper.readTree(json);
        User user = JsonParser.parse(User::new, node);
        obj.setUser(user);
        return obj;
    }

}

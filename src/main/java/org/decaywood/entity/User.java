package org.decaywood.entity;

/**
 * @author decaywood (zyx@webull.com)
 * @date 2020/10/7 17:37
 */
public class User {

    public interface UserSetter {
        void setUser(User user);
        String getUserId();
    }

    /** 昵称 */
    private String screen_name;
    /** 粉丝数量 */
    private String followers_count;
    /** 自选股票数 */
    private String stocks_count;
    /** 关注人数 */
    private String friends_count;

    public String getScreen_name() {
        return screen_name;
    }

    public void setScreen_name(String screen_name) {
        this.screen_name = screen_name;
    }

    public String getFollowers_count() {
        return followers_count;
    }

    public void setFollowers_count(String followers_count) {
        this.followers_count = followers_count;
    }

    public String getStocks_count() {
        return stocks_count;
    }

    public void setStocks_count(String stocks_count) {
        this.stocks_count = stocks_count;
    }

    public String getFriends_count() {
        return friends_count;
    }

    public void setFriends_count(String friends_count) {
        this.friends_count = friends_count;
    }
}

package org.decaywood.entity;

/**
 * 评论模型
 * @author decaywood (zyx@webull.com)
 * @date 2020/10/7 16:56
 */
public class Comment implements User.UserSetter {
    private String description;
    private String text;
    private String target;
    private String title;
    private String user_id;
    private User user;

    @Override
    public void setUser(User user) {
        this.user = user;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    @Override
    public String getUserId() {
        return this.user_id;
    }

    public User getUser() {
        return user;
    }
}

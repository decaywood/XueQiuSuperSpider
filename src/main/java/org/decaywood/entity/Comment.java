package org.decaywood.entity;

import java.util.List;
import java.util.Objects;

/**
 * 评论模型
 * @author decaywood
 * @date 2020/10/7 19:18
 */
public class Comment implements User.UserSetter {

    public interface CommentSetter {
        void addComments(List<Comment> comments);
        String getPostId();
        int getReplyCnt();
    }

    /** 评论id */
    private String id;

    /** 用户信息 */
    private User user;

    /** 引用 */
    private Comment reply_comment;

    /** 评论内容 */
    private String text;

    /** 用户id */
    private String user_id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public Comment getReply_comment() {
        return reply_comment;
    }

    public void setReply_comment(Comment reply_comment) {
        this.reply_comment = reply_comment;
    }

    @Override
    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String getUserId() {
        return user_id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Comment comment = (Comment) o;
        return id.equals(comment.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

package org.decaywood.entity;

import org.springframework.util.CollectionUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 评论模型
 * @author decaywood (zyx@webull.com)
 * @date 2020/10/7 16:56
 */
public class PostInfo implements User.UserSetter, Comment.CommentSetter {
    /** 描述 */
    private String description;
    /** 正文 */
    private String text;
    /** 帖子id */
    private String id;
    /** userId/commentId */
    private String target;
    /** 标题 */
    private String title;
    /** 用户id */
    private String user_id;
    /** 用户信息 填充次信息需要用到 UserSetMapper */
    private User user;
    /** 点赞数 */
    private String like_count;
    /** 已阅读次数 */
    private String view_count;
    /** 回复数量 */
    private String reply_count;

    private Set<Comment> comments = new HashSet<>();

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLike_count() {
        return like_count;
    }

    public void setLike_count(String like_count) {
        this.like_count = like_count;
    }

    public String getView_count() {
        return view_count;
    }

    public void setView_count(String view_count) {
        this.view_count = view_count;
    }

    public String getReply_count() {
        return reply_count;
    }

    public void setReply_count(String reply_count) {
        this.reply_count = reply_count;
    }

    public User getUser() {
        return user;
    }

    public Set<Comment> getComments() {
        return comments;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }

    @Override
    public void addComments(List<Comment> comments) {
        if (CollectionUtils.isEmpty(comments)) {
            return;
        }
        this.comments = new HashSet<>(this.comments);
        this.comments.addAll(comments);
    }

    @Override
    public String getPostId() {
        return getId();
    }

    @Override
    public int getReplyCnt() {
        return Integer.parseInt(reply_count);
    }

    @Override
    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String getUserId() {
        return this.user_id;
    }
}

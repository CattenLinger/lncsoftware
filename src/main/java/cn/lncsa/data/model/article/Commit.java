package cn.lncsa.data.model.article;

import cn.lncsa.data.model.user.User;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by catten on 16/3/4.
 */
@Entity
@Table(name = "commits")
public class Commit {

    private Integer id;

    private Integer userId;
    private String contents;
    private Article targetArticle;
    private Commit replyTo;
    private Date date;

    public Commit() {
    }

    public Commit(Integer userId, String contents, Commit replyTo) {
        this.userId = userId;
        this.contents = contents;
        this.replyTo = replyTo;
    }

    public Commit(Integer userId, String contents, Article targetArticle) {
        this.userId = userId;
        this.contents = contents;
        this.targetArticle = targetArticle;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @ManyToOne
    public Article getTargetArticle() {
        return targetArticle;
    }

    public void setTargetArticle(Article targetArticle) {
        this.targetArticle = targetArticle;
    }

    @OneToOne
    public Commit getReplyTo() {
        return replyTo;
    }

    public void setReplyTo(Commit replyTo) {
        this.replyTo = replyTo;
    }
}

package cn.lncsa.data.model;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Created by catten on 16/3/4.
 */
@Entity
@Table(name = "commits")
public class Commit implements IBaseModel<Integer> {

    private Integer id;

    private String contents;
    private Date date;

    private Article targetArticle;
    private Commit replyTo;

    private User user;

    public Commit(){

    }

    public Commit(String contents, Article targetArticle, User user) {
        this.contents = contents;
        this.targetArticle = targetArticle;
        this.user = user;
        this.setDate(new Date());
    }

    /*
    * Getter and setter
    *
    * */

    @Id
    @Column(length = 32)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @ManyToOne
    public User getUser() {
        return user;
    }

    public void setUser(User userId) {
        this.user = userId;
    }

    @NotEmpty(message = "validate_commit_content_empty")
    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    @Temporal(TemporalType.TIMESTAMP)
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    public Article getTargetArticle() {
        return targetArticle;
    }

    public void setTargetArticle(Article targetArticle) {
        this.targetArticle = targetArticle;
    }

    @OneToOne(fetch = FetchType.LAZY)
    public Commit getReplyTo() {
        return replyTo;
    }

    public void setReplyTo(Commit replyTo) {
        this.replyTo = replyTo;
    }
}

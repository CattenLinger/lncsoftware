package cn.lncsa.data.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.util.Date;
import java.util.Set;

/**
 * Created by catten on 16/1/15.
 */
@Entity
@Table(name = "article_head")
@JsonIgnoreProperties({"author","body"})
public class Article implements IBaseModel<Integer> {

    public static String STATUS_DRAFT = "draft";
    public static String STATUS_SUBMITTED = "submitted";
    public static String STATUS_PUBLISHED = "published";
    public static String STATUS_PRIVATE = "private";
    public static String STATUS_DELETED = "deleted";
    public static String STATUS_BANNED = "banned";
    public static String STATUS_AUDITING = "auditing";

    public Article(){
        this.status = STATUS_DRAFT;
        this.createDate = new Date();
    }

    private Integer id;

    private String title;
    private String subtitle;
    private String status;

    private Date createDate;

    private User author;

    private ArticleBody body;
    private Set<Topic> topics;

    /*
    *  Getter and setter
    * */

    @Column(nullable = false,length = 15)
    @NotEmpty(message = "validate_article_status_empty")
    @Pattern(regexp = "^(draft|submitted|published|private|delete|banned|auditing)$", message = "validate_article_status_not_in_set")
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Column(nullable = false)
    @Length(min = 1, max = 128, message = "validate_article_title_not_in_range")
    @NotEmpty(message = "validate_article_title_empty")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @ManyToOne
    public User getAuthor() {
        return author;
    }

    public void setAuthor(User authorId) {
        this.author = authorId;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String previewSentences) {
        this.subtitle = previewSentences;
    }

    @Id
    @Column(length = 32)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @OneToOne(fetch = FetchType.LAZY)
    public ArticleBody getBody() {
        return body;
    }

    public void setBody(ArticleBody contentId) {
        this.body = contentId;
    }

    @ManyToMany
    public Set<Topic> getTopics() {
        return topics;
    }

    public void setTopics(Set<Topic> topics) {
        this.topics = topics;
    }
}

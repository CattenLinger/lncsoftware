package cn.lncsa.view;

import cn.lncsa.data.model.Article;
import cn.lncsa.data.model.ArticleBody;
import cn.lncsa.data.model.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Max;
import javax.validation.constraints.Pattern;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by cattenlinger on 2017/2/13.
 */
public class ArticleDTO {

    private Integer id;
    private Article article;

    private String title;
    private String subtitle;
    private User author;
    private String status;
    private Date createDate;
    private Date modifiedDate;
    private String content;
    private Set<String> topics;

    public ArticleDTO() {

    }

    public ArticleDTO(Article article) {
        this.article = article;
        this.id = article.getId();
        this.title = article.getTitle();
        this.subtitle = article.getSubtitle();
        this.createDate = article.getCreateDate();
        if (article.getBody() != null) {
            this.modifiedDate = article.getBody().getLatestModifiedDate();
            this.content = article.getBody().getContent();
        } else this.modifiedDate = createDate;
        this.author = article.getAuthor();
    }

    /*
    *
    * Logic
    *
    * */

    public String getAuthorName() {
        if (this.author != null) {
            return this.author.getShownName();
        }
        return null;
    }

    @JsonIgnore
    public Article merge(Article origin) {
        if(this.article != origin) this.article = origin;
        origin.setTitle(title);
        origin.setSubtitle(subtitle);
        origin.setStatus(status);
        origin.setAuthor(author == null ? origin.getAuthor() : author);
        origin.setTopics(topics);
        if (origin.getBody() == null && this.content != null) {
            ArticleBody articleBody = new ArticleBody(content);
            articleBody.setLatestModifiedDate(modifiedDate);
            origin.setBody(articleBody);
        } else {
            article.getBody().setContent(content);
            article.getBody().setLatestModifiedDate(modifiedDate);
        }
        return origin;
    }

    public Article merge(){
        return merge(article);
    }

    public Article parse() {
        Article article = new Article();
        return merge(article);
    }

    /*
    *
    * Getter and setter
    *
    *
    * */

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @JsonIgnore
    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    @Length(min = 1, max = 128, message = "validate_article_title_not_in_range")
    @NotEmpty(message = "validate_article_title_empty")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Max(value = 140, message = "validate_article_subtitle_too_long")
    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    @NotEmpty(message = "validate_article_status_empty")
    @Pattern(regexp = "^(draft|submitted|published|private|delete|banned|auditing)$", message = "validate_article_status_not_in_set")
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    @JsonIgnore
    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Set<String> getTopics() {
        return topics;
    }

    public void setTopics(Set<String> topics) {
        this.topics = topics;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}

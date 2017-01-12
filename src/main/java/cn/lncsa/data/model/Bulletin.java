package cn.lncsa.data.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by catten on 16/1/15.
 */
@Entity
@Table(name = "bulletins")
@JsonIgnoreProperties({"author"})
public class Bulletin implements IBaseModel<Integer> {

    private Integer id;

    private String type;
    private String content;
    private String imageLink;
    private String link;

    private Date createDate;
    private Date periodOfValidity;

    private User author;

    /*
    * Getter and setter
    *
    * */

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    @Column(length = 15)
    @NotEmpty(message = "validate_bulletin_type_empty")
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @NotEmpty(message = "validate_bulletin_content_empty")
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Temporal(TemporalType.TIMESTAMP)
    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
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

    @ManyToOne
    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME, pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getPeriodOfValidity() {
        return periodOfValidity;
    }

    public void setPeriodOfValidity(Date periodOfValidity) {
        this.periodOfValidity = periodOfValidity;
    }
}

package cn.lncsa.view;

import cn.lncsa.data.model.Commit;
import cn.lncsa.data.model.User;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by cattenlinger on 2017/1/16.
 */
public class ArticleCommitDTO {
    private Integer id = null;

    private String shownName = null;
    private Integer userId = null;
    private String headPic = null;
    private Date createDate = null;
    private String content = null;

    private Integer replyTo = null;
    private String replyToContent = null;

    public ArticleCommitDTO() {
    }

    public ArticleCommitDTO(Commit commit){
        this.id = commit.getId();

        User user = commit.getUser();
        if(user != null){
            this.shownName = user.getShownName();
            this.headPic = user.getHeadPic();
            this.userId = user.getId();
        }

        this.createDate = commit.getDate();
        this.content = commit.getContents();

        Commit replyCommit = commit.getReplyTo();
        if(replyCommit != null){
            this.replyTo = replyCommit.getId();
            this.replyToContent = replyCommit.getContents();
        }
    }

    public static List<ArticleCommitDTO> convert(List<Commit> commits){
        return commits.parallelStream().map(ArticleCommitDTO::new).collect(Collectors.toList());
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getShownName() {
        return shownName;
    }

    public void setShownName(String shownName) {
        this.shownName = shownName;
    }

    public String getHeadPic() {
        return headPic;
    }

    public void setHeadPic(String headPic) {
        this.headPic = headPic;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getReplyTo() {
        return replyTo;
    }

    public void setReplyTo(Integer replyTo) {
        this.replyTo = replyTo;
    }

    public String getReplyToContent() {
        return replyToContent;
    }

    public void setReplyToContent(String replyToContent) {
        this.replyToContent = replyToContent;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}

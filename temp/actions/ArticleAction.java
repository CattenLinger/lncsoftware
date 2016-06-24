package cn.lncsa.actions;

import cn.lncsa.common.RegexTools;
import cn.lncsa.common.StringTools;
import cn.lncsa.data.Passport;
import cn.lncsa.data.dao.IAppInfoDAO;
import cn.lncsa.data.dao.IArticleDAO;
import cn.lncsa.data.dao.IBulletinDAO;
import cn.lncsa.data.model.Article;
import cn.lncsa.data.model.Bulletin;
import cn.lncsa.data.model.Right;
import cn.lncsa.data.model.User;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import org.apache.struts2.interceptor.RequestAware;
import org.apache.struts2.interceptor.SessionAware;
import org.pegdown.PegDownProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by catten on 16/4/25.
 */
public class ArticleAction extends ActionSupport implements RequestAware, SessionAware {
    private static PegDownProcessor pegDownProcessor = new PegDownProcessor();

    private IArticleDAO articleDAO;
    private IBulletinDAO bulletinDAO;

    @Autowired
    public void setArticleDAO(IArticleDAO articleDAO) {
        this.articleDAO = articleDAO;
    }

    @Autowired
    public void setBulletinDAO(IBulletinDAO bulletinDAO) {
        this.bulletinDAO = bulletinDAO;
    }

    private String id;
    private String title;
    private String tags;
    private String context;
    private String previewSentences;

    private String page;

    private String preId;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String execute() {
        return "success";
    }

    public String getPreview() {
        if (preId != null) {
            try {
                Article article = articleDAO.findOne(Integer.parseInt(preId));
                if (article != null) {
                    requestContext.put("article", article);
                }
            } catch (Throwable e) {
                //Do nothing
            }
        }
        return "success";
    }

    //List articles
    public String list() {
        List<Article> articles;
        int itemPerPage = 10;
        int pageNum;
        int totalPage;
        if (page == null) {
            pageNum = 1;
        } else {
            if (!page.matches("\\d+")) {
                if ("<<".equals(page))
                    pageNum = 1;
                else pageNum = (int) (articleDAO.count() / itemPerPage);
            } else {
                pageNum = Integer.parseInt(page);
            }
        }
        Page<Article> thePage = articleDAO.findAll(new PageRequest(pageNum - 1,itemPerPage));
        articles = thePage.getContent();
        totalPage = thePage.getTotalPages();
        requestContext.put("articleList", articles);
        requestContext.put("pageList", rendPageList(pageNum, totalPage, itemPerPage));
        requestContext.put("currentPage", pageNum);
        requestContext.put("pageCount", totalPage);
        return "success";
    }

    //Rending article for shown
    public String rend() {
        Article article;
        try {
            article = articleDAO.findOne(Integer.parseInt(preId));
        } catch (Throwable e) {
            return "error";
        }
        if (article == null) return "not-found";
        requestContext.put("renderedArticle", pegDownProcessor.markdownToHtml(article.getContext()));
        requestContext.put("article", article);
        User user = (User) ActionContext.getContext().getSession().get("passport");
        if (user.getId().intValue() == article.getAuthor().getId().intValue()) {
            requestContext.put("isAuthor", "true");
        }
        return "success";
    }

    private List<String> rendPageList(int position, int totalPage, int pagesPerList) {
        List<String> list;
        //Only 1 page.
        if (position <= 1 && totalPage <= 1) {
            list = new ArrayList<>(1);
            list.add("1");
            return list;
        }

        //More than one page

        //Lesser than pages per list
        if (totalPage <= pagesPerList) {
            list = new ArrayList<>(totalPage);
            for (int i = 0; i < totalPage; i++) {
                list.add(String.valueOf(i + 1));
            }
        } else {
            list = new ArrayList<>(pagesPerList);
            int start, end;
            int before = (int) Math.floor(pagesPerList / 2);
            if (position < before) {
                start = 1;
                end = pagesPerList;
            } else {
                start = (int) (position - Math.floor(pagesPerList / 2));
                if (start == 0) start = 1;
                end = (start + pagesPerList) > totalPage ? totalPage : start + pagesPerList;
                if (position > (totalPage - (pagesPerList - before))) start = totalPage - pagesPerList;
            }
            if (start != 1) list.add("<<");
            for (int i = start; i <= end; i++) {
                list.add(String.valueOf(i));
            }
            if (end != totalPage) list.add(">>");
        }
        return list;
    }

    public String load() {
        User user = (User) sessionContext.get("passport");
        if (user == null) return "login";
        if (id != null) {
            try {
                Article article = articleDAO.findOne(Integer.parseInt(preId));
                if (user.getId().intValue() != article.getAuthor().getId().intValue()) {
                    requestContext.put("message", "Permission denied.");
                    return "error";
                }
                requestContext.put("articleID", article.getId().toString());
                requestContext.put("article", article);
            } catch (Throwable e) {
                return "error";
            }
        }
        return "success";
    }

    public String save() {
        Passport passport = (Passport) sessionContext.get("passport");
        if (passport == null) return "login";
        boolean writer = false;
        for (Right r : passport.getRights()) {
            if (r.getName().equals("article")) {
                writer = true;
                break;
            }
        }
        if (!writer) {
            requestContext.put("message", "permission denied");
            return "error";
        }

        if (title == null || context == null) {
            return "new";
        }

        Article uploaded = new Article();
        uploaded.setStatus("show");
        uploaded.setDate(new Date());
        uploaded.setPreviewSentences(previewSentences);
        uploaded.setAuthor(passport.getUser());
        uploaded.setContext(context);
        uploaded.setTitle(title);
        //uploaded.setTags(StringTools.splitTags(tags));

        if (id == null || !id.matches("\\w+")) {
            articleDAO.save(uploaded);
        } else {
            uploaded = articleDAO.findOne(Integer.parseInt(id));
            articleDAO.save(uploaded);
        }
        return "success";
    }

    Map<String, Object> requestContext;

    @Override
    public void setRequest(Map<String, Object> map) {
        requestContext = map;
        requestContext.put("bulletin", bulletinDAO.getArticleBulletin());
    }

    private Map<String, Object> sessionContext;

    @Override
    public void setSession(Map<String, Object> map) {
        sessionContext = map;
    }

    public String getPreviewSentences() {
        return previewSentences;
    }

    public void setPreviewSentences(String previewSentences) {
        this.previewSentences = previewSentences;
    }

    public String getPreId() {
        return preId;
    }

    public void setPreId(String preId) {
        this.preId = preId;
    }
}
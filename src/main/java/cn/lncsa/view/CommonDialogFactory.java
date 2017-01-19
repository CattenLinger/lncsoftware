package cn.lncsa.view;

import org.springframework.ui.Model;

/**
 * Created by cattenlinger on 2017/1/19.
 */
public class CommonDialogFactory {
    private String templatePath = "";

    public String createDialog(Model model, String mainTitle, String subTitle, Button... buttonList){
        model.addAttribute("mainTitle",mainTitle);
        model.addAttribute("subTitle",subTitle);
        model.addAttribute("buttonList",buttonList);
        return templatePath;
    }

    public String getTemplatePath() {
        return templatePath;
    }

    public void setTemplatePath(String templatePath) {
        this.templatePath = templatePath;
    }

    public static class Button{
        private String title;
        private String link;
        private String role;

        public Button(String title, String link, String role) {
            this.title = title;
            this.link = link;
            this.role = role;
        }

        public String getTitle() {
            return title;
        }

        public String getLink() {
            return link;
        }

        public String getRole() {
            return role;
        }
    }
}

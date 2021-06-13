package ua.training.myWeb.tags;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;

/**
 * Tag for selected/non selected options creation in choose forms
 */
public class OptionTag extends SimpleTagSupport {

    private String value = "";
    private String title = "";
    private boolean selected = false;

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void doTag() {
        PageContext pageContext = (PageContext) getJspContext();
        JspWriter out = pageContext.getOut();
        try {
            StringBuilder sb = new StringBuilder("<option value=\"");
            sb.append(value).append("\"");
            if (selected) {
                sb.append(" selected");
            }
            sb.append(">").append(title).append("</option>");
            out.println(sb.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

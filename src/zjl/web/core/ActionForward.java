package zjl.web.core;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Properties;

/**
 * 这是一个跳转类（）
 */
public class ActionForward {

    private String path = null;//path是个逻辑名
    private boolean redirect = false;

    public ActionForward(String path) {

        this(path,false);
    }

    public ActionForward(String path,boolean redirect) {
        this.path = path;
        this.redirect = redirect;
    }

    public void forward(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        //应该根据逻辑名找到配置中对应的实际路径
        Properties config = (Properties) (req.getSession().getServletContext().getAttribute("config"));

        if(redirect){
            resp.sendRedirect(config.getProperty(path));
        }else{
            req.getRequestDispatcher(config.getProperty(path)).forward(req,resp);
        }


    }


}

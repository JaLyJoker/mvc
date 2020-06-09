package zjl.web.core;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public abstract class Action {

    public abstract ActionForward execute (HttpServletRequest request, HttpServletResponse response,ActionForm form) throws ServletException, IOException;

}

package zjl.web.action;

import zjl.web.core.Action;
import zjl.web.core.ActionForm;
import zjl.web.core.ActionForward;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginAction extends Action {

    public LoginAction() {
        System.out.println("loginAction");
    }

    @Override
    public ActionForward execute(HttpServletRequest request, HttpServletResponse response, ActionForm form) throws ServletException, IOException {

        System.out.println(form);

        return new ActionForward("success");
    }


}

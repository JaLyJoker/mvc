package zjl.web.form;

import zjl.web.core.ActionForm;

/**
 * 搜集Login表单信息
 */
public class LoginActionForm extends ActionForm {

    private String uname;
    private String upass;

    public void setUname(String uname) {
        this.uname = uname;
    }

    public void setUpass(String upass) {
        this.upass = upass;
    }

    public String getUname() {
        return uname;
    }

    public String getUpass() {
        return upass;
    }

    @Override
    public String toString() {
        return "LoginActionForm{" +
                "uname='" + uname + '\'' +
                ", upass='" + upass + '\'' +
                '}';
    }

    @Override
    public boolean validate() {
        return false;
    }
}

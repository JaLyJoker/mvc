package zjl.web.form;

import zjl.web.core.ActionForm;

/**
 * 搜集Login表单信息
 */
public class DemoActionForm extends ActionForm {

    private String age;


    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    @Override
    public boolean validate() {
        return false;
    }
}

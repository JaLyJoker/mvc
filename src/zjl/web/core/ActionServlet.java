package zjl.web.core;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

public class ActionServlet extends HttpServlet {



    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req,resp);
    }


    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //拿出配置文件
        Properties config = (Properties) this.getServletContext().getAttribute("config");
        //拿出池(key:类的全路径，value：该类对应的实例)
        Properties actionPool = (Properties) this.getServletContext().getAttribute("actionPool");
        //通过获得提交的路径
        String uri = req.getRequestURI();

        //截取uri
        int a = uri.lastIndexOf("/");
        int b = uri.indexOf(".");
        if(a != -1 && b != -1 && b > a){
            uri = uri.substring(a+1,b);
        }

        //通过uri要封装数据的ActionForm
        String formuri = uri + "Form";
        //zjl.web.form.LoginActionForm
        String classFormName = config.getProperty(formuri);
        //反射出form实例
        ActionForm form = null;
        try {
            Class c = Class.forName(classFormName);
            form = (ActionForm)c.newInstance();
            //拿到请求所有的参数
            Set<Map.Entry<String,String[]>> set = req.getParameterMap().entrySet();
            for (Map.Entry<String, String[]> entry : set) {
                //param===(uname,upass)
                String param = entry.getKey();
                //找到对应的set方法
                Method m = c.getDeclaredMethod("set"+param.substring(0, 1).toUpperCase()+param.substring(1), String.class);
                m.invoke(form, entry.getValue()[0]);
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }

        //通过uri 找到要处理的uri的类的全路径
       String className = config.getProperty(uri);
        Action action = null;
        if(className!=null){
            try {
                //我们先去池子拿对应的实例
                Object obj = actionPool.get(className);
                if(obj == null){
                    //表示第一次访问action
                    //反射出子类的实例，转型成父类的引用
                    action = (Action)Class.forName(className).newInstance();
                    //将实例存入池子中（目的是第二次可以拿到）
                    actionPool.put(className,action);
                }else{
                    //表示第N次调用
                    action = (Action)obj;
                }
                //调用父类的方法（多态调用子类）,返回 一个ActionForward对象
                ActionForward af = action.execute(req, resp,form);
                //跳转
                af.forward(req, resp);
            } catch (Exception e) {
                e.printStackTrace();
            }


        }



    }


    @Override
    public void init() throws ServletException {

        //准备一个存 Action的map集合
        Properties actionPool = new Properties();
        //加载配置文件路径  一次
        String path = this.getServletConfig().getInitParameter("configLocation");
        String configPath = this.getServletContext().getRealPath("/") + path;

        //加载配置文件  一次
        Properties config = new Properties();
        try {
            config.load(new FileInputStream(configPath));
            //将整个配置文件存在应用程序的作用域中
            this.getServletContext().setAttribute("config",config);
            //将整个池存在应用程序的作用域中
            this.getServletContext().setAttribute("actionPool",actionPool);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}

package ws_demo;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
@WebServlet("/login")
public class LoginServer extends HttpServlet{
    @Override
    protected void doPost(HttpServletRequest req,HttpServletResponse res) throws ServletException,IOException{
        req.setCharacterEncoding("UTF-8");
        res.setContentType("text/html;charset=UTF-8");
        String username=req.getParameter("name");
        String password=req.getParameter("password");
        try{
            String userId=UserDAO.login("username","password");
            if(userId!=null){
                res.getWriter().write("登陆成功!");
            }else{
                res.getWriter().write("登陆失败!请检查用户名或密码是否正确!");
            }
        } catch (Exception e) {
           res.getWriter().write("登陆失败!"+e.getMessage());
        }
    }
}

package ws_demo;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.websocket.Session;
import java.io.IOException;

@WebServlet("/login")

public class LoginServer extends HttpServlet{
    @Override
    protected void doPost(HttpServletRequest req,HttpServletResponse res) throws ServletException,IOException{
        req.setCharacterEncoding("UTF-8");
        res.setContentType("text/html;charset=UTF-8");
        String username=req.getParameter("username");
        String password=req.getParameter("password");
        try{
            String userId=UserDAO.login(username,password);
            if(userId!=null){
                HttpSession session=req.getSession();
                session.setAttribute("userId",username);
                res.sendRedirect("chat.html?username=" + username);
                return;
            }else{
                res.getWriter().write("登录失败!请检查用户名或密码是否正确!");
            }
        } catch (Exception e) {
           res.getWriter().write("登录失败!" + e.getMessage());
        }
    }
}

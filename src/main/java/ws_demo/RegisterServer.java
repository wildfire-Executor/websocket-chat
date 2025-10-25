package ws_demo;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.SQLIntegrityConstraintViolationException;
@WebServlet("/register")
public class RegisterServer extends HttpServlet{
    @Override
    protected void doPost(HttpServletRequest req,HttpServletResponse res)throws ServletException,IOException{
        String username=req.getParameter("username");
        String password=req.getParameter("password");
        req.setCharacterEncoding("UTF-8");
        res.setContentType("text/html;charset=UTF-8");
        if(username==null||password==null||username.isEmpty()||password.isEmpty()){
            res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            res.getWriter().write("请求出错,请输入用户名及密码");
            return;
        }

        try{
            String userId=UserDAO.register(username,password);
            res.sendRedirect("chat.html?username="+username);
            return;


        } catch (Exception e) {
            res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            res.getWriter().write("注册失败 "+e.getMessage());

        }

    }

}

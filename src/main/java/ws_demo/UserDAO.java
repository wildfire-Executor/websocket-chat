package ws_demo;
import java.sql.*;
import java.util.UUID;
import java.security.MessageDigest;
import java.util.Base64;
public class UserDAO {
    private static final String url = "jdbc:mysql://localhost:3306/chat_people";
    private static final String username = "root";
    private static final String password = "SUTyyds666!";

    public static String register(String usernameStr,String passwordStr) throws Exception{

    String userId =UUID.randomUUID().toString();
    MessageDigest digest=MessageDigest.getInstance("SHA-256");
    byte []hash=digest.digest(passwordStr.getBytes("UTF-8"));
    String hashedPassword=Base64.getEncoder().encodeToString(hash);

    String sql="insert into users (id,username,password_hash) values (?,?,?)";
    try(Connection conn=DriverManager.getConnection(url,username,password);
        PreparedStatement ps=conn.prepareStatement(sql);){
        ps.setString(1,userId);
        ps.setString(2,usernameStr);
        ps.setString(3,hashedPassword);
        ps.executeUpdate();
    }
        System.out.println("注册成功，用户ID: " + userId);

        return userId;
    }

    public static String login(String usernameStr,String passwordStr)throws Exception{
      String sql="select id,password_hash from user where username=?";
      try(Connection conn=DriverManager.getConnection(url,username,password);
          PreparedStatement ps=conn.prepareStatement(sql)){
          ps.setString(1,usernameStr);
          try(ResultSet rs=ps.executeQuery()){
              if(rs.next()){
                  String storedPassword=rs.getString("password_hash");
                  String userId=rs.getString("id");
                  MessageDigest digest=MessageDigest.getInstance("SHA-256");
                  byte []hash=digest.digest(passwordStr.getBytes("UTF-8"));
                  String hashedInputPassword=Base64.getEncoder().encodeToString(hash);

                  if(storedPassword.equals(hashedInputPassword)){
                      System.out.println("登陆成功!用户id:" + userId);
                      return  userId;
                  }else{
                      System.out.println("登陆失败!");
                      return null;
                  }
              }else{
                  System.out.println("找不到用户!");
                  return null;
              }


          }
      }

    }


}

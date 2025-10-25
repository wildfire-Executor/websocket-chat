package ws_demo;
import java.security.SecureRandom;

import java.sql.*;
import java.util.UUID;
import java.security.MessageDigest;
import java.util.Base64;
public class UserDAO {
    private static final String url = "jdbc:mysql://localhost:3306/chat_people";
    private static final String username = "root";
    private static final String password = "SUTyyds666!";
    private static final int SALT_LENGHT = 16;

    private static String generateSalt(){
        byte[] salt = new byte[SALT_LENGHT];
        SecureRandom random = new SecureRandom();
        random.nextBytes(salt);
        return  Base64.getEncoder().encodeToString(salt);
    }

    private static String hashPassword(String password,String salt) throws Exception{
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        String combined = salt + password;
        byte[] hash = digest.digest(combined.getBytes("UTF-8"));
        return Base64.getEncoder().encodeToString(hash);
    }

    public static String register(String usernameStr,String passwordStr) throws Exception{
        Class.forName("com.mysql.cj.jdbc.Driver");
    String userId =UUID.randomUUID().toString();
    String salt = generateSalt();
    MessageDigest digest=MessageDigest.getInstance("SHA-256");
    byte []hash=digest.digest(passwordStr.getBytes("UTF-8"));
    String hashedPassword=hashPassword(passwordStr,salt);

    String sql="insert into users (id,username,password_hash,salt) values (?,?,?,?)";
    try(Connection conn=DriverManager.getConnection(url,username,password);
        PreparedStatement ps=conn.prepareStatement(sql);){
        ps.setString(1,userId);
        ps.setString(2,usernameStr);
        ps.setString(3,hashedPassword);
        ps.setString(4,salt);
        ps.executeUpdate();
    }
        System.out.println("注册成功，用户ID: " + userId);
        return userId;
    }

    public static String login(String usernameStr,String passwordStr)throws Exception{
        Class.forName("com.mysql.cj.jdbc.Driver");
      String sql="select id,password_hash,salt from users where username=?";
      try(Connection conn=DriverManager.getConnection(url,username,password);
          PreparedStatement ps=conn.prepareStatement(sql)){
          ps.setString(1,usernameStr);
          try(ResultSet rs=ps.executeQuery()){
              if(rs.next()){
                  String storedPassword=rs.getString("password_hash");
                  String userId=rs.getString("id");
                  String salt = rs.getString("salt");
                  MessageDigest digest=MessageDigest.getInstance("SHA-256");
                  byte []hash=digest.digest(passwordStr.getBytes("UTF-8"));
                  String hashedInputPassword=hashPassword(passwordStr,salt);

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

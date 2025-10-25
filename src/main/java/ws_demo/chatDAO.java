package ws_demo;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
public class chatDAO {
    private static final String url = "jdbc:mysql://localhost:3306/chat_people";
    private static final String username = "root";
    private static final String password = "SUTyyds666!";
    public static void SaveMessage(String sender, String content) {
        String sql = "insert into chat_message (sender,content) value (?,?)";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection conn = DriverManager.getConnection(url, username, password);
                 PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, sender);
                ps.setString(2, content);
                ps.executeUpdate();
                System.out.println("消息保存成功: " + sender + " -> " + content);
            } catch (SQLException e) {
                throw new RuntimeException("保存消息失败", e);
            }
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("找不到 MySQL 驱动", e);
        }
    }

}
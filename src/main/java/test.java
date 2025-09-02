import ws_demo.UserDAO;

public class test {
    public static void main(String[] args) throws Exception {
        String userID=UserDAO.register("alice","12345678");
        System.out.println("分配的 userId: " + userID);
    }
}

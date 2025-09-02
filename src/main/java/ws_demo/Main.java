package ws_demo;

public class Main {
    public static void main(String[] args) throws Exception {
        String userID=UserDAO.register("alice","12345678");
        System.out.println("分配的 userId: " + userID);
    }
}

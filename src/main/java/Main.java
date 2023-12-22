import model.db.DBManager;

public class Main {
    public static void main(String[] args) {
        var c = DBManager.getInstance().getConnection();
        System.out.println("");
    }
}

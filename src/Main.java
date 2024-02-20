import java.sql.Connection;



public class Main {
    public static void main(String[] args)
    {
        DbFunctions DB = new DbFunctions();
        Connection conn= DB.connect_to_db("GunShop","postgres","1q2w3e");
        Menu menu = new Menu();

        menu.showMenu(DB, conn);

    }
}
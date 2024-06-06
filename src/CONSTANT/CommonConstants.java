package CONSTANT;

import java.awt.*;

public class CommonConstants {
    // color hex values
    public static final Color PRIMARY_COLOR = Color.decode("#000814"); // Dark Blue
    public static final Color SECONDARY_COLOR = Color.decode("#001D3D"); // Darker Blue
    public static final Color TEXT_COLOR = Color.decode("#FFC300"); // Yellow
    public static final Color BACKGROUND_COLOR = Color.decode("#003566"); // Dark Cyan
    public static final Color BUTTON_COLOR = Color.decode("#FFC300"); // Yellow
    public static final Color BUTTON_TEXT_COLOR = Color.decode("#000814"); // Dark Blue
    public static final Color BLACK_COLOR = Color.decode("#000000"); // Black


    // mysql credentials

    // place the url of your db in this format -> jdbc:mysql:ip_address/schema-name
    public static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/carrentalsystem";

    // place the username that you made here (might be different)
    public static final String DB_USERNAME = "root";

    // place the password that you made here (might be different)
    public static final String DB_PASSWORD = "Syeddani123@";
    public static final String DB_CARS_TABLE_NAME = "car";
    public static final String DB_CUSTOMERS_TABLE_NAME = "customer";
    public static final String DB_TRANSACTIONS_TABLE_NAME = "transaction";
    public static final String DB_CARRENTAL_TABLE_NAME = "carrental";
    public static final String DB_LIVERENTING_TABLE_NAME = "liverenting";

    // Example daily rental rate

}

package Database;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Connect {

    private static final String CONFIG_RESOURCE = "/Database/db.properties";
    private static final String DEFAULT_URL = "jdbc:mysql://localhost:3306/store?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String DEFAULT_USERNAME = "root";
    private static final String DEFAULT_PASSWORD = "123456789";

    private static Properties loadDbProperties() {
        Properties properties = new Properties();

        try (InputStream inputStream = Connect.class.getResourceAsStream(CONFIG_RESOURCE)) {
            if (inputStream != null) {
                properties.load(inputStream);
            } else {
                properties.setProperty("db.url", DEFAULT_URL);
                properties.setProperty("db.user", DEFAULT_USERNAME);
                properties.setProperty("db.password", DEFAULT_PASSWORD);
            }
        } catch (IOException e) {
            System.err.println("Unable to read DB config from classpath resource: " + CONFIG_RESOURCE);
            properties.setProperty("db.url", DEFAULT_URL);
            properties.setProperty("db.user", DEFAULT_USERNAME);
            properties.setProperty("db.password", DEFAULT_PASSWORD);
            e.printStackTrace();
        }

        return properties;
    }

    public static Connection getConnection() {
        Connection c = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Properties config = loadDbProperties();
            String url = config.getProperty("db.url", DEFAULT_URL);
            String username = config.getProperty("db.user", DEFAULT_USERNAME);
            String password = config.getProperty("db.password", DEFAULT_PASSWORD);
            c = DriverManager.getConnection(url, username, password);
            System.out.println("OK");
        } catch (ClassNotFoundException e) {
            System.err.println("Missing MySQL JDBC driver. Place mysql-connector-j-8.3.0.jar in lib/.");
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return c;
    }

    public static void closeConnection(Connection c) {
        try {
            if (c != null) {
                c.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void printInfo(Connection c) {
        try {
            if (c != null) {
                java.sql.DatabaseMetaData mtdt = c.getMetaData();
                System.out.println(mtdt.getDatabaseProductName());
                System.out.println(mtdt.getDatabaseProductVersion());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Connection c = Connect.getConnection();
        Connect.printInfo(c);
        Connect.closeConnection(c);
    }
}
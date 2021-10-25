
package io.fnproject.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Properties;

public class CreateProductFunction {

    private Connection conn = null;

    public CreateProductFunction() {
        try {

            String dbUser = System.getenv().getOrDefault("DB_USER", "test_user");
            System.err.println("DB User " + dbUser);

            String dbPasswd = System.getenv().getOrDefault("DB_PASSWORD", "default_password1");

            String dbServiceName = System.getenv().getOrDefault("DB_SERVICE_NAME", "localhost");
            System.err.println("DB Service name " + dbServiceName);

            String tnsAdminLocation = System.getenv().getOrDefault("CLIENT_CREDENTIALS", ".");
            System.err.println("TNS Admin location " + tnsAdminLocation);

            String dbUrl = "jdbc:oracle:thin:@" + dbServiceName + "?TNS_ADMIN=" + tnsAdminLocation;
            System.err.println("DB URL " + dbUrl);

            Properties prop = new Properties();

            prop.setProperty("user", dbUser);
            prop.setProperty("password", dbPasswd);

            System.err.println("Connecting to Oracle ATP DB......");

            conn = DriverManager.getConnection(dbUrl, prop);
            if (conn != null) {
                System.err.println("Connected to Oracle ATP DB successfully");
            }

        } catch (Throwable e) {
            System.err.println("DB connectivity failed due - " + e.getMessage());
        }
    }

    public String handle(CreateProductInfo prodcutInfo) {
        return create(prodcutInfo);
    }

    private String create(CreateProductInfo prodcutInfo) {
        String status = "Failed to insert product " + prodcutInfo;

        if (conn == null) {
            System.err.println("Warning: JDBC connection was 'null'");
            return status;
        }

        System.err.println("Inserting prodcut info into DB " + prodcutInfo);

        int updated = 0;
        try (PreparedStatement st = conn.prepareStatement("INSERT INTO PRODUCTS VALUES (?,?)")) {
            st.setString(1, prodcutInfo.getProduct_NAME());
            st.setInt(2, prodcutInfo.getProduct_COUNT());
            updated = st.executeUpdate();

            System.err.println(updated + " rows inserted");
            status = "Created product " + prodcutInfo;

        } catch (Exception se) {
            System.err.println("Unable to insert data into DB due to - " + se.getMessage());
        }

        return status;
    }

}

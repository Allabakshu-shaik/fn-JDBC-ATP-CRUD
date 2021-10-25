package io.fnproject.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Properties;

public class UpdateProductFunction {

    private Connection conn = null;

    public UpdateProductFunction() {
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

    public String handle(UpdateProductInfo productInfo) {
        return update(productInfo);
    }

    private String update(UpdateProductInfo productInfo) {
        String status = "Failed to update product " + productInfo;

        if (conn == null) {
            System.err.println("Warning: JDBC connection was 'null'");
            return status;
        }

        System.err.println("Updating product info " + productInfo);

        int updated = 0;
        try (PreparedStatement st = conn.prepareStatement("update PRODUCTS set PRODUCT_COUNT=? where PRODUCT_NAME=?")) {
            st.setString(1, productInfo.getProduct_name());
            st.setInt(2, productInfo.getProduct_count());

            updated = st.executeUpdate();

            System.err.println(updated + " rows updated");
            status = "Updated product " + productInfo;

        } catch (Exception se) {
            System.err.println("Unable to update data in DB due to - " + se.getMessage());
        }
        return status;
    }

}

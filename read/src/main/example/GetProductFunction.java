package io.fnproject.example;

import com.fnproject.fn.api.RuntimeContext;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

public class GetProductFunction {

    private Connection conn = null;

    public GetProductFunction(RuntimeContext ctx) {
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

    public List<Product> handle(String productName) {
        return read(productName);
    }

    private static final String GET_ALL_PRODUCTS = "select * from test_user.products";
    private static final String GET_PRODUCT_INFO = "select * from test_user.products where products.name=?";

    private List<Product> read(String productName) {

        if (conn == null) {
            System.err.println("Warning: JDBC connection was 'null'");
            return Collections.emptyList();
        }

        String query = null;

        if (productName.equals("")) {
            System.err.println("Getting all employees...");
            query = GET_ALL_PRODUCTS;
        } else {
            System.err.println("Fetching employee info for " + productName);
            query = GET_PRODUCT_INFO;
        }

        List<Product> products = new ArrayList<>();

        try (PreparedStatement st = conn.prepareStatement(query)) {
            if (!productName.equals("")) {
                st.setString(1, productName);
            }

            ResultSet productRSet = st.executeQuery();

            while (productRSet.next()) {
                products.add(new Product(productRSet.getString("PRODUCTS_NAME"), productRSet.getInt("PRODUCTS_COUNT")));
            }

        } catch (Exception se) {
            System.err.println("Unable to fetch product info " + se.getMessage());
        }
        return products;
    }

}

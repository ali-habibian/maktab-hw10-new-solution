package q1.db;

import com.mysql.cj.jdbc.MysqlDataSource;
import q1.utilities.Constants;

import java.sql.Connection;
import java.sql.SQLException;

public class DbConnection {
    public static Connection getConnection() {
        MysqlDataSource dataSource = new MysqlDataSource();
        Connection connection = null;
        dataSource.setURL(Constants.DB_URL);
        dataSource.setUser(Constants.DB_USERNAME);
        dataSource.setPassword(Constants.DB_PASSWORD);

        try {
            connection = dataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return connection;
    }
}

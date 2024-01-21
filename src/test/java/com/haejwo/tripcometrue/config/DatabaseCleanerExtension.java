package com.haejwo.tripcometrue.config;

import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class DatabaseCleanerExtension implements AfterEachCallback {

    @Override
    @Transactional
    public void afterEach(ExtensionContext context) throws Exception {
        var applicationContext = SpringExtension.getApplicationContext(context);
        var jdbcTemplate = applicationContext.getBean(JdbcTemplate.class);

        try (var connection = Objects.requireNonNull(Objects.requireNonNull(jdbcTemplate.getDataSource()).getConnection())) {
            var rs = connection.getMetaData().getTables(null, null, "%", new String[]{"TABLE"});

            executeResetTableQuery(jdbcTemplate, rs);

        } catch (Exception exception) {
            throw new RuntimeException("database table clean error");
        }
    }

    private void executeResetTableQuery(JdbcTemplate jdbcTemplate, ResultSet rs) throws SQLException {
        jdbcTemplate.execute("SET FOREIGN_KEY_CHECKS = 0");

        while (rs.next()) {
            var tableName = rs.getString("TABLE_NAME");
            jdbcTemplate.execute(createTruncateTableQuery(tableName));
            jdbcTemplate.execute(createResetAutoIncrementQuery(tableName));
        }

        jdbcTemplate.execute("SET FOREIGN_KEY_CHECKS = 1");
    }

    private String createTruncateTableQuery(String tableName) {
        return "TRUNCATE TABLE " + tableName;
    }

    private String createResetAutoIncrementQuery(String tableName) {
        return "ALTER TABLE " + tableName + " AUTO_INCREMENT = 1";
    }

}

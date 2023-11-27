package com.example.www_week1.Repository;

import com.example.www_week1.Model.Log;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Optional;

public class LogRepository {
    private final Connection connection;

    public LogRepository() {
        this.connection = connection;
    }

    public Optional<Log> findById(Long id) {
        String sql = "SELECT * FROM Log WHERE log_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapResultSetToLog(rs));
                }
            }
        } catch (Exception e) {
            handleException(e);
        }
        return Optional.empty();
    }

    public Long getLogsToLogout(Long accountId) {
        String sql = "SELECT log_id FROM Log WHERE account_id = ? AND logout_date IS NULL ORDER BY login_date DESC LIMIT 1";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, accountId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getLong(1);
                }
            }
        } catch (Exception e) {
            handleException(e);
        }
        return null;
    }

    public int add(Log log) {
        String sql = "INSERT INTO Log(account_id, login_date, logout_date, description) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            setLogParameters(ps, log);
            return ps.executeUpdate();
        } catch (Exception e) {
            handleException(e);
            return 0;
        }
    }

    public int updateLogoutDate(Log log) {
        String sql = "UPDATE Log SET logout_date = ? WHERE log_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setDate(1, log.getLogoutDate());
            ps.setLong(2, log.getLogId());
            return ps.executeUpdate();
        } catch (Exception e) {
            handleException(e);
            return 0;
        }
    }

    public int updateDescription(Log log) {
        String sql = "UPDATE Log SET description = ? WHERE log_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, log.getDescription());
            ps.setLong(2, log.getLogId());
            return ps.executeUpdate();
        } catch (Exception e) {
            handleException(e);
            return 0;
        }
    }

    public int delete(Log log) {
        String sql = "DELETE FROM Log WHERE log_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, log.getLogId());
            return ps.executeUpdate();
        } catch (Exception e) {
            handleException(e);
            return 0;
        }
    }

    private Log mapResultSetToLog(ResultSet rs) throws SQLException {
        Long logId = rs.getLong("log_id");
        Long accountId = rs.getLong("account_id");
        Date loginDate = rs.getDate("login_date");
        Date logoutDate = rs.getDate("logout_date");
        String description = rs.getString("description");
        return new Log(logId, accountId, (java.sql.Date) loginDate, logoutDate, description);
    }

    private void setLogParameters(PreparedStatement ps, Log log) throws SQLException {
        ps.setLong(1, log.getAccountId());
        ps.setDate(2, log.getLoginDate());
        ps.setDate(3, log.getLogoutDate());
        ps.setString(4, log.getDescription());
    }

    private void handleException(Exception e) {
        // Handle or throw the exception appropriately
        e.printStackTrace();
    }
}


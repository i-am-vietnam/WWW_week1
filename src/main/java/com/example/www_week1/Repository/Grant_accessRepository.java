package com.example.www_week1.Repository;

import com.example.www_week1.Model.Grant_access;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Grant_accessRepository {
    private final Connection connection;

    public Grant_accessRepository() {
        this.connection = connection;
    }

    public List<Grant_access> findAll() {
        List<Grant_access> grantAccesses = new ArrayList<>();
        String sql = "SELECT * FROM Grant_access";
        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                grantAccesses.add(mapResultSetToGrantAccess(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return grantAccesses;
    }

    public Optional<Grant_access> findById(Long accountId, Long roleId) {
        String sql = "SELECT * FROM Grant_access WHERE account_id = ? AND role_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, accountId);
            ps.setLong(2, roleId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapResultSetToGrantAccess(rs));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public int add(Grant_access grantAccess) {
        String sql = "INSERT INTO Grant_access VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            setGrantAccessParameters(ps, grantAccess);
            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int updateIs_grant(Grant_access grantAccess) {
        String sql = "UPDATE Grant_access SET is_grant = ? WHERE account_id = ? AND role_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setBoolean(1, grantAccess.isGrant());
            ps.setLong(2, grantAccess.getAccountId());
            ps.setLong(3, grantAccess.getRoleId());
            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int delete(Grant_access grantAccess) {
        String sql = "DELETE FROM Grant_access WHERE account_id = ? AND role_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, grantAccess.getAccountId());
            ps.setLong(2, grantAccess.getRoleId());
            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    private Grant_access mapResultSetToGrantAccess(ResultSet rs) throws Exception {
        Long accountId = rs.getLong("account_id");
        Long roleId = rs.getLong("role_id");
        boolean isGrant = rs.getBoolean("is_grant");
        String note = rs.getString("note");
        return new Grant_access(accountId, roleId, isGrant, note);
    }

    private void setGrantAccessParameters(PreparedStatement ps, Grant_access grantAccess) throws Exception {
        ps.setLong(1, grantAccess.getAccountId());
        ps.setLong(2, grantAccess.getRoleId());
        ps.setBoolean(3, grantAccess.isGrant());
        ps.setString(4, grantAccess.getNote());
    }
}

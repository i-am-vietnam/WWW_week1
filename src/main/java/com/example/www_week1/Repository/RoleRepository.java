package com.example.www_week1.Repository;

import com.example.www_week1.Model.Role;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RoleRepository {
    private final Connection connection;

    public RoleRepository() {
        this.connection = connection;
    }

    public List<Role> findAll() {
        List<Role> roles = new ArrayList<>();
        String sql = "SELECT * FROM Role";
        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                roles.add(mapResultSetToRole(rs));
            }
        } catch (Exception e) {
            handleException(e);
        }
        return roles;
    }

    public List<Role> findRolesByAccountId(Long accountId) {
        List<Role> roles = new ArrayList<>();
        String sql = "SELECT Role.* FROM Role JOIN grant_access ON Role.role_id = grant_access.role_id WHERE grant_access.account_id = ? AND grant_access.is_grant = 1";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, accountId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    roles.add(mapResultSetToRole(rs));
                }
            }
        } catch (Exception e) {
            handleException(e);
        }

        return roles;
    }

    public Optional<Role> findById(Long id) {
        String sql = "SELECT * FROM Role WHERE role_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapResultSetToRole(rs));
                }
            }
        } catch (Exception e) {
            handleException(e);
        }
        return Optional.empty();
    }

    public int add(Role role) {
        String sql = "INSERT INTO Role VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            setRoleParameters(ps, role);
            return ps.executeUpdate();
        } catch (Exception e) {
            handleException(e);
            return 0;
        }
    }

    public int updateName(Role role) {
        String sql = "UPDATE Role SET name = ? WHERE role_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, role.getRoleName());
            ps.setLong(2, role.getRoleId());
            return ps.executeUpdate();
        } catch (Exception e) {
            handleException(e);
            return 0;
        }
    }

    public int updateDescription(Role role) {
        String sql = "UPDATE Role SET description = ? WHERE role_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, role.getDescription());
            ps.setLong(2, role.getRoleId());
            return ps.executeUpdate();
        } catch (Exception e) {
            handleException(e);
            return 0;
        }
    }

    public int updateStatus(Role role) {
        String sql = "UPDATE Role SET status = ? WHERE role_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, role.getStatus());
            ps.setLong(2, role.getRoleId());
            return ps.executeUpdate();
        } catch (Exception e) {
            handleException(e);
            return 0;
        }
    }

    public int delete(Role role) {
        String sql = "DELETE FROM Role WHERE role_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, role.getRoleId());
            return ps.executeUpdate();
        } catch (Exception e) {
            handleException(e);
            return 0;
        }
    }

    private Role mapResultSetToRole(ResultSet rs) throws Exception {
        Long roleId = rs.getLong("role_id");
        String name = rs.getString("name");
        String desc = rs.getString("description");
        int status = rs.getInt("status");
        return new Role(roleId, name, desc, status);
    }

    private void setRoleParameters(PreparedStatement ps, Role role) throws Exception {
        ps.setLong(1, role.getRoleId());
        ps.setString(2, role.getRoleName());
        ps.setString(3, role.getDescription());
        ps.setInt(4, role.getStatus());
    }

    private void handleException(Exception e) {
        // Handle or throw the exception appropriately
        e.printStackTrace();
    }
}


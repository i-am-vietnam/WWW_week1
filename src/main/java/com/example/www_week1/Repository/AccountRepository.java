package com.example.www_week1.Repository;

import com.example.www_week1.Model.Account;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AccountRepository {
    private final Connection connection;

    public AccountRepository() {
        this.connection = connection;
    }

    public List<Account> findAll() {
        List<Account> accounts = new ArrayList<>();
        String sql = "SELECT * FROM Account";
        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                accounts.add(mapResultSetToAccount(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return accounts;
    }

    public Optional<Account> findById(Long id) {
        String sql = "SELECT * FROM Account WHERE account_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapResultSetToAccount(rs));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public Optional<Account> findByEmail(String userEmail) {
        String sql = "SELECT * FROM Account WHERE email = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, userEmail);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapResultSetToAccount(rs));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public int add(Account account) {
        String sql = "INSERT INTO Account VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            setAccountParameters(ps, account);
            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int updateAccount(Account account) {
        String sql = "UPDATE Account SET fullname = ?, password = ?, email = ?, phone = ?, status = ? WHERE account_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            setAccountParameters(ps, account);
            ps.setLong(6, account.getAccountId());
            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int delete(Account account) {
        String sql = "DELETE FROM Account WHERE account_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, account.getAccountId());
            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    private Account mapResultSetToAccount(ResultSet rs) throws Exception {
        Long accountId = rs.getLong("account_id");
        String fullName = rs.getString("fullname");
        String password = rs.getString("password");
        String email = rs.getString("email");
        String phone = rs.getString("phone");
        int status = rs.getInt("status");
        return new Account(accountId, fullName, password, email, phone, status);
    }

    private void setAccountParameters(PreparedStatement ps, Account account) throws Exception {
        ps.setLong(1, account.getAccountId());
        ps.setString(2, account.getFullName());
        ps.setString(3, account.getPassword());
        ps.setString(4, account.getEmail());
        ps.setString(5, account.getPhone());
        ps.setInt(6, account.getStatus());
    }
}


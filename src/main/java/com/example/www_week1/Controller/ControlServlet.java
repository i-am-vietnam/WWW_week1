package com.example.www_week1.Controller;

import com.example.www_week1.Model.Account;
import com.example.www_week1.Model.Grant_access;
import com.example.www_week1.Model.Log;
import com.example.www_week1.Model.Role;
import com.example.www_week1.Repository.AccountRepository;
import com.example.www_week1.Repository.Grant_accessRepository;
import com.example.www_week1.Repository.LogRepository;
import com.example.www_week1.Repository.RoleRepository;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@WebServlet(urlPatterns = "/ControlServlet")
public class ControlServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private final AccountRepository accountRepository = new AccountRepository();
    private final Grant_accessRepository grantAccessRepository = new Grant_accessRepository();
    private final RoleRepository roleRepository = new RoleRepository();
    private final LogRepository logRepository = new LogRepository();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String action = req.getParameter("action");

            switch (action) {
                case "login":
                    handleLogin(req, resp);
                    break;
                case "add-account":
                    handleAddAccount(req, resp);
                    break;
                case "update-account":
                    handleUpdateAccount(req, resp);
                    break;
                case "logout":
                    handleLogout(req, resp);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean handleLogout(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            HttpSession session = req.getSession();
            Account account = (Account) session.getAttribute("account");

            Long logId = logRepository.getLogsToLogout(account.getAccountId());
            Log log = new Log(logId, account.getAccountId(), null, new Date(System.currentTimeMillis()), null);

            logRepository.updateLogoutDate(log);

            session.removeAttribute("account");
            forwardToPage(req, resp, "index.jsp");
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private Account extractAccountFromRequest(HttpServletRequest req) {
        Long id = Long.valueOf(req.getParameter("account_id"));
        String fullName = req.getParameter("fullName");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String phone = req.getParameter("phone");
        int status = Integer.parseInt(req.getParameter("status"));
        return new Account(id, fullName, password, email, phone, status);
    }

    private boolean handleUpdateAccount(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Account account = extractAccountFromRequest(req);
            String[] selectedRoles = req.getParameterValues("roles");

            updateGrantAccess(account.getAccountId(), selectedRoles);
            accountRepository.updateAccount(account);

            forwardToPage(req, resp, "dashboard.jsp");
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private void updateGrantAccess(Long accountId, String[] selectedRoles) throws Exception {
        List<Role> allRoles = roleRepository.findAll();

        for (Role role : allRoles) {
            Long roleId = role.getRoleId();
            boolean isCheck = selectedRoles != null && Arrays.asList(selectedRoles).contains(roleId.toString());

            Grant_access grantAccess = new Grant_access(accountId, roleId, isCheck, "");
            grantAccessRepository.updateIs_grant(grantAccess);
        }
    }

    private boolean handleAddAccount(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Account account = extractAccountFromRequest(req);
            String[] selectedRoles = req.getParameterValues("roles");

            addGrantAccess(account.getAccountId(), selectedRoles);

            if (!validateNewAccount(account)) {
                resp.getWriter().println("Account already exists");
                return false;
            }

            accountRepository.add(account);
            forwardToPage(req, resp, "dashboard.jsp");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private void addGrantAccess(Long accountId, String[] selectedRoles) throws Exception {
        List<Role> allRoles = roleRepository.findAll();

        for (Role role : allRoles) {
            Long roleId = role.getRoleId();
            boolean isCheck = Arrays.asList(selectedRoles).contains(roleId.toString());

            Grant_access grantAccess = new Grant_access(accountId, roleId, isCheck, "");
            grantAccessRepository.add(grantAccess);
        }
    }

    private boolean validateNewAccount(Account account) throws Exception {
        return accountRepository.findById(account.getAccountId()).isEmpty();
    }

    private boolean handleLogin(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String username = req.getParameter("username");
            String password = req.getParameter("password");

            Optional<Account> account = accountRepository.findByEmail(username);

            if (account.isEmpty()) {
                resp.getWriter().println("Wrong email");
                return false;
            }

            if (!validatePassword(password, account.get())) {
                resp.getWriter().println("Wrong password");
                return false;
            }

            Log log = new Log(account.get().getAccountId(), (java.sql.Date) new Date(System.currentTimeMillis()), null, "");
            logRepository.add(log);

            HttpSession session = req.getSession();
            session.setAttribute("account", account.get());
            forwardToPage(req, resp, "dashboard.jsp");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean validatePassword(String inputPassword, Account account) {
        return inputPassword.equals(account.getPassword());
    }

    private void forwardToPage(HttpServletRequest req, HttpServletResponse resp, String page) throws ServletException, IOException {
        RequestDispatcher rd = req.getRequestDispatcher(page);
        rd.forward(req, resp);
    }
}


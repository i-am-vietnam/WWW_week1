<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.thymeleaf.org">
<head>
    <title>Title</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-4bw+/aepP/YC94hEpVNVgiZdgIC5+VKNBQNGCHeKRQN+PtmoHDEXuppvnDJzQIu9" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-kenU1KFdBIe4zVF0s0G1M5b4hcpxyD9F7jL+jjXkk+Q2h455rYXK/7HAuoJl+0I4"
            crossorigin="anonymous"></script>
</head>
<body>

<!-- Add Account Modal -->
<div class="modal fade" id="addAccountModal" tabindex="-1" aria-labelledby="addAccountModalLabel" aria-hidden="true"
     th:attr="data-bs-target='#addAccountModal'">
    <div class="modal-dialog">
        <div class="modal-content">
            <form th:action="@{/ControlServlet}" method="post">
                <input type="hidden" name="action" value="add-account">
                <div class="modal-header">
                    <h1 class="modal-title fs-5" id="addAccountModalLabel">Add New Account</h1>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <div class="mb-3">
                        <label for="emailInput" class="form-label">Email address</label>
                        <input type="email" class="form-control" name="email" id="emailInput"
                               placeholder="name@example.com">
                        <label for="fullNameInput" class="form-label">FullName</label>
                        <input class="form-control" name="fullName" id="fullNameInput">
                        <label for="passwordInput" class="form-label">Password</label>
                        <input class="form-control" name="password" id="passwordInput">
                        <label for="phoneInput" class="form-label">Phone</label>
                        <input class="form-control" name="phone" id="phoneInput">
                        <div class="mb-3">
                            <label class="form-label">Status</label>
                            <div class="form-check">
                                <input class="form-check-input" type="radio" name="status" value="1" id="activeRadio"
                                       checked>
                                <label class="form-check-label" for="activeRadio">
                                    Active
                                </label>
                            </div>
                            <div class="form-check">
                                <input class="form-check-input" type="radio" name="status" value="0" id="deactiveRadio">
                                <label class="form-check-label" for="deactiveRadio">
                                    Inactive
                                </label>
                            </div>
                            <div class="form-check">
                                <input class="form-check-input" type="radio" name="status" value="-1" id="deletedRadio">
                                <label class="form-check-label" for="deletedRadio">
                                    Deleted
                                </label>
                            </div>
                        </div>
                        <div class="mb-3">
                            <label class="form-label">Roles</label>
                            <div th:each="role : ${allRoles}">
                                <div class="form-check">
                                    <input class="form-check-input" type="checkbox" name="roles"
                                           th:value="${role.getRole_id()}" id="role${role.getRole_id()}Checkbox">
                                    <label class="form-check-label" th:for="'role' + ${role.getRole_id()} + 'Checkbox'">
                                        <span th:text="${role.getRole_name()}"></span>
                                    </label>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                    <input type="submit" class="btn btn-primary" value="add account">
                </div>
            </form>
        </div>
    </div>
</div>

<!-- Update Account Modals -->
<table class="table">
    <thead>
    <th>#</th>
    <th>id</th>
    <th>fullName</th>
    <th>email</th>
    <th>phone</th>
    <th>status</th>
    <th>role</th>
    <th>action</th>
    </thead>
    <tbody>
    <tr th:each="account, stat : ${allAccount}">
        <td th:text="${stat.index + 1}"></td>
        <td th:text="${account.getAccount_id()}"></td>
        <td th:text="${account.getFullName()}"></td>
        <td th:text="${account.getEmail()}"></td>
        <td th:text="${account.getPhone()}"></td>
        <td th:text="${@getStatusString(account.getStatus())}"></td>
        <td th:text="${@getRoleNamesForAccount(account.getAccount_id())}"></td>
        <td>
            <button type="button" class="btn btn-primary" data-bs-toggle="modal"
                    th:data-bs-target="'#modal' + ${account.getAccount_id()}">
                update
            </button>
        </td>
        <!-- Update Account Modal -->
        <div class="modal fade" th:id="'modal' + ${account.getAccount_id()}" tabindex="-1"
             th:aria-labelledby="'modal' + ${account.getAccount_id()}" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <form th:action="@{/ControlServlet}" method="post">
                        <input type="hidden" name="action" value="update-account">
                        <input type="hidden" name="account_id" th:value="${account.getAccount_id()}">
                        <div class="modal-header">
                            <h5 class="modal-title" th:id="'modalLabel' + ${account.getAccount_id()}"
                                th:text="'Account Details - ' + ${account.getAccount_id()}"></h5>
                            <button type="button" class="btn-close" data-bs-dismiss="modal"
                                    aria-label="Close"></button>
                        </div>
                        <div class="modal-body">
                            <div class="mb-3">
                                <label for="idInput" class="form-label">ID</label>
                                <input class="form-control" id="idInput" name="account_id" type="text"
                                       th:value="${account.getAccount_id()}" readonly>
                            </div>
                            <div class="mb-3">
                                <label for="fullNameInput" class="form-label">Full Name</label>
                                <input class="form-control" id="fullNameInput" name="fullName" type="text"
                                       th:value="${account.getFullName()}">
                            </div>
                            <div class="mb-3">
                                <label for="emailInput" class="form-label">Email</label>
                                <input class="form-control" id="emailInput" name="email" type="email"
                                       th:value="${account.getEmail()}">
                            </div>
                            <div class="mb-3">
                                <label for="phoneInput" class="form-label">Phone</label>
                                <input class="form-control" id="phoneInput" name="phone" type="text"
                                       th:value="${account.getPhone()}">
                            </div>
                            <div class="mb-3">
                                <label for="statusInput" class="form-label">Status</label>
                                <select class="form-select" id="statusInput" name="status">
                                    <option value="1" th:selected="${account.getStatus() == 1}">Active</option>
                                    <option value="0" th:selected="${account.getStatus() == 0}">Inactive</option>
                                    <option value="-1" th:selected="${account.getStatus() == -1}">Deleted</option>
                                </select>
                            </div>
                            <div class="mb-3">
                                <label class="form-label">Roles</label>
                                <div th:each="role : ${allRoles}">
                                    <div class="form-check">
                                        <input class="form-check-input" type="checkbox" name="roles"
                                               th:value="${role.getRole_id()}" id="'role' + ${role.getRole_id()} + 'Checkbox'"
                                               th:checked="${account.hasRole(role.getRole_id())}">
                                        <label class="form-check-label" th:for="'role' + ${role.getRole_id()} + 'Checkbox'">
                                            <span th:text="${role.getRole_name()}"></span>
                                        </label>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                            <input type="submit" class="btn btn-primary" value="Save changes">
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </tr>
    </tbody>
</table>

<!-- Logout Form -->
<form action="ControlServlet" method="post">
    <input type="hidden" name="action" value="logout">
    <input type="submit" class="btn btn-danger" value="logout">
</form>

</body>
</html>

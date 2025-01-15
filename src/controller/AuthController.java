package controller;

import model.AuthModel;

public class AuthController {
    private final AuthModel authModel;

    public AuthController() {
        authModel = new AuthModel();
    }

    public boolean registerUser(String username, String password) {
        return authModel.register(username, password);
    }

    public boolean loginUser(String username, String password) {
        return authModel.login(username, password);
    }
}

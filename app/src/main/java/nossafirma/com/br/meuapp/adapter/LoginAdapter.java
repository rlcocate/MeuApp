package nossafirma.com.br.meuapp.adapter;

import java.util.List;

import nossafirma.com.br.meuapp.model.Login;

public class LoginAdapter {

    private Login _login;

    public LoginAdapter(Login login) {
        this._login = login;
    }

    public void update(Login login) {
        this._login = login;
    }
}
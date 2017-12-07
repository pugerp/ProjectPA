package com.lasemcode.app;

/**
 * Created by Z & N on 07/12/2017.
 */

public class Pengguna {
    String email, namaPengguna, password;

    public Pengguna() {
    }

    public Pengguna(String email, String namaPengguna, String password) {
        this.email = email;
        this.namaPengguna = namaPengguna;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNamaPengguna() {
        return namaPengguna;
    }

    public void setNamaPengguna(String namaPengguna) {
        this.namaPengguna = namaPengguna;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

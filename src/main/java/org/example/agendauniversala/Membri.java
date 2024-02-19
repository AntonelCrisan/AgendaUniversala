package org.example.agendauniversala;
 public class Membri{
    private final String numePrenume;
    private final String numeUtilizator;
    private final String email;
    private final String telefon;
    private final String rol;
    private final String status;

    public Membri(String numePrenume, String numeUtilizator, String email, String telefon, String rol, String status) {
        this.numePrenume = numePrenume;
        this.numeUtilizator = numeUtilizator;
        this.email = email;
        this.telefon = telefon;
        this.rol = rol;
        this.status = status;
    }

    public String getNumePrenume() {
        return numePrenume;
    }

    public String getNumeUtilizator() {
        return numeUtilizator;
    }

    public String getEmail() {
        return email;
    }

    public String getTelefon() {
        return telefon;
    }

    public String getRol() {
        return rol;
    }
    public String getStatus(){return status;}
}

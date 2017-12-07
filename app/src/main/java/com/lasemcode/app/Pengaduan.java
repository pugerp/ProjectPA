package com.lasemcode.app;

/**
 * Created by Z & N on 07/12/2017.
 */

public class Pengaduan {
    String foto, latitude, longtitude, alamat, keterangan, pengadu;

    public Pengaduan() {
    }

    public String getPengadu() {
        return pengadu;
    }

    public void setPengadu(String pengadu) {
        this.pengadu = pengadu;
    }

    public Pengaduan(String alamat, String foto, String keterangan, String latitude, String longtitude, String pengadu) {

        this.foto = foto;
        this.latitude = latitude;
        this.longtitude = longtitude;
        this.alamat = alamat;
        this.keterangan = keterangan;
        this.pengadu = pengadu;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongtitude() {
        return longtitude;
    }

    public void setLongtitude(String longtitude) {
        this.longtitude = longtitude;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }
}

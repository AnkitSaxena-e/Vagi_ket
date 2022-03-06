package com.example.euser.Modal;

public class OPro {

    private String PName, PNum, PCom, PPri, PQut, PImage, PPid;

    public OPro() {
    }

    public OPro(String PName, String PNum, String PCom, String PPri, String pQut, String pImage, String pPid) {
        this.PName = PName;
        this.PNum = PNum;
        this.PCom = PCom;
        this.PPri = PPri;
        PQut = pQut;
        PImage = pImage;
        PPid = pPid;
    }

    public String getPName() {
        return PName;
    }

    public String getPNum() {
        return PNum;
    }

    public String getPCom() {
        return PCom;
    }

    public String getPPri() {
        return PPri;
    }

    public String getPQut() {
        return PQut;
    }

    public String getPImage() {
        return PImage;
    }

    public String getPPid() {
        return PPid;
    }
}

package Tables;

import Viewers.PlayerView;

import java.util.List;

public class Players extends People{
    String position;
    float ppG;
    float apG;
    float rpG;
    float per;
    float spG;
    float bPg;

    public Players(){
    }

    public Players(String name, String team, String role, int age){
        super(name, team, role, age);
    }

    public Players(String name, String team, String role, String position, int age, float ppG, float apG, float rpG, float per, float spG, float bPg) {
        super(name, team, role, age);
        this.position = position;
        this.ppG = ppG;
        this.apG = apG;
        this.rpG = rpG;
        this.per = per;
        this.spG = spG;
        this.bPg = bPg;
    }

    public void setPosition(String position){
        this.position = position;
    }

    public void setPpG(float ppG) {
        this.ppG = ppG;
    }

    public void setApG(float apG) {
        this.apG = apG;
    }

    public void setRpG(float rpG) {
        this.rpG = rpG;
    }

    public void setPer(float per) {
        this.per = per;
    }

    public void setSpG(float spG) {
        this.spG = spG;
    }

    public void setbPg(float bPg) {
        this.bPg = bPg;
    }

    public String getPosition() {
        return position;
    }

    public float getPpG() {
        return ppG;
    }

    public float getApG() {
        return apG;
    }

    public float getRpG() {
        return rpG;
    }

    public float getPer() {
        return per;
    }

    public float getSpG() {
        return spG;
    }

    public float getbPg() {
        return bPg;
    }

    public String toString1() {
        return name + "    " + team + "    " + age + "    " + position + System.lineSeparator();
    }

    public String toString2(){
        return name + "    " + ppG + "    " + apG + "    " + rpG + System.lineSeparator();
    }

    public String toString3(){
        return name + "    " + per + "    " + spG + "    " + bPg + System.lineSeparator();
    }

    public String toString4(){
        return name + "    " + ppG + "    " + apG + "    " + rpG + "    " + per + "    " + spG + "    " + bPg + System.lineSeparator();
    }
}

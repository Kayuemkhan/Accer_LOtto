package com.morangesoft.lotteryapp;

public class DatasetFireabase {
    public  String lotterynum;
    public  String pr;
    public  String numero;
    public  String valor;

    public DatasetFireabase() {
    }

    public DatasetFireabase(String lotterynum, String pr, String numero, String valor) {
        this.lotterynum = lotterynum;
        this.pr = pr;
        this.numero = numero;
        this.valor = valor;
    }

    public String getLotterynum() {
        return lotterynum;
    }

    public void setLotterynum(String lotterynum) {
        this.lotterynum = lotterynum;
    }

    public String getPr() {
        return pr;
    }

    public void setPr(String pr) {
        this.pr = pr;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }
}

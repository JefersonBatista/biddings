package com.github.jb.biddings.entities;

public class Bidding {
  private static int currentId = 0;

  private final int id;
  private final String dataAbertura;
  private final String orgao;
  private final String numeroProcesso;
  private final String numeroEdital;
  private final String objeto;
  private final String modalidade;
  private boolean lida = false;

  public Bidding(String dataAbertura, String orgao, String numeroProcesso, String numeroEdital, String objeto,
      String modalidade) {
    this.id = ++Bidding.currentId;
    this.dataAbertura = dataAbertura;
    this.orgao = orgao;
    this.numeroProcesso = numeroProcesso;
    this.numeroEdital = numeroEdital;
    this.objeto = objeto;
    this.modalidade = modalidade;
  }

  public int getId() {
    return id;
  }

  public String getDataAbertura() {
    return dataAbertura;
  }

  public String getOrgao() {
    return orgao;
  }

  public String getNumeroProcesso() {
    return numeroProcesso;
  }

  public String getNumeroEdital() {
    return numeroEdital;
  }

  public String getObjeto() {
    return objeto;
  }

  public String getModalidade() {
    return modalidade;
  }

  public boolean isLida() {
    return lida;
  }

  public void setLida(boolean lida) {
    this.lida = lida;
  }
}

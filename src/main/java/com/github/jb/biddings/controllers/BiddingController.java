package com.github.jb.biddings.controllers;

import java.io.IOException;
import java.util.Arrays;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.jb.biddings.entities.Bidding;

@RestController
public class BiddingController {
  private final String website = "https://www.siga.es.gov.br/sgc/faces/pub/sgc/central/EditalPageList.jsp";
  private final String tableId = "form_EditalPageList:listaDataTable:tbody_element";
  private final String tableCellIdFormat = "form_EditalPageList:listaDataTable:%d:lb%s";
  private final String[] columnNames = { "DataAbertura", "Orgao", "NumeroProcesso", "NumeroEdital", "Objeto",
      "Modalidade" };

  @GetMapping("/biddings")
  public String showBiddings() {
    Document doc;
    try {
      doc = Jsoup.connect(website).get();
    } catch (IOException e) {
      return "Site is down!";
    }

    Element biddingTable = doc.getElementById(tableId);

    Bidding[] biddings = getBiddings(biddingTable);

    ObjectMapper objectMapper = new ObjectMapper();
    try {
      return objectMapper.writeValueAsString(Arrays.asList(biddings));
    } catch (JsonProcessingException e) {
      return "Erro ao processar a resposta!";
    }
  }

  private String getCellData(Element biddingTable, int lineNumber, String columnName) {
    String cellId = String.format(tableCellIdFormat, lineNumber, columnName);
    return biddingTable.getElementById(cellId).text();
  }

  private Bidding[] getBiddings(Element biddingTable) {
    int numBiddings = 10;
    Bidding[] biddings = new Bidding[numBiddings];

    for (int i = 0; i < numBiddings; i++) {
      String dataAbertura = getCellData(biddingTable, i, columnNames[0]);
      String orgao = getCellData(biddingTable, i, columnNames[1]);
      String numeroProcesso = getCellData(biddingTable, i, columnNames[2]);
      String numeroEdital = getCellData(biddingTable, i, columnNames[3]);
      String objeto = getCellData(biddingTable, i, columnNames[4]);
      String modalidade = getCellData(biddingTable, i, columnNames[5]);

      biddings[i] = new Bidding(dataAbertura, orgao, numeroProcesso, numeroEdital, objeto, modalidade);
    }

    return biddings;
  }
}

package com.github.jb.biddings.controllers;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BiddingController {
  private final String website = "https://www.siga.es.gov.br/sgc/faces/pub/sgc/central/EditalPageList.jsp";
  private final String tableId = "form_EditalPageList:listaDataTable:tbody_element";
  private final String tableCellIdFormat = "form_EditalPageList:listaDataTable:%d:lb%s";

  @GetMapping("/biddings")
  public String showBiddings() {
    Document doc;
    try {
      doc = Jsoup.connect(website).get();
    } catch (IOException e) {
      return "Site is down!";
    }

    Element biddingTable = doc.getElementById(tableId);

    String result = "";

    result += getColumnLines(biddingTable, "DataAbertura");
    result += getColumnLines(biddingTable, "Orgao");
    result += getColumnLines(biddingTable, "NumeroProcesso");
    result += getColumnLines(biddingTable, "NumeroEdital");
    result += getColumnLines(biddingTable, "Objeto");
    result += getColumnLines(biddingTable, "Modalidade");

    return result;
  }

  private String getColumnLines(Element biddingTable, String columnName) {
    String result = String.format("Coluna: %s\n", columnName);

    for (int i = 0; i < 10; i++) {
      String cellId = String.format(tableCellIdFormat, i, columnName);
      result += biddingTable.getElementById(cellId).text() + "\n";
    }

    return result + "\n";
  }
}

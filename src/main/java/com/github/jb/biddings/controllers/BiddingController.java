package com.github.jb.biddings.controllers;

import java.io.IOException;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BiddingController {
  @GetMapping("/biddings")
  public String showBiddings() {
    String url = "https://www.siga.es.gov.br/sgc/faces/pub/sgc/central/EditalPageList.jsp";

    Document doc;
    try {
      doc = Jsoup.connect(url).get();
    } catch (IOException e) {
      return "Site is down!";
    }

    String biddingTableId = "form_EditalPageList:listaDataTable:tbody_element";
    Element biddingTable = doc.getElementById(biddingTableId);

    String processNumbers = "";
    for (int i = 0; i < 10; i++) {
      String processNumberIdFormat = "form_EditalPageList:listaDataTable:%d:lbNumeroProcesso";
      String processNumberId = String.format(processNumberIdFormat, i);
      processNumbers += biddingTable.getElementById(processNumberId).text() + "\n";
    }
    return processNumbers;
  }
}

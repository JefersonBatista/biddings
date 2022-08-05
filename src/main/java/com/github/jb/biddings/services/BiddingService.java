package com.github.jb.biddings.services;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.github.jb.biddings.entities.Bidding;
import com.github.jb.biddings.repositories.BiddingRepository;

public class BiddingService {
  private final static String website = "https://www.siga.es.gov.br/sgc/faces/pub/sgc/central/EditalPageList.jsp";
  private final static String tableId = "form_EditalPageList:listaDataTable:tbody_element";
  private final static String tableCellIdFormat = "form_EditalPageList:listaDataTable:%d:lb%s";
  private final static String[] columnNames = { "DataAbertura", "Orgao", "NumeroProcesso", "NumeroEdital", "Objeto",
      "Modalidade" };

  public static Bidding[] getBiddings() {
    Bidding[] biddings = BiddingRepository.getBiddings();
    if (biddings != null) {
      return biddings;
    }

    String firefoxDriverPath = "drivers/geckodriver";
    System.setProperty("webdriver.gecko.driver", firefoxDriverPath);

    WebDriver driver = new FirefoxDriver();
    driver.get(website);
    String pageSource = driver.getPageSource();

    Document doc = Jsoup.parse(pageSource);

    Element biddingTable = doc.getElementById(tableId);

    biddings = getBiddingsData(biddingTable);
    BiddingRepository.setBiddings(biddings);
    return biddings;
  }

  private static String getCellData(Element biddingTable, int lineNumber, String columnName) {
    String cellId = String.format(tableCellIdFormat, lineNumber, columnName);
    return biddingTable.getElementById(cellId).text();
  }

  private static Bidding[] getBiddingsData(Element biddingTable) {
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

  public static void markAsRead(int id) {
    BiddingRepository.markAsRead(id);
  }

  public static void markAsUnread(int id) {
    BiddingRepository.markAsUnread(id);
  }
}

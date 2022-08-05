package com.github.jb.biddings.services;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.github.jb.biddings.entities.Bidding;
import com.github.jb.biddings.repositories.BiddingRepository;

public class BiddingService {
  private final static String website = "https://www.siga.es.gov.br/sgc/faces/pub/sgc/central/EditalPageList.jsp";
  private final static String tableId = "form_EditalPageList:listaDataTable:tbody_element";
  private final static String tableCellIdFormat = "form_EditalPageList:listaDataTable:%d:lb%s";
  private final static String[] columnNames = { "DataAbertura", "Orgao", "NumeroProcesso", "NumeroEdital", "Objeto",
      "Modalidade" };
  private final static String buttonNextId = "form_EditalPageList:listaDataTable:dataScrollernext";

  public static List<Bidding> getBiddings() {
    List<Bidding> biddings = BiddingRepository.getBiddings();
    if (!biddings.isEmpty()) {
      return biddings;
    }

    String firefoxDriverPath = "drivers/geckodriver";
    System.setProperty("webdriver.gecko.driver", firefoxDriverPath);

    WebDriver driver = new FirefoxDriver();
    driver.get(website);

    String pageSource = driver.getPageSource();
    Document doc = Jsoup.parse(pageSource);
    Element biddingTable = doc.getElementById(tableId);
    List<Bidding> pageBiddings = getBiddingsData(biddingTable);
    BiddingRepository.addBiddings(pageBiddings);

    WebElement buttonNext = driver.findElement(By.id(buttonNextId));
    buttonNext.click();

    pageSource = driver.getPageSource();
    doc = Jsoup.parse(pageSource);
    biddingTable = doc.getElementById(tableId);
    pageBiddings = getBiddingsData(biddingTable);
    BiddingRepository.addBiddings(pageBiddings);

    biddings = BiddingRepository.getBiddings();
    return biddings;
  }

  private static String getCellData(Element biddingTable, int lineIndex, String columnName) {
    String cellId = String.format(tableCellIdFormat, lineIndex, columnName);
    return biddingTable.getElementById(cellId).text();
  }

  private static List<Bidding> getBiddingsData(Element biddingTable) {
    List<Bidding> biddings = new ArrayList<>();

    int numLines = biddingTable.childrenSize();
    IntStream.range(0, numLines).forEach(index -> {
      String dataAbertura = getCellData(biddingTable, index, columnNames[0]);
      String orgao = getCellData(biddingTable, index, columnNames[1]);
      String numeroProcesso = getCellData(biddingTable, index, columnNames[2]);
      String numeroEdital = getCellData(biddingTable, index, columnNames[3]);
      String objeto = getCellData(biddingTable, index, columnNames[4]);
      String modalidade = getCellData(biddingTable, index, columnNames[5]);

      biddings.add(new Bidding(dataAbertura, orgao, numeroProcesso, numeroEdital, objeto, modalidade));
    });

    return biddings;
  }

  public static void markAsRead(int id) {
    BiddingRepository.markAsRead(id);
  }

  public static void markAsUnread(int id) {
    BiddingRepository.markAsUnread(id);
  }
}

package com.github.jb.biddings.controllers;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.jsoup.Jsoup;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BiddingController {
  @GetMapping("/biddings")
  public String showBiddings() {
    String url = "https://www.siga.es.gov.br/sgc/faces/pub/sgc/central/EditalPageList.jsp";

    String html;
    try {
      html = Jsoup.connect(url).get().html();
    } catch (IOException e) {
      return "Site is down!";
    }
    return html;
  }
}

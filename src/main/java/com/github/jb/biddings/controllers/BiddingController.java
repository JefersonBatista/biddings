package com.github.jb.biddings.controllers;

import java.util.Arrays;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.jb.biddings.entities.Bidding;
import com.github.jb.biddings.services.BiddingService;

@RestController
public class BiddingController {
  @GetMapping("/biddings")
  public static String showBiddings() {
    Bidding[] biddings = BiddingService.getBiddings();

    ObjectMapper objectMapper = new ObjectMapper();
    try {
      return objectMapper.writeValueAsString(Arrays.asList(biddings));
    } catch (JsonProcessingException e) {
      return "Erro ao processar a resposta!";
    }
  }
}

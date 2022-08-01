package com.github.jb.biddings.controllers;

import java.util.Arrays;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

  @PatchMapping("/biddings/{id}/read")
  public static void markAsRead(@PathVariable int id) {
    BiddingService.markAsRead(id);
  }

  @PatchMapping("/biddings/{id}/unread")
  public static void markAsUnread(@PathVariable int id) {
    BiddingService.markAsUnread(id);
  }

}

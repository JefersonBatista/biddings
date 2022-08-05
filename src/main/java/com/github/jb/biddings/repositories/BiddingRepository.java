package com.github.jb.biddings.repositories;

import java.util.ArrayList;
import java.util.List;

import com.github.jb.biddings.entities.Bidding;

public class BiddingRepository {
  private static List<Bidding> biddings = new ArrayList<>();

  public static List<Bidding> getBiddings() {
    return biddings;
  }

  public static void setBiddings(List<Bidding> biddings) {
    BiddingRepository.biddings = biddings;
  }

  public static void addBiddings(List<Bidding> biddings) {
    BiddingRepository.biddings.addAll(biddings);
  }

  public static void markAsRead(int id) {
    if (id <= biddings.size()) {
      biddings.get(id - 1).setLida(true);
    }
  }

  public static void markAsUnread(int id) {
    if (id <= biddings.size()) {
      biddings.get(id - 1).setLida(false);
    }
  }
}

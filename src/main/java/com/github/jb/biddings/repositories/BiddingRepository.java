package com.github.jb.biddings.repositories;

import com.github.jb.biddings.entities.Bidding;

public class BiddingRepository {
  private static Bidding[] biddings = null;

  public static Bidding[] getBiddings() {
    return biddings;
  }

  public static void setBiddings(Bidding[] biddings) {
    BiddingRepository.biddings = biddings;
  }

  public static void markAsRead(int id) {
    if (id <= biddings.length) {
      biddings[id - 1].setLida(true);
    }
  }

  public static void markAsUnread(int id) {
    if (id <= biddings.length) {
      biddings[id - 1].setLida(false);
    }
  }
}

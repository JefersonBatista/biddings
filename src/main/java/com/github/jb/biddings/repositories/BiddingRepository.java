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
}

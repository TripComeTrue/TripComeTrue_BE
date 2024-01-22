package com.haejwo.tripcometrue.domain.member.entity.tripLevel;

public enum TripLevel {
  BEGINNER(0, 49),
  RUNNER(50, 149),
  TRAVELER(150, Integer.MAX_VALUE);

  private final int min;
  private final int max;

  TripLevel(int min, int max) {
    this.min = min;
    this.max = max;
  }

  public static TripLevel getLevelByPoint(int totalPoints) {
    for (TripLevel level : TripLevel.values()) {
      if (totalPoints >= level.min && totalPoints <= level.max) {
        return level;
      }
    }
    return TRAVELER;
  }
}

package com.haejwo.tripcometrue.domain.member.entity.rating;

public enum MilkLevel {
  WHITE_MILK(0, 49),
  STRAWBERRY_MILK(50, 149),
  CHOCO_MILK(150, Integer.MAX_VALUE);

  private final int min;
  private final int max;

  MilkLevel(int min, int max) {
    this.min = min;
    this.max = max;
  }

  public static MilkLevel getLevelByPoint(int totalPoints) {
    for (MilkLevel level : MilkLevel.values()) {
      if (totalPoints >= level.min && totalPoints <= level.max) {
        return level;
      }
    }
    return CHOCO_MILK;
  }
}

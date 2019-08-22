package com.huobi.client.enums;

import com.huobi.client.utils.EnumLookup;


/**
 * withdraw, deposit.
 */
public enum DepositState {

  UNKNOWN("unknown"),
  CONFIRMING("confirming"),
  SAFE("safe"),
  CONFIRMED("confirmed"),
  ORPHAN("orphan");


  private final String code;

  DepositState(String code) {
    this.code = code;
  }

  @Override
  public String toString() {
    return code;
  }

  private static final EnumLookup<DepositState> lookup = new EnumLookup<>(DepositState.class);

  public static DepositState lookup(String name) {
    return lookup.lookup(name);
  }

}
package com.svitix.hw2020;

public class MyAtm implements BaseAtm {
    private final CassetesHolder cassetesHolder;

    public MyAtm(CassetesHolder cassetesHolder) {
        this.cassetesHolder = cassetesHolder;
    }

    @Override
    public void giveOutMoney() {

    }

    @Override
    public void getBalance() {
        cassetesHolder.getTotalMoney();
    }
}

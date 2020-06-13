package com.example.redis.pojo;

public class BankAccount {
    private String name;
    private String creditCode;
    private Long deposit;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreditCode() {
        return creditCode;
    }

    public void setCreditCode(String creditCode) {
        this.creditCode = creditCode;
    }

    public Long getDeposit() {
        return deposit;
    }

    public void setDeposit(Long deposit) {
        this.deposit = deposit;
    }

    @Override
    public String toString() {
        return "BankAccount{" +
                "name='" + name + '\'' +
                ", creditCode='" + creditCode + '\'' +
                ", deposit=" + deposit +
                '}';
    }
}

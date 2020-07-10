package com.vinspier.demo.mvc.Service;

public interface OrderService {
    void selectOrderNeverTransaction();
    void doTransactionThrewUnChecked();
    void doTransactionThrewChecked();
}

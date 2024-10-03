package com.technicaltest.services;

import com.technicaltest.exceptions.BusinessException;

public interface AccountService {
    void deposit(int amount) throws BusinessException;
    void withdraw(int amount) throws BusinessException;
    void printStatement();

}

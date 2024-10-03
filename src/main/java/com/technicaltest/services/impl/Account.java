package com.technicaltest.services.impl;

import com.technicaltest.entities.Operation;
import com.technicaltest.exceptions.BusinessException;
import com.technicaltest.services.AccountService;

import java.time.LocalDate;
import java.util.Stack;

public class Account implements AccountService {
    private Stack<Operation> operations=new Stack<>();


    @Override
    public void deposit(int amount) throws BusinessException {
        if(amount<=0) throw new BusinessException("The amount should be positive");
        Operation lastOperation=operations.isEmpty() ? null:operations.peek();
        int balance=lastOperation==null ? 0 :lastOperation.balance();
        operations.push(new Operation(LocalDate.now(),amount,balance+amount));
    }

    @Override
    public void withdraw(int amount) throws BusinessException {
        if(amount<=0) throw new BusinessException("The amount should be positive");
        Operation lastOperation=operations.isEmpty() ? null:operations.peek();
        int balance=lastOperation==null ? 0 :lastOperation.balance();
        if(amount>balance) throw new BusinessException("Insufficient ...");
        operations.push(new Operation(LocalDate.now(),(-1)*amount,balance-amount));

    }

    @Override
    public void printStatement() {
        Stack<Operation> ops= (Stack<Operation>) this.operations.clone();
        String message = "    Date    || Amount|| Balance\n";
        while(!ops.isEmpty()) {
            Operation operation=ops.pop();
            message=message.concat(operation.operationDate() + "  || " + operation.amount() + "  || " + operation.balance()+"\n");
        }
        System.out.println(message);
        MessageQueue.getMessages().add(message);

    }

    public Stack<Operation> getOperations() {
        return operations;
    }

}

package AccountServiceTest;

import com.technicaltest.entities.Operation;
import com.technicaltest.exceptions.BusinessException;
import com.technicaltest.services.impl.Account;
import com.technicaltest.services.impl.MessageQueue;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.LinkedList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

public class AccountServiceTest {
    private Account accountService;
    @Before
    public void setUp(){
        this.accountService=new Account();
    }


    @Test
    public void givenAClientMakesADepositThenAnOperationShouldAdded() throws BusinessException {
        accountService.deposit(1000);
        LinkedList<Operation> operations= new LinkedList<>();
        operations.add(new Operation(LocalDate.now(),1000,1000));
        assertEquals(operations,this.accountService.getOperations());
    }


    @Test
    public void givenAClientMakesAWithdrawWhenThenAnOperationShouldAdded() throws BusinessException {
        accountService.deposit(1000);
        accountService.withdraw(450);
        LinkedList<Operation> operations= new LinkedList<>();
        operations.add(new Operation(LocalDate.now(),1000,1000));
        operations.add(new Operation(LocalDate.now(),-450,550));
        assertEquals(operations,this.accountService.getOperations());
    }


    @Test
    public void givenAClientMakesAWithdrawWhenWithAnAmountGreaterThanBalanceThenAnExceptionShouldBeThrown() throws BusinessException {
        accountService.deposit(1000);
        accountService.withdraw(450);
        assertThrows(BusinessException.class,()->this.accountService.withdraw(1000));
    }


    @Test
    public void testDepositWithANegativeAmount(){
        assertThrows(BusinessException.class,()->this.accountService.withdraw(-100));
    }


    @Test
    public void testWithDrawWithANegativeAmount(){
        assertThrows(BusinessException.class,()->this.accountService.deposit(-100));

    }


    @Test
    public void givenAClientMakesOperationWhenPrintThenSeeOperations() throws BusinessException {
        this.accountService.deposit(1000);
        this.accountService.deposit(2000);
        this.accountService.withdraw(500);

        this.accountService.printStatement();
        assertEquals(""" 
                     Date    || Amount|| Balance
                 2024-10-03  || -500  || 2500
                 2024-10-03  || 2000  || 3000
                 2024-10-03  || 1000  || 1000
                 """, MessageQueue.getMessages().peek());

    }
}

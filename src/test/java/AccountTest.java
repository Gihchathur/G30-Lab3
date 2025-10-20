import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;


class AccountTest {

    @Test
    void testGetMaxOverdrawn() {
        Account myTestAccount = new Account(BigDecimal.ZERO, "SEK", BigDecimal.ZERO);
        assertEquals(BigDecimal.ZERO, myTestAccount.getMaxOverdrawn());

        Account myTestAccount2 = new Account(BigDecimal.ZERO, "SEK", new BigDecimal(-1));
        assertEquals(BigDecimal.ZERO, myTestAccount2.getMaxOverdrawn()); //max_overdrawn must be non-negative

        Account myTestAccount3 = new Account(BigDecimal.ZERO, "SEK", new BigDecimal(1000));
        assertEquals(new BigDecimal(1000), myTestAccount3.getMaxOverdrawn());
    }

    @Test
    void testSetMaxOverdrawn() {
        Account myTestAccount = new Account(BigDecimal.ZERO, "SEK", BigDecimal.ZERO);

        myTestAccount.setMaxOverdrawn(new BigDecimal(-1));
        assertEquals(BigDecimal.ZERO, myTestAccount.getMaxOverdrawn()); //max_overdrawn must be non-negative

        myTestAccount.setMaxOverdrawn(new BigDecimal(100));
        assertEquals(new BigDecimal(100), myTestAccount.getMaxOverdrawn()); //max_overdrawn must be non-negative
    }

    @Test
    void testGetCurrency() {
        Account myTestAccount = new Account(BigDecimal.ZERO,  "SEK", BigDecimal.ZERO);
        assertEquals("SEK", myTestAccount.getCurrency());

        myTestAccount = new Account(BigDecimal.ZERO,  "EUR", BigDecimal.ZERO);
        assertEquals("EUR", myTestAccount.getCurrency());

        myTestAccount = new Account(BigDecimal.ZERO,  "USD", BigDecimal.ZERO);
        assertEquals("USD", myTestAccount.getCurrency());
    }

    @Test
    void testSetCurrency() {
        Account myTestAccount = new Account(BigDecimal.ZERO, "SEK", BigDecimal.ZERO);
        myTestAccount.setCurrency("EUR");
        assertEquals("EUR", myTestAccount.getCurrency());

        myTestAccount.setCurrency("SEK");
        assertEquals("SEK", myTestAccount.getCurrency());
    }

    @Test
    void testGetBalance() {
        Account myTestAccount = new Account(BigDecimal.ZERO, "SEK", BigDecimal.ZERO);
        assertEquals(BigDecimal.ZERO, myTestAccount.getBalance());

        myTestAccount = new Account(new BigDecimal(100), "SEK", BigDecimal.ZERO);
        assertEquals(new BigDecimal(100), myTestAccount.getBalance());
    }

    @Test
    void testSetBalance() {
        Account myTestAccount = new Account(BigDecimal.ZERO, "SEK", BigDecimal.ONE);

        //should not be allowed to set balance to lower that -1 * maxOverdrawn
        myTestAccount.setBalance(new BigDecimal(-2));
        assertEquals(BigDecimal.ZERO, myTestAccount.getBalance());

        myTestAccount.setBalance(new BigDecimal(42));
        assertEquals(new BigDecimal(42), myTestAccount.getBalance());
    }

     @Test
     void testWithdraw() {
         /*
          * TestCase 01 — Withdraw within available balance (no overdraft)
          * Desired behaviour: Withdrawing 10 from balance of 100 (with max_overdrawn 0) should success and set new balance to 90.
          * Observed skeleton behaviour: withdraw method returns (balance - requestedAmount), but does not update the internal balance.
          * Purpose of this testcase: A basic test to check that verify basic subtraction and state update, without overdraft.
          * Fix: after the withdraw method, this.balance should update to the new balance
          */
         Account myTestAccount1 = new Account(BigDecimal.ZERO, "SEK", BigDecimal.ZERO);
         myTestAccount1.setBalance(new BigDecimal(100));
         BigDecimal returnedBalance1 = myTestAccount1.withdraw(new BigDecimal(10));
         assertEquals(new BigDecimal(90), returnedBalance1);
         assertEquals(new BigDecimal(90), myTestAccount1.getBalance());

         /*
          * TestCase 02 — Withdraw exactly to overdraft amount
          * Desired behaviour: balance 100, max_overdrawn 100, withdraw 200 should success and new balance should -100.
          * Skeleton: no overdraft limitations and does not update the internal balance.
          * Reason: Boundary check for withdraw amount (-max_overdrawn).
          * Fix: if newBalance >= -max_overdrawn then this.balance set to the new balance
          */
         Account myTestAccount2 = new Account(BigDecimal.ZERO, "SEK", BigDecimal.ZERO);
         myTestAccount2.setBalance(new BigDecimal(100));
         myTestAccount2.setMaxOverdrawn(new BigDecimal(100));
         BigDecimal returnedBalance2 = myTestAccount2.withdraw(new BigDecimal(200));
         assertEquals(new BigDecimal(-100), returnedBalance2);
         assertEquals(new BigDecimal(-100), myTestAccount2.getBalance());


         /*
          * TestCase 03 — Withdraw more than overdraft amount(should be rejected)
          * Desired behaviour: balance 100, max_overdrawn 100, withdraw 201 should reject and balance stays 100. withdraw method returns unchanged balance.
          * Skeleton: returns -201 (illegal) and still not updating state.
          * Reason: to avoid minimal balance violation.
          * Fix: If newBalance < -max_overdrawn, This withdraw should block and return current balance.
          */
         Account myTestAccount3 = new Account(BigDecimal.ZERO, "SEK", BigDecimal.ZERO);
         myTestAccount3.setBalance(new BigDecimal(100));
         myTestAccount3.setMaxOverdrawn(new BigDecimal(100));
         BigDecimal returnedBalance3 = myTestAccount3.withdraw(new BigDecimal(201));
         assertEquals(new BigDecimal(100), returnedBalance3);
         assertEquals(new BigDecimal(100), myTestAccount3.getBalance());

     }

    // @Test
    // void testDeposit() {
    //     fail("Not yet implemented"); //TODO implement
    // }

    // @Test
    // void testConvertToCurrency() {
    //     fail("Not yet implemented"); //TODO implement
    // }

    // @Test
    // void testTransferToAccount() {
    //     fail("Not yet implemented"); //TODO implement
    // }

    // @Test
    // void testWithdrawAll() {
    //     fail("Not yet implemented"); //TODO implement
    // }
}

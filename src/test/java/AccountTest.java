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
        fail("Not yet imnplemented"); //TODO implement
    }

    @Test
    void testDeposit() {
        fail("Not yet implemented"); //TODO implement
    }

    @Test
    void testConvertToCurrency() {
        fail("Not yet implemented"); //TODO implement
    }

    @Test
    void testTransferToAccount() {
        fail("Not yet implemented"); //TODO implement
    }

    @Test
    void testWithdrawAll() {
        fail("Not yet implemented"); //TODO implement
    }
}

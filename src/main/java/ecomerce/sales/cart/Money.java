package ecomerce.sales.cart;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class Money {

    long amount;

    private Money(long amount) {
        this.amount = amount;
    }

    public static Money zero() {
        return new Money(0L);
    }

    public Money multiply(int scalar) {
        return new Money(amount * scalar);
    }

    public Money add(Money money) {
        return new Money(amount + money.amount);
    }
}

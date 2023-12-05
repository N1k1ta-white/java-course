package bg.sofia.uni.fmi.mjt.udemy.account;

import bg.sofia.uni.fmi.mjt.udemy.account.type.AccountType;

public class StandardAccount extends AccountBase {
    public StandardAccount(String username, double budget) {
        super(username, budget);
        type = AccountType.STANDARD;
    }
}

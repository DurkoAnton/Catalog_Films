package DataStructure;

import DataStructure.CustomerAccount;

import java.io.Serializable;
import java.util.ArrayList;

public class AccountsDataBase implements Serializable {

    private static ArrayList<CustomerAccount> customerAccountsDataBase;

    public AccountsDataBase() {
        customerAccountsDataBase = new ArrayList<>();
    }

    public boolean addNewUser(CustomerAccount user) {
        if (!checkIsAvailableThisLogin(user)) {
            customerAccountsDataBase.add(user);
            System.out.println(customerAccountsDataBase.size());
            return true;
        } else
            return false;
    }

    public ArrayList<CustomerAccount> getCustomerAccountsDataBase() {
        return customerAccountsDataBase;
    }

    public void setCustomerAccountsDataBase(ArrayList<CustomerAccount> customerAccountsDataBase) {
        this.customerAccountsDataBase = customerAccountsDataBase;
    }

    public boolean checkIsAvailableThisLogin(CustomerAccount user) {
        for (int i = 0; i < customerAccountsDataBase.size(); i++)
            if (customerAccountsDataBase.get(i).getUserLogin().equals(user.getUserLogin()))
                return true;
        return false;
    }

    public boolean checkIsAvailableThisUser(CustomerAccount user) {
        for (int i = 0; i < customerAccountsDataBase.size(); i++)
            if (customerAccountsDataBase.get(i).getUserLogin().equals(user.getUserLogin()))
                return true;
        return false;
    }

}

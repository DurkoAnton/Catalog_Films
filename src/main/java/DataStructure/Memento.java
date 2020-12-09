package DataStructure;

import java.util.ArrayList;

public class Memento {

    private CustomerAccount oldState;

    public Memento() {
        oldState = new CustomerAccount();
    }

  /*  public Memento(CustomerAccount newState){
        this.oldState = newState;
    }*/

    public void setState(CustomerAccount newState) {
        this.oldState.setUserLogin(newState.getUserLogin());
        this.oldState.setUserPassword(newState.getUserPassword());
        this.oldState.setCustomerMoviesList(newState.getCustomerMoviesList());
        this.oldState.setWatchedCustomerMoviesList(newState.getWatchedCustomerMoviesList());
    }

    public CustomerAccount getState() {
        return this.oldState;
    }
}

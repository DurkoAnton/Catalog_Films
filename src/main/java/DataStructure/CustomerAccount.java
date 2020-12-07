package DataStructure;

import com.truedev.kinoposk.api.model.film.FilmExt;

import java.util.ArrayList;

public class CustomerAccount {

    private String login;
    private String password;
    private ArrayList<Integer> customerMoviesList;
    private boolean authorizeStatus;

    public CustomerAccount() {
    }

    public CustomerAccount(String login, String password) {
        authorizeStatus = false;
        this.login = login;
        this.password = password;
        customerMoviesList = new ArrayList<>();
    }

    public ArrayList<Integer> getCustomerMoviesList(){
        return customerMoviesList;
    }
    public void setUserLogin(String userLogin) {

    }

    public void setPasswordLogin(String userPasssword) {

    }

    public String getUserLogin() {
        return this.login;
    }

    public String getPasswordLogin() {
        return this.password;
    }
}

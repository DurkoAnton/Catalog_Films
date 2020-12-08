package DataStructure;

import com.sun.prism.Image;
import com.truedev.kinoposk.api.model.film.FilmExt;

import java.io.Serializable;
import java.util.ArrayList;

public class CustomerAccount implements Serializable {

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

    public ArrayList<Integer> getCustomerMoviesList() {
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

    public int getIndexMovieInUserCatalog(int movieId) {
        for (int i = 0; i < customerMoviesList.size(); i++)
            if (customerMoviesList.get(i) == movieId)
                return i;
        return -1;
    }
}

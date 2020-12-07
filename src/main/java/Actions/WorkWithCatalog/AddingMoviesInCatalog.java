package Actions.WorkWithCatalog;

import DataStructure.CustomerAccount;

import java.util.ArrayList;

public class AddingMoviesInCatalog {

    public AddingMoviesInCatalog() {
    }

    public void addNewMovieInCatalog(CustomerAccount customerAccount, int newMovieId) {
        customerAccount.getCustomerMoviesList();
        if (!isAvailableThisMovie(customerAccount.getCustomerMoviesList(), newMovieId)) {
            customerAccount.getCustomerMoviesList().add(newMovieId);
           // System.out.println("yes");
        }
    }

    public boolean isAvailableThisMovie(ArrayList<Integer> movieList, int newMovieId) {
        for (int i = 0; i < movieList.size(); i++)
            if (movieList.get(i).equals(newMovieId))
                return true;
        return false;
    }
}

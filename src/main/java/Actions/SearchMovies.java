package Actions;

import com.truedev.kinoposk.api.model.film.FilmExt;
import com.truedev.kinoposk.api.model.navigator.NavigatorExt;
import com.truedev.kinoposk.api.model.navigator.filter.Order;
import com.truedev.kinoposk.api.model.staff.StaffItem;
import com.truedev.kinoposk.api.service.KinopoiskApiService;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class SearchMovies {

    private String keywords;
    private int genreId;
    private int countryId;
    private byte ratingFrom;
    private byte ratingTo;

    public int getYearFrom() {
        return yearFrom;
    }

    public void setYearFrom(int yearFrom) {
        this.yearFrom = yearFrom;
    }

    public int getYearTo() {
        return yearTo;
    }

    public void setYearTo(int yearTo) {
        this.yearTo = yearTo;
    }

    private int yearFrom;
    private int yearTo;

    public SearchMovies() {
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public int getGenreId() {
        return genreId;
    }

    public void setGenreId(int genreId) {
        this.genreId = genreId;
    }

    public int getCountryId() {
        return countryId;
    }

    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    public byte getRatingFrom() {
        return ratingFrom;
    }

    public void setRatingFrom(byte ratingFrom) {
        this.ratingFrom = ratingFrom;
    }

    public byte getRatingTo() {
        return ratingTo;
    }

    public void setRatingTo(byte ratingTo) {
        this.ratingTo = ratingTo;
    }

    public NavigatorExt searchMovies(int pageCount) {

        ArrayList<Integer> countriesList = new ArrayList();
        countriesList.add((int) countryId);
        ArrayList<Integer> genresList = new ArrayList();
        genresList.add((int) genreId);

        KinopoiskApiService kinopoiskExtApiService = new KinopoiskApiService();

        if (this.keywords.length() == 0) {
            return kinopoiskExtApiService.getNavigator(countriesList, genresList, Order.RATING, 6, 8, 1950, 2000, 1);
        }

        return null;
    }

    public void getInformationAboutMovieStaff(int movieId) {
        KinopoiskApiService kinopoiskExtApiService = new KinopoiskApiService();
        System.out.println(kinopoiskExtApiService.getGallery(movieId).getData().getGallery().getKadr().get(0).getImage());
        List<StaffItem> filmInfo;
        int i = 0;
        int j = 0;

/*      do{

            filmInfo = kinopoiskExtApiService.getStaffList(movieId).getData().getStaffItems().get(i);//.get(j).getDescription();
            j=0;
            do{

                filmInfo.get(j).getDescription();
                System.out.println(filmInfo);
                j++;
                System.out.println("j = "+j);
            }while(j<filmInfo.size());
            System.out.println("*"+filmInfo.size());
            i++;
        }while(i<kinopoiskExtApiService.getStaffList(movieId).getData().getStaffItems().size());
*/

    }

    public FilmExt getInformationAboutMovie(int movieId) {
        KinopoiskApiService kinopoiskExtApiService = new KinopoiskApiService();
        return kinopoiskExtApiService.getFilmInfo(movieId);
    }
}

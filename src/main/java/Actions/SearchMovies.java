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
    private byte genreId;
    private byte countryId;
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

    public SearchMovies(Stage stage) {

        //this.stage = stage;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public byte getGenreId() {
        return genreId;
    }

    public void setGenreId(byte genreId) {
        this.genreId = genreId;
    }

    public byte getCountryId() {
        return countryId;
    }

    public void setCountryId(byte countryId) {
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
        genresList.add((int) countryId);
        NavigatorExt filmInfo = null;
        KinopoiskApiService kinopoiskExtApiService = new KinopoiskApiService();

        if (this.keywords.length() == 0) {
            filmInfo = kinopoiskExtApiService.getNavigator(countriesList, genresList, Order.RATING, ratingFrom, ratingTo, yearFrom, yearTo, pageCount);
        }

        System.out.println(filmInfo);
        return filmInfo;
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
        FilmExt filmInfo = new FilmExt();
        filmInfo = kinopoiskExtApiService.getFilmInfo(movieId);
        return filmInfo;
    }
}

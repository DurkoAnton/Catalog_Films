import Actions.AuthorizationInSystem;
import Actions.SearchMovies;
import Actions.WorkWithUserCatalog;
import DataStructure.AccountsDataBase;
import DataStructure.CustomerAccount;
import com.truedev.kinoposk.api.model.film.FilmExt;
import com.truedev.kinoposk.api.model.navigator.NavigatorExt;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/*import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;*/
import java.io.*;
import java.util.ArrayList;
import java.util.Properties;

public class StartWindowController {

    @FXML
    public Button returnToBackButton;
    @FXML
    public Button authorizeInSystemButton;
    @FXML
    public Label idLabel;
    @FXML
    public Button addMovieButton;
    @FXML
    public Button deleteMovieButton;
    @FXML
    public Label countWatchedMovies;
    @FXML
    public Label countMoviesInUserCatalog;
    @FXML
    private TextField searchindString;
    @FXML
    private ChoiceBox countryIdsBox;
    @FXML
    private ChoiceBox genreIdsBox;
    @FXML
    private Label sloganLabel;
    @FXML
    private Label movieLengthLabel;
    @FXML
    private Label premierDataLabel;
    @FXML
    private Label countriesLabel;
    @FXML
    private Label ageRatingLabel;
    @FXML
    private Label nameLabel;
    @FXML
    private Label descriptionLabel;
    @FXML
    private Label budgetLabel;
    @FXML
    private Label genreLabel;
    @FXML
    private Label seriesInfoLabel;
    @FXML
    private FlowPane flowPaneForShowListMovies;
    @FXML
    private Button getPremiersButton;
    @FXML
    private ScrollPane scroll;
    @FXML
    private ImageView imageViewForHandle;

    private SearchMovies searchMovies;
    private Stage stage;
    @FXML
    private Button showCatalogButton;
    @FXML
    private TextField ratingFrom;
    @FXML
    private TextField ratingTo;
    @FXML
    private TextField yearFrom;
    @FXML
    private TextField yearTo;
    @FXML
    private AnchorPane showFilmPane;

    private WorkWithUserCatalog workWithUserCatalog;

    private AuthorizationInSystem authorizationInSystem;

    public StartWindowController() throws IOException, ClassNotFoundException {
        searchMovies = new SearchMovies();
        initAuthorizationController();
        workWithUserCatalog = new WorkWithUserCatalog();
    }

    public void setStage(Stage stage) {
        this.stage = stage;

        this.stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                try {
                    writeDataBaseInFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void initAuthorizationController() throws IOException {
        FXMLLoader l = new FXMLLoader(getClass().getResource("AuthorizeWindow.fxml"));
        Stage st = new Stage();
        Scene scene = new Scene(l.load());
        st.setScene(scene);
        //  st.show();
        AuthorizationInSystem au = (AuthorizationInSystem) l.getController();
        this.authorizationInSystem = au;
        au.setStage(st);
    }

    @FXML
    public void doAutorize() throws IOException {
        this.authorizationInSystem.openAutorizeShow();
    }

    public void clearFlowPane() {
        int countChildrenElements = flowPaneForShowListMovies.getChildren().size();
        for (int i = 0; i < countChildrenElements; i++) {
            flowPaneForShowListMovies.getChildren().remove(0);
        }
    }

    public void showErrorMessage() {
    }

    public void showInfoLabelsAboutCatalog() {
        countMoviesInUserCatalog.setVisible(true);
        countMoviesInUserCatalog.setText(String.valueOf(authorizationInSystem.getCurrentUserInSystem().getCustomerMoviesList().size()));

        countWatchedMovies.setVisible(true);
        countWatchedMovies.setText(String.valueOf(authorizationInSystem.getCurrentUserInSystem().getWatchedCustomerMoviesList().size()));
    }

    @FXML
    public void showCatalog() {
        FilmExt filmInfo;
        if (authorizationInSystem.getStatus()) {

            flowPaneForShowListMovies.setVisible(true);
            scroll.setVisible(true);
            clearFlowPane();

            showInfoLabelsAboutCatalog();

            workWithUserCatalog.setCurrentCustomerAccount(authorizationInSystem.getCurrentUserInSystem());
            for (int i = 0; i < authorizationInSystem.getCurrentUserInSystem().getCustomerMoviesList().size(); i++) {
                filmInfo = searchMovies.getInformationAboutMovie(authorizationInSystem.getCurrentUserInSystem().getCustomerMoviesList().get(i));
                Image im = new Image(filmInfo.getData().getBigPosterUrl(), 210, 300, false, false);
                ImageView imageView = new ImageView(im);
                Hyperlink nameLink = new Hyperlink(filmInfo.getData().getNameRU());
                FilmExt finalFilmInfo = filmInfo;
                nameLink.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {

                        showFilmPane.setVisible(true);
                        scroll.setVisible(false);
                        FilmExt film = searchMovies.getInformationAboutMovie(finalFilmInfo.getData().getFilmID());
                        showInformationAboutMovie(film);
                        // searchMovies.getInformationAboutMovieStaff(finalFilmInfo.getData().getItems().get(finalI).getId());
                    }
                });
                VBox v = new VBox(imageView, nameLink);
                flowPaneForShowListMovies.getChildren().add(v);
            }

        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Look, an Information Dialog");
            alert.setContentText("I have a great message for you!");
            alert.showAndWait().ifPresent(rs -> {
                if (rs == ButtonType.OK) {
                }
            });
        }
    }

    public void setOnActionForLink() {

    }

    public byte getCountryId(int index) {
        switch (index) {
            case 1:
                return 2;
            case 2:
                return 13;
            case 0:
                return 1;
          /*  case "Франция":
                return 8;
            case "талия":
                return 14;
            case "спания":
                return 15;*/
            default:
                return 0;
        }
    }

    public int getGenreId(int index) {
        switch (index) {
            case 0:
                return 4;
            case 2:
                return 2;
            case 1:
                return 1750;
         /*   case "Биография":
                return 22;
            case "Вестерн":
                return 13;
            case "Военные":
                return 19;
            case "Детектив":
                return 17;
            case "Детский":
                return 456;
            case "Драма":
                return 8;*/
            default:
                return -1;
        }
    }

    public void setParamsForSearch() {
        searchMovies.setKeywords(searchindString.getText());
        searchMovies.setCountryId(getCountryId(countryIdsBox.getItems().indexOf(countryIdsBox.getValue())));

        searchMovies.setGenreId(getGenreId(genreIdsBox.getItems().indexOf(genreIdsBox.getValue())));
        searchMovies.setRatingFrom(Byte.parseByte(ratingFrom.getText()));

        searchMovies.setRatingTo(Byte.parseByte(ratingTo.getText()));
        searchMovies.setYearFrom(Integer.parseInt(yearFrom.getText()));
        searchMovies.setYearTo(Integer.parseInt(yearTo.getText()));
    }

    @FXML
    public void doSearchMovies() {
        NavigatorExt filmInfo;
        setParamsForSearch();

        getPremiersButton.setVisible(false);
        scroll.setVisible(true);
        flowPaneForShowListMovies.setVisible(true);
        returnToBackButton.setVisible(true);
        clearFlowPane();

        flowPaneForShowListMovies.setHgap(30);
        flowPaneForShowListMovies.setVgap(30);

        int pageCount = 1;
        do {
            filmInfo = searchMovies.searchMovies(pageCount);
            System.out.println(filmInfo.getData());//[0];
            for (int i = 0; i < filmInfo.getData().getItems().size(); i++) {

                Image im = new Image(filmInfo.getData().getItems().get(i).getBigPosterUrl(), 210, 300, false, false);
                ImageView imageView = new ImageView(im);
                Hyperlink nameLink = new Hyperlink(filmInfo.getData().getItems().get(i).getNameRU());
                int finalI = i;
                NavigatorExt finalFilmInfo = filmInfo;
                nameLink.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {

                        showFilmPane.setVisible(true);
                        scroll.setVisible(false);
                        FilmExt film = searchMovies.getInformationAboutMovie(finalFilmInfo.getData().getItems().get(finalI).getId());
                        showInformationAboutMovie(film);
                        // searchMovies.getInformationAboutMovieStaff(finalFilmInfo.getData().getItems().get(finalI).getId());
                    }
                });
                VBox v = new VBox(imageView, nameLink);
                flowPaneForShowListMovies.getChildren().add(v);
            }
            pageCount++;
        } while (pageCount <= filmInfo.getData().getPagesCount());
    }

    @FXML
    public void returnBackScene() {
        if (showFilmPane.isVisible()) {
            flowPaneForShowListMovies.setVisible(true);
            scroll.setVisible(true);
            showFilmPane.setVisible(false);
        } else {
            flowPaneForShowListMovies.setVisible(false);
        }
    }


    /*public void sendEmail() throws AddressException {

        String to = "customerlogin6@gmail.com";

        // Sender's email ID needs to be mentioned
        String from = "anton.durko.work@gmail.com";
        final String username = "anton.durko.work";//change accordingly
        final String password = "rasko123";//change accordingly

        // Assuming you are sending email through relay.jangosmtp.net
        String host = "relay.jangosmtp.net";

        Properties props = new Properties();
        // Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        // Get the Session object.
        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {
            // Create a default MimeMessage object.
            Message message = new MimeMessage(session);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(from));

            // Set To: header field of the header.
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(to));

            // Set Subject: header field
            message.setSubject("Testing Subject");

            // Now set the actual message
            message.setText("Hello, this is sample for to check send " +
                    "email using JavaMailAPI ");
            System.out.println("Sent message successfully....");
            // Send message
            Transport.send(message);

            System.out.println("Sent message successfully....");

        } catch (MessagingException e) {
            // throw new RuntimeException(e);
            System.out.println(e.getMessage());
        }
    }*/

    public void writeDataBaseInFile() throws IOException {
        FileOutputStream fos = new FileOutputStream(new File("CustomerDataBaseFile"));
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(authorizationInSystem.getDataBase().getCustomerAccountsDataBase());
        oos.close();
    }

    public void readDataBaseFromFile() throws IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream(new File("CustomerDataBaseFile"));
        ObjectInputStream ois = new ObjectInputStream(fis);
        ArrayList<CustomerAccount> object = (ArrayList<CustomerAccount>) ois.readObject();
        this.authorizationInSystem.getDataBase().setCustomerAccountsDataBase(object);
        this.authorizationInSystem.getDataBase().getIterator().setList(object);
        ois.close();
    }

    public void showInformationAboutMovie(FilmExt film) {
        nameLabel.setText(film.getData().getNameEN());
        ageRatingLabel.setText(String.valueOf(film.getData().getRatingAgeLimits()));
        countriesLabel.setText(film.getData().getCountry());
        sloganLabel.setText(film.getData().getSlogan());
        movieLengthLabel.setText(film.getData().getFilmLength());
        genreLabel.setText(film.getData().getGenre());
        budgetLabel.setText(film.getData().getBudgetData().getBudget());
        descriptionLabel.setText(film.getData().getDescription());
        seriesInfoLabel.setText(String.valueOf(film.getData().getSeriesInfo()));
        idLabel.setText(String.valueOf(film.getData().getFilmID()));
        Image im = new Image(film.getData().getBigPosterUrl());
        imageViewForHandle.setImage(im);
        if (authorizationInSystem.getStatus())
            addMovieButton.setDisable(false);
        else
            addMovieButton.setDisable(true);
    }

    @FXML
    public void addMovieInUserCatalog() {
        if (authorizationInSystem.getStatus()) {
            workWithUserCatalog.setCurrentCustomerAccount(authorizationInSystem.getCurrentUserInSystem());
        }
        workWithUserCatalog.getAddingMoviesInCatalog().
                actionInCatalog(workWithUserCatalog.getCurrentCustomerAccount(),
                        Integer.parseInt(idLabel.getText().substring(0, idLabel.getText().length())));
    }

    public void deleteMovieFromUserCatalog() {
        if (authorizationInSystem.getStatus()) {
            workWithUserCatalog.setCurrentCustomerAccount(authorizationInSystem.getCurrentUserInSystem());
        }
        workWithUserCatalog.getDeletingMoviesFromCatalog().
                actionInCatalog(workWithUserCatalog.getCurrentCustomerAccount(),
                        Integer.parseInt(idLabel.getText().substring(0, idLabel.getText().length())));
    }
}
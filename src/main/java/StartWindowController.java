import Actions.AuthorizationInSystem;
import Actions.SearchMovies;
import Actions.WorkWithUserCatalog;
import DataStructure.AccountsDataBase;
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

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.*;
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
    private FlowPane flows;
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

    private AuthorizationInSystem authorizeInSystem;

    public StartWindowController() throws IOException {
        searchMovies = new SearchMovies(null);
        initAuthorizationController();
        workWithUserCatalog = new WorkWithUserCatalog();
    }

    public void initAuthorizationController() throws IOException {
        FXMLLoader l = new FXMLLoader(getClass().getResource("AuthorizeWindow.fxml"));
        Stage st = new Stage();
        Scene scene = new Scene(l.load());
        st.setScene(scene);
        AuthorizationInSystem au = (AuthorizationInSystem) l.getController();
        this.authorizeInSystem = au;
        au.setStage(st);
    }

    public StartWindowController(Stage stage) throws IOException, ClassNotFoundException {
        this.stage = stage;
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
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

    @FXML
    public void doAutorize() throws IOException {
        this.authorizeInSystem.openAutorizeShow();
    }

    public void clearFlowPane() {
        int countChildrenElements = flows.getChildren().size();
        for (int i = 0; i < countChildrenElements; i++) {
            flows.getChildren().remove(0);
        }
    }

    public void showErrorMessage() {
    }

    @FXML
    public void showCatalog() {
        System.out.println("zaq");
        FilmExt filmInfo;
        if (authorizeInSystem.getStatus()) {
            flows.setVisible(true);
            scroll.setVisible(true);
            clearFlowPane();
            workWithUserCatalog.setCurrentCustomerAccount(authorizeInSystem.getCurrentUserInSystem());
            for (int i = 0; i < authorizeInSystem.getCurrentUserInSystem().getCustomerMoviesList().size(); i++) {
                System.out.println("qqq" + authorizeInSystem.getCurrentUserInSystem().getCustomerMoviesList().get(i));
                filmInfo = searchMovies.getInformationAboutMovie(authorizeInSystem.getCurrentUserInSystem().getCustomerMoviesList().get(i));
                Image im = new Image(filmInfo.getData().getBigPosterUrl(), 210, 300, false, false);
                ImageView imageView = new ImageView(im);
                Hyperlink nameLink = new Hyperlink(filmInfo.getData().getNameRU());
                VBox v = new VBox(imageView, nameLink);
                flows.getChildren().add(v);
            }

//            System.out.println("system"+authorizeInSystem.getCurrentUserInSystem().getUserLogin());
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

    public byte getCountryId(String word) {
        switch (word) {
            case "Россия":
                return 2;
            case "СССР":
                return 13;
            case "США":
                return 1;
            case "Франция":
                return 8;
            case "Италия":
                return 14;
            case "Испания":
                return 15;
            default:
                return 0;
        }
    }

    public short getGenreId(String word) {
        switch (word) {
            case "Комедия":
                return 6;
            case "Мультфильм":
                return 14;
            case "Триллер":
                return 4;
            case "Ужасы":
                return 1;
            case "Фантастика":
                return 2;
            case "Аниме":
                return 1750;
            case "Биография":
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
                return 8;
            default:
                return 0;
        }
    }

    public void setParamsForSearch() {
        searchMovies.setKeywords(searchindString.getText());

        searchMovies.setCountryId(getCountryId((String) countryIdsBox.getValue()));
        searchMovies.setGenreId((byte) getGenreId((String) genreIdsBox.getValue()));
        searchMovies.setRatingFrom(Byte.parseByte(ratingFrom.getText()));
        searchMovies.setRatingTo(Byte.parseByte(ratingTo.getText()));
        searchMovies.setYearFrom(Integer.parseInt(yearFrom.getText()));
        searchMovies.setYearTo(Integer.parseInt(yearTo.getText()));
    }

    @FXML
    public void doSearchMovies() {
        setParamsForSearch();
        NavigatorExt filmInfo;

        getPremiersButton.setVisible(false);
        scroll.setVisible(true);
        flows.setVisible(true);
        returnToBackButton.setVisible(true);

        flows.setHgap(30);
        flows.setVgap(30);

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
                flows.getChildren().add(v);
            }
            pageCount++;
        } while (pageCount <= filmInfo.getData().getPagesCount());
    }

    @FXML
    public void returnBackScene() {
        if (showFilmPane.isVisible()) {
            flows.setVisible(true);
            scroll.setVisible(true);
            showFilmPane.setVisible(false);
        } else {
            flows.setVisible(false);
        }
    }

    @FXML
    public void sendEmail() throws AddressException {

        String to = "customerlogin6@gmail.com";

        // Sender's email ID needs to be mentioned
        String from = "anton.durko.work@gmail.com";
        final String username = "anton.durko.work";//change accordingly
        final String password = "";//change accordingly

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
    }

    public void writeDataBaseInFile() throws IOException {
        FileOutputStream fos = new FileOutputStream(new File("D:\\untitled\\src\\main\\resources\\CustomerDataBase"));
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        System.out.println(authorizeInSystem.getDataBase().getCustomerAccountsDataBase().size());
        oos.writeObject(authorizeInSystem.getDataBase());
        oos.close();
    }

    public void readDataBaseFromFile() throws IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream(new File("D:\\untitled\\src\\main\\resources\\CustomerDataBase"));
        ObjectInputStream ois = new ObjectInputStream(fis);
        AccountsDataBase data = (AccountsDataBase) ois.readObject();
        ois.close();
    }

    public void showInformationAboutMovie(FilmExt film) {
        nameLabel.setText(nameLabel.getText() + ": " + film.getData().getNameEN());
        ageRatingLabel.setText(ageRatingLabel.getText() + ": " + film.getData().getRatingAgeLimits());
        countriesLabel.setText(countriesLabel.getText() + ": " + film.getData().getCountry());
        sloganLabel.setText(sloganLabel.getText() + ": " + film.getData().getSlogan());
        movieLengthLabel.setText(movieLengthLabel.getText() + ": " + film.getData().getFilmLength());
        genreLabel.setText(genreLabel.getText() + ": " + film.getData().getGenre());
        budgetLabel.setText(budgetLabel.getText() + ": " + film.getData().getBudgetData().getBudget());
        descriptionLabel.setText(descriptionLabel.getText() + ": " + film.getData().getDescription());
        seriesInfoLabel.setText(seriesInfoLabel.getText() + ": " + film.getData().getSeriesInfo());
        idLabel.setText(idLabel.getText() + " " + film.getData().getFilmID());
        Image im = new Image(film.getData().getBigPosterUrl());
        imageViewForHandle.setImage(im);
        if (authorizeInSystem.getStatus())
            addMovieButton.setDisable(false);
        else
            addMovieButton.setDisable(true);
    }

    @FXML
    public void addMovieInUserCatalog() {
        if (authorizeInSystem.getStatus()) {
            workWithUserCatalog.setCurrentCustomerAccount(authorizeInSystem.getCurrentUserInSystem());
        }
        workWithUserCatalog.getAddingMoviesInCatalog().
                addNewMovieInCatalog(workWithUserCatalog.getCurrentCustomerAccount(),
                        Integer.parseInt(idLabel.getText().substring(4, idLabel.getText().length())));
    }
}
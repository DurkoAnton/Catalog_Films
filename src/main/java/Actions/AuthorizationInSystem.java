package Actions;

import DataStructure.AccountsDataBase;
import DataStructure.CustomerAccount;
import DataStructure.Memento;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

public class AuthorizationInSystem {

    public Button createAccountButton;
    @FXML
    private PasswordField passwordField;
    @FXML
    private TextField loginField;

    private AccountsDataBase dataBase;

    private boolean authorizationStatus;
    private Memento oldState;
    private Stage stage;

    private CustomerAccount currentUserInSystem;

    public AuthorizationInSystem() {
        dataBase = new AccountsDataBase();
        oldState = new Memento();
    }

    public Memento getOldState() {
        return oldState;
    }

    public AccountsDataBase getDataBase() {
        return dataBase;
    }

    public void setDataBase(AccountsDataBase dataBase) {
        this.dataBase = dataBase;
    }

    public boolean getStatus() {
        return authorizationStatus;
    }

    public AuthorizationInSystem(Stage previousStage) throws IOException {
        authorizationStatus = false;
        //Parent root = FXMLLoader.load(getClass().getResource("/AuthorizeWindow.fxml"));
        //Scene scene = new Scene(root);
        //stage = new Stage();
        //stage.setScene(scene);
        //this.previousStage = previousStage;
    }

    public void openAutorizeShow() {

        stage.show();
    }

    @FXML
    public void addNewUser() {
        if (dataBase.addNewUser(new CustomerAccount(loginField.getText(), passwordField.getText()))) {
            System.out.println(dataBase.getCustomerAccountsDataBase().size());
        }
        stage.close();
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void clearFieldsAuthorization() {
        loginField.clear();
        passwordField.clear();
    }

    public void enterInSystem() {
        if (!authorizationStatus) {
            if (dataBase.checkIsAvailableThisUser(new CustomerAccount(loginField.getText(), passwordField.getText()))) {

                currentUserInSystem = dataBase.getUserObject(loginField.getText());
                authorizationStatus = true;
                oldState.setState(currentUserInSystem.getStatus());
                stage.close();
            } else
                authorizationStatus = false;
        }
    }

    public void exitFromSystem() {
        if (authorizationStatus)
            authorizationStatus = false;
    }

    public void initializationStartStatus() {

    }

    public CustomerAccount getCurrentUserInSystem() {
        return currentUserInSystem;
    }

    public void showErrorAuthorizationInSystem() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Look, an Information Dialog");
        alert.setContentText("I have a great message for you!");
        alert.showAndWait().ifPresent(rs -> {
            if (rs == ButtonType.OK) {
            }
        });
    }

    public boolean checkCorrectInfo() {
        return false;
    }
}

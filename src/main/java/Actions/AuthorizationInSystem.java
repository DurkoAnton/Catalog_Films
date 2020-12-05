package Actions;

import DataStructure.AccountsDataBase;
import DataStructure.CustomerAccount;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class AuthorizationInSystem {
    public Button createAccountButton;
    @FXML
    private PasswordField passwordField;
    @FXML
    private TextField loginField;

    private static AccountsDataBase dataBase;

    private static boolean authorizationStatus;
    private static Stage stage;
    private Stage previousStage;

    public AuthorizationInSystem() {
        dataBase = new AccountsDataBase();
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
        Parent root = FXMLLoader.load(getClass().getResource("AutorizeWindow.fxml"));
        Scene scene = new Scene(root);
        stage = new Stage();
        stage.setScene(scene);
        this.previousStage = previousStage;
    }

    public void openAutorizeShow() {
        stage.show();
    }

    public void addNewUser() {
        if (dataBase.addNewUser(new CustomerAccount(loginField.getText(), passwordField.getText()))) {
            authorizationStatus = true;
            System.out.println(dataBase.getCustomerAccountsDataBase().size());
        }
        stage.close();
    }

    public void enterInSystem() {
        if (!authorizationStatus) {
            if (dataBase.checkIsAvailableThisUser(new CustomerAccount(loginField.getText(), passwordField.getText())))
                authorizationStatus = true;
            else
                authorizationStatus = false;
        }
    }

    public void exitFromSystem() {
        if (authorizationStatus)
            authorizationStatus = false;
    }

    public void initializationStartStatus() {

    }
}

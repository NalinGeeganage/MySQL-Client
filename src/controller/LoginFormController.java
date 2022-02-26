package controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;

public class LoginFormController {

    public TextField txtHost;
    public TextField txtPort;
    public TextField txtUserName;
    public Button btnConnect;
    public Button btnExit;
    public PasswordField txtPassword;

    public void btnConnect(ActionEvent event) {
        /*Check whether username and password are correct*/


        try {
            if (txtHost.getText().trim().isEmpty()){
                new Alert(Alert.AlertType.ERROR,"Please enter Host name",ButtonType.OK).show();
                txtHost.requestFocus();
                txtHost.selectAll();
                return;
            }
            else if(txtPort.getText().trim().isEmpty()){
                new Alert(Alert.AlertType.ERROR,"Check your port that you entered",ButtonType.OK).show();
                txtPort.requestFocus();
                txtPort.selectAll();
                return;
            }
            else if (txtUserName.getText().trim().isEmpty()){
                new Alert(Alert.AlertType.ERROR,"Invalid username",ButtonType.OK).show();
                txtUserName.requestFocus();
                txtUserName.selectAll();
                return;
            }

            //        String command = String.format("mysql -h %s -u %s -p%s --port %s -e exit",
//                txtHost.getText(),
//                txtUserName.getText(),
//                txtPassword.getText(),
//                txtPort.getText());

//            String[]  command = {"mysql",
//                    "-h",txtHost.getText(),
//                    "-u",txtUserName.getText(),
//                    "-p"+txtPassword.getText(),"--port",
//                    txtPort.getText(),
//                    "-e","exit"};
//
//            Process mysql = Runtime.getRuntime().exec(command);

            Process mysql = new ProcessBuilder("mysql",
                    "-h",txtHost.getText(),
                    "-u",txtUserName.getText(),
                    "-p","--port",
                    txtPort.getText(),
                    "-e","exit").start();
            /*to remove show password warning*/
            mysql.getOutputStream().write(txtPassword.getText().getBytes());
            mysql.getOutputStream().close();

            int exitCode = mysql.waitFor();
            if (exitCode !=0){
                InputStream es = mysql.getErrorStream();
                byte[] buffer = new byte[es.available()];
                es.read(buffer);
                es.close();

                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Connection failure");
                alert.setHeaderText("Can't establish connection");
                alert.setContentText(new String(buffer));
                alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
                alert.show();
                txtUserName.requestFocus();
                txtUserName.selectAll();
            }
            else{
                System.out.println("wede goda");
                FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("/view/ShellForm.fxml"));
                AnchorPane pane = fxmlLoader.load();
                ShellFormController controller = fxmlLoader.getController();
                controller.initData(txtHost.getText(),txtPort.getText(),txtUserName.getText(),txtPassword.getText());
                Scene scene = new Scene(pane);
                Stage primaryStage = (Stage) (btnConnect.getScene().getWindow());
                primaryStage.setScene(scene);
                primaryStage.sizeToScene();
                primaryStage.setTitle("Shell Form");
                primaryStage.setResizable(false);
                Platform.runLater(primaryStage::sizeToScene);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void btnExit_OnAction(ActionEvent event) {
        System.exit(0);
    }
}

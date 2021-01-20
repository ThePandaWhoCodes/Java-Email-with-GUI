/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mailguı;

import javax.mail.*;  
import javax.mail.internet.*; 
import java.util.Properties;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import java.lang.Exception;

/**
 *
 * @author Kullanıcı
 */
public class MailGUI extends Application {
    
    Session session;
    
    public boolean sendmail(String to, String from, String subj, String msg)
    {   
        Properties properties = System.getProperties();
        String host = "smtp.gmail.com";//or IP address
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");
        session = Session.getInstance(properties, new javax.mail.Authenticator() {
            
            @Override
            protected PasswordAuthentication getPasswordAuthentication(){

                return new PasswordAuthentication(from , "pandademic");

            }

        });
        
        session.setDebug(true);  
      
         //compose the message  
          try{  
             MimeMessage message = new MimeMessage(session);  
             message.setFrom(new InternetAddress(from));  
             message.addRecipient(Message.RecipientType.TO,new InternetAddress(to));  
             message.setSubject(subj);  
             message.setText(msg);  
      
             // Send message  
             Transport.send(message);  
             return true;
      
          }catch (MessagingException mex) {mex.printStackTrace();
          return false;}  
       }  

    
    GridPane pane = new GridPane();
    
    @Override
    public void start(Stage primaryStage) {
        TextField to = new TextField();
        TextField from = new TextField();
        TextField subj = new TextField();
        TextArea msg = new TextArea();
        
        msg.setMinHeight(300);
        //msg.setMinWidth(200);
        msg.setMaxHeight(300);
        //msg.setMaxWidth(200);
        Label lbl_to = new Label();
        lbl_to.setText("To: ");
        
        Label lbl_from = new Label();
        lbl_from.setText("From: ");
        
        Label lbl_subj = new Label();
        lbl_subj.setText("Subject: ");
        
        Button btn = new Button();
        btn.setText("Send mail");
        
        btn.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                boolean check;
                check = sendmail(to.getText(), from.getText(), subj.getText(), msg.getText());
                
                if(check == true)
                    btn.setText("Sent");
            }
        });
        
        pane.add(lbl_to, 0, 1);
        pane.add(to, 1, 1);
        pane.add(lbl_from, 0, 2);
        pane.add(from, 1, 2);
        pane.add(lbl_subj, 0, 3);
        pane.add(subj, 1, 3);
        pane.add(msg, 1, 4);
        pane.add(btn, 2, 5);
        
        
        Scene scene = new Scene(pane, 700, 450);
        
        primaryStage.setTitle("Mail GUI");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}

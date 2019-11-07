package beans;

import dao.UserDAO;
import entities.User;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.event.ActionEvent;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

@ManagedBean
@RequestScoped
public class UserBean {
    private String email, oldPassword, password, password2, message;

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword2() {
        return password2;
    }

    public void setPassword2(String password2) {
        this.password2 = password2;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public UserBean() {
    }

    public String register() {
        if (!password.equals(password2)) {
            message = "Passwords do not match!";
            return "register";
        }

        User u = new User();
        u.setEmail(email);
        u.setPassword(password);

        boolean done = UserDAO.register(u);

        if (done) {
            return "login?faces-redirect=true";
        } else {
            message = "Sorry! User registration failed!";
            return "register";
        }
    }
    
     public String login() {
        User u = UserDAO.login(email,password);

        if (u == null) {
            message = "Sorry! Login failed!";
            return "login";
        } else {
            Util.addToSession("email", email);
            return "/search?faces-redirect=true";
        }
    }
     
     
     public void changePassword(ActionEvent evt) {
        boolean done = UserDAO.changePassword(Util.getEmail(),oldPassword, password);
        if (done) {
            message="Password has been changed successfully!";
        }
        else {
             message = "Sorry! Could not change password. Old passwod may be incorrect!";
        }
    }
     
    public String logout() {
        Util.terminateSession();
        return "/all/login?faces-redirect=true";
    }
    
    public void recoverPassword(ActionEvent evt){
        User  u = UserDAO.getUser(email);
        if( u == null) {
            message  = "Sorry! Could not find user with the given email address!";
            return;
        }
        
        // send mail with details
        
        String body = "Dear User,<p/>" +
                   "Please use the following password to login.<p/>" + 
                   "Password : " + u.getPassword() + "<p/>" +
                   "Team,<br/>YourNotes.Com";
        
         try {
            Properties props = System.getProperties();
            Session session = Session.getDefaultInstance(props, null);
            // construct the message
            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress("webmaster@yournotes.com"));
            msg.setRecipient(Message.RecipientType.TO, new InternetAddress(u.getEmail()));
            msg.setDataHandler(new DataHandler(body, "text/html"));
            msg.setSubject("Password Recovery");
            // send message
            Transport.send(msg);
            message="A mail has been sent with your details. Please use those details to login again!";
        } catch (Exception ex) {
            System.out.println("Error sending mail : " + ex.getMessage());
            message="Sorry! Could not send mail! Please try again!";
        }
        
    }

}

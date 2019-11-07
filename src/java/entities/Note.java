
package entities;

import java.text.SimpleDateFormat;
import java.util.Date;


public class Note {
    private int id;
    private String title, text;
    private java.util.Date updated;
    private User user;
    
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
   

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
  

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

   

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    
    public String getPreview() {
        return   text.substring(0,  text.length() > 50 ? 50: text.length());
    }
    
    public String getformattedUpdated() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        return sdf.format(updated);
    }
    
    
}

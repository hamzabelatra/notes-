package beans;

import dao.NoteDAO;
import dao.UserDAO;
import entities.Note;
import java.util.Date;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.event.ActionEvent;

@ManagedBean
@RequestScoped
public class NoteBean {

    private int id;
    private String title, text, email, message, action;

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
    private Date updatedon;
    private List<Note> notes, allNotes;

    public List<Note> getAllNotes() {
        allNotes = NoteDAO.getAllNotes(Util.getEmail());
        return allNotes;
    }
    public List<Note> getNotes() {
        return notes;
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getUpdatedon() {
        return updatedon;
    }

    public void setUpdatedon(Date updatedon) {
        this.updatedon = updatedon;
    }

    public NoteBean() {
    }

    public void add(ActionEvent evt) {
        Note note = new Note();
        note.setTitle(title);
        note.setText(text);
        note.setUpdated(new Date());
        note.setUser(UserDAO.getUser(Util.getEmail()));
        boolean done = NoteDAO.add(note);
        if (!done) {
            message = "Sorry! Could not add note. Please try again!";
        } else {
            text = "";
            title = "";
            message = "Added note successfully!";
        }
        
        
    }
    //  executed by viewAction 
    public String initSearch() {
        if (text != null || action != null)
            notes = NoteDAO.searchNotes(Util.getEmail(),text == null ? "" : text);
        return null;
    }

    public void search(ActionEvent evt) {
        notes = NoteDAO.searchNotes(Util.getEmail(), text);
    }

    public String delete() {
        NoteDAO.delete(id);
        return action + "?action=" + action + "&text=" + text;
    }
    
    public String load() {
        Note note = NoteDAO.getNote(id);
        this.title = note.getTitle();
        this.text = note.getText();
        return null;
    }
    
    public void update(ActionEvent evt) {
         boolean done = NoteDAO.update(id, title, text);
         if ( done)
             message = "Updated Note Successfully!";
         else
             message = "Sorry! Could not update note!";
    }
}

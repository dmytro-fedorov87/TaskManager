package dto;


import java.util.Date;

public class TaskToNotifyDTO {
    private String email;
    private String text;
    private Date data;

    public TaskToNotifyDTO(String email, String text, Date data) {
        this.email = email;
        this.text = text;
        this.data = data;
    }

    public String getEmail() {
        return email;
    }

    public String getText() {
        return text;
    }

    public Date getData() {
        return data;
    }
}

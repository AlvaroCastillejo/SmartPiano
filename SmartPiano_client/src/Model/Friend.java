package Model;

import java.io.Serializable;

public class Friend implements Serializable {
    private String name;
    private String mail;

    public Friend(String mail, String name) {
        this.mail = mail;
        this.name = name;
    }

    public String getId() {
        return mail;
    }

    public void setId(String mail) {
        this.mail = mail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

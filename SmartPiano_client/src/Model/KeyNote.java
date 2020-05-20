
package Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class KeyNote {

    @SerializedName("note")
    @Expose
    private String note;
    @SerializedName("keyBoard")
    @Expose
    private Integer keyBoard;

    public String getNote() { return note; }

    public void setNote(String note) {
        this.note = note;
    }

    public Integer getKeyBoard() {
        return keyBoard;
    }

    public void setKeyBoard(Integer keyBoard) {
        this.keyBoard = keyBoard;
    }

}

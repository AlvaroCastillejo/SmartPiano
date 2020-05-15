
package Model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class KeyNotes {

    @SerializedName("keyNote")
    @Expose
    private List<KeyNote> keyNote = null;

    public List<KeyNote> getKeyNote() {
        return keyNote;
    }

    public void setKeyNote(List<KeyNote> keyNote) {
        this.keyNote = keyNote;
    }

}

package Model;

import java.io.Serializable;
import java.util.ArrayList;

public class FriendListToSend implements Serializable {
    private ArrayList<Friend> friendList;

    public void setFriendList(ArrayList<Friend> friendList) {
        this.friendList = friendList;
    }

    public ArrayList<Friend> getFriendList() {
        return friendList;
    }
}

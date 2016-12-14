package bpsound.meettheteam.items;

/**
 * Created by elegantuniv on 2016. 12. 10..
 */

public class MemberItem {
    public String id;
    public String avatar;
    public String bio;
    public String title;
    public String firstName;
    public String lastName;

    public MemberItem() {
    }

    public MemberItem(String id, String avatar, String bio, String title, String firstName, String lastName) {
        this.id = id;
        this.avatar = avatar;
        this.bio = bio;
        this.title = title;
        this.firstName = firstName;
        this.lastName = lastName;
    }
}

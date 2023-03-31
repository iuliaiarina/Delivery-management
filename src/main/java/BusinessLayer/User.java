package BusinessLayer;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable {
    private static int max;
    private String username;
    private String password;
    private int id;
    private Type type;

    public User(String username, String password, Type type) {
        max++;
        this.username = username;
        this.password = password;
        this.id = max;
        this.type = type;
    }

    public static void setMax(ArrayList<User> users) {
        int count=0;
        for(User u: users)
            if(count<= u.id)
                count=u.id;
        max = count;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Type getType() {
        return type;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", id=" + id +
                ", type=" + type +
                '}';
    }
}

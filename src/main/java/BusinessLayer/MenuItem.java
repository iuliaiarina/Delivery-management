package BusinessLayer;

import java.awt.*;
import java.io.Serializable;

public abstract class MenuItem implements Serializable {
    public String title;
    public abstract int computePrice();

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}

package in.co.visiontek.quoteoftheday;

import java.io.Serializable;

public class FavoriteItem implements Serializable {
    public FavoriteItem() {
    }

    private String text;
        public FavoriteItem(String text) {
            this.text = text;
        }

        public String getText() {
            return text;
        }
    }


package com.zwx.scan.app.feature.member;

import java.io.Serializable;

/**
 * author : lizhilong
 * time   : 2019/04/03
 * desc   :
 * version: 1.0
 **/
public class FontBean implements Serializable {

    private Boolean isChecked;

    private int font;

    private String colour;

    public Boolean getChecked() {
        return isChecked;
    }

    public void setChecked(Boolean checked) {
        isChecked = checked;
    }

    public int getFont() {
        return font;
    }

    public void setFont(int font) {
        this.font = font;
    }

    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }
}

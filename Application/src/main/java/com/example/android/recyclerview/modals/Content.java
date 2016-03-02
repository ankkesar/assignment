package com.example.android.recyclerview.modals;

/**
 * Created by ankur on 28-02-2016.
 */
public class Content extends AbstractBaseContent {
   String label;
    String image;
    String template;
    SubContent[] subContent;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public SubContent[] getSubContent() {
        return subContent;
    }

    public void setSubContent(SubContent[] subContent) {
        this.subContent = subContent;
    }
}

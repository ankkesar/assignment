package com.example.android.recyclerview.modals;

/**
 * Created by ankur on 28-02-2016.
 */
public abstract class AbstractBaseContent {
    public static final String TEMPLATE_TYPE_1 = "product-template-1";
    public static final String TEMPLATE_TYPE_2 = "product-template-2";
    public static final String TEMPLATE_TYPE_3 = "product-template-3";
    String template="";
    String label;
    String image;

    public abstract String getTemplate();

    public abstract void setTemplate(String template);

    public abstract String getLabel();

    public abstract void setLabel(String label) ;

    public abstract String getImage();

    public abstract void setImage(String image);
    public abstract SubContent[] getSubContent() ;
}

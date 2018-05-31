package com.company.pppp;

/**
 * Created by company on 5/22/2018.
 */

public class Category {
    static int id;
    String category;
    String linkphotos;
    public Category(String category, String linkphotos)
    {
        id++;
        this.category=category;
        this.linkphotos=linkphotos;
    }
    public Category()
    {
//gfyyjujigfrfgvbkjuughy
    }
    public Category(String category)
    {
this.category=category;
    }

    public String getCategory()
    {
        return category;
    }
    public String getLinkphotos()
    {
        return linkphotos;
    }
    public void setCategory(String category)
    {
        this.category=category;
    }
    public void setLinkphotos(String linkphotos)
    {
        this.linkphotos=linkphotos;
    }

}

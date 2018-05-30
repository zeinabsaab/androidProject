package com.company.pppp;

/**
 * Created by company on 5/22/2018.
 */

public class News {
    static int idNew;
      int idCategory=0;
    String title;
    String source;
    String description;
    String attachment;
    String date;
    public News(String title, String source, String description, String attachment, String date)
    {
        idNew++;
        idCategory++;
    this.title=title;
    this.source=source;
    this.description=description;
    this.attachment=attachment;
    this.date=date;

    }
    public News(String title, String source, String description, String date)
    {
        idNew++;

        this.title=title;
        this.source=source;
        this.description=description;
        this.date=date;

    }

    public String getTitle()
    {
        return title;
    }
    public void setTitle(String title)
    {
        this.title=title;
    }
    public String getSource()
    {
        return source;
    }
    public void setSource(String source)
    {
        this.source=source;
    }
    public void setDescription(String description)
    {
        this.description=description;
    }
 public String getDescription()
 {
     return description;
 }

  public void setIdNew(int idNew)
  {
      this.idNew=idNew;
  }
  public int getIdNew(){
        return idNew;
  }
  public int getIdCategory()
  {
      return idCategory;
  }
  public void setIdCategory(int idCategory)
  {
      this.idCategory=idCategory;
  }
  public String getDate()
  {
      return date;
  }
  public void setDate(String date)
  {this.date=date;
  }
  public String getAttachment()
  {
      return attachment;
  }
  public void setAttachment(String attachment)
  {
      this.attachment=attachment;
  }

}

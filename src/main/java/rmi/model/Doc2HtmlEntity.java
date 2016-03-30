package rmi.model;

import java.io.Serializable;

public class Doc2HtmlEntity
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  private int id;
  private String name;
  private int pagecount;
  
  public void setId(int id)
  {
    this.id = id;
  }
  
  public int getId()
  {
    return this.id;
  }
  
  public void setName(String name)
  {
    this.name = name;
  }
  
  public String getName()
  {
    return this.name;
  }
  
  public void setPagecount(int pagecount)
  {
    this.pagecount = pagecount;
  }
  
  public int getPagecount()
  {
    return this.pagecount;
  }
}

package model;


import javafx.scene.image.Image;

public class Card
{
  private int id = 1;

  public Card(int id)
  {
    this.id = id;
  }

  public Image getImage()
  {
    return new Image(getClass().getResourceAsStream("../images/card"+id+".jpg"));
  }

  @Override
  public String toString()
  {
    return String.valueOf(id);
  }
}

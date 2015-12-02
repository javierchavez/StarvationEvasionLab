package model;


import javafx.scene.image.Image;

/**
 * Class to hold data about a card
 */
public class Card
{
  private final Image image;
  private int id = 1;

  public Card(int id)
  {
    this.id = id;
    image = new Image(getClass().getResourceAsStream("../images/card"+id+".jpg"));
  }

  public Image getImage()
  {
    return image;
  }

  @Override
  public String toString()
  {
    return "Card id: " + String.valueOf(id);
  }
}

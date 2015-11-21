package model;


import javafx.scene.image.Image;

import java.util.ArrayList;

public class Region
{
  public String name = "";
  private String imageFilename = "";
  private ArrayList<Card> cards = new ArrayList<>();
  private Image image;

  public Region(String name, String img)
  {

    this.name = name;
    this.imageFilename = img;
    this.image = new Image(getClass().getResourceAsStream(img));
  }

  public Image getImage()
  {
    return image;
  }


  public ArrayList<Card> getDraftedCards()
  {
    return cards;
  }
}

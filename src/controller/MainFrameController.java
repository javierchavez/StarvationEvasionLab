package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Shape;
import model.Card;
import model.Region;
import util.Reader;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.ResourceBundle;

public class MainFrameController implements Initializable
{
  private HashMap<Polygon, Region> regionMap = new HashMap<>();
  private final Image image = new Image(getClass().getResourceAsStream("../images/usa.png"));
  private Region clickedRegion = null;
  private GraphicsContext graphics = null;
  private GraphicsContext graphicsData = null;
  private ArrayList<Card> hand = new ArrayList<>();

  @FXML
  Canvas mapCanvas;

  @FXML
  Canvas mapDataCanvas;

  @FXML
  Group handGroup;


  @Override
  public void initialize(URL location, ResourceBundle resources)
  {
    for (int i = 0; i < 7; i++)
    {
      hand.add(new Card((i % 4) + 1));
    }

    graphics = mapCanvas.getGraphicsContext2D();
    graphicsData = mapDataCanvas.getGraphicsContext2D();
    graphics.drawImage(image,0,0);

    initalizeRegions();
    startPolicyAnimation();


    mapCanvas.setOnMouseClicked(event -> {
      Region _region = getRegion(event);

      if (clickedRegion == _region)
      {
        System.out.println(_region.name);
        return;
      }
      else
      {
        clickedRegion = _region;
      }

      if (_region != null)
      {
        graphics.drawImage(_region.getImage(), 0, 0);
        System.out.println(_region.name);
      }


    });


    mapCanvas.setOnMouseMoved(event -> {

      Region _region = getRegion(event);
      if (_region != null)
      {

        graphics.clearRect(0, 0, image.getWidth(), image.getHeight() + 30);
        graphics.drawImage(image,0,0);
        graphics.drawImage(clickedRegion.getImage(), 0, 0);

        graphics.drawImage(_region.getImage(), 0, 0);
        graphics.strokeText(_region.name, mapCanvas.getWidth() / 5,
                            image.getHeight() + 20);

      }
    });

    int c = 0;
    for (Card card : hand)
    {
      ImageView iv = new ImageView();

      iv.setImage(card.getImage());

      if (c > 0)
      {
        iv.setX(c * card.getImage().getWidth() / 4);
      }
      iv.setY(0);

      iv.setOnMouseClicked(event -> System.out.println(card.toString()));

      handGroup.getChildren().add(iv);
      if (new Random().nextBoolean())
      {
        int rand = new Random().nextInt(regionMap.keySet().size());
        Region p = (Region) regionMap.values().toArray()[rand];
        p.getDraftedCards().add(card);
      }
      c++;
    }
  }

  private Region getRegion(MouseEvent event)
  {
    Region _r = null;
    for (Polygon region : regionMap.keySet())
    {
      Shape s = Shape.intersect(region, new Ellipse(event.getX(), event.getY(), 1, 1));
      if (s.getBoundsInLocal().getWidth() != -1)
      {
        _r = regionMap.get(region);

      }
    }
    return _r;
  }

  private void startPolicyAnimation()
  {
    new Thread(() -> {
      while (!Thread.interrupted())
      {
        graphicsData.setStroke(new Color(255 / 255, 121 / 255, 105 / 255,
                                         oscillator(currentOpacity)));
        currentOpacity += .1;
        if (currentOpacity >= Math.PI * 2)
        {
          currentOpacity = 0;
        }
        graphicsData.clearRect(0, 0,
                               mapDataCanvas.getWidth(),
                               mapDataCanvas.getHeight());

        int i = 1;
        for (Region region : regionMap.values())
        {

          graphicsData.strokeText(region.name + ": " + region.getDraftedCards().size(),
                                  10, 25 * i);

          i++;
        }
        try
        {
          Thread.sleep(100);
        }
        catch (InterruptedException e)
        {
          e.printStackTrace();
        }
      }
    }).start();
  }

  private double currentOpacity = 0.0;

  private double oscillator(double opacity)
  {
    return ((Math.sin(opacity) * 1.0) + 1) / 2;

  }

  private void initalizeRegions()
  {

    Polygon cal = new Polygon();
    cal.getPoints().addAll(Reader.readFile(getClass().getResourceAsStream("../data/cal.csv")));

    Polygon mtn = new Polygon();
    mtn.getPoints().addAll(Reader.readFile(getClass().getResourceAsStream("../data/mtn.csv")));

    Polygon s = new Polygon();
    s.getPoints().addAll(Reader.readFile(getClass().getResourceAsStream("../data/s.csv")));

    Polygon n = new Polygon();
    n.getPoints().addAll(Reader.readFile(getClass().getResourceAsStream("../data/n.csv")));

    Polygon ne = new Polygon();
    ne.getPoints().addAll(Reader.readFile(getClass().getResourceAsStream("../data/ne.csv")));

    Polygon cen = new Polygon();
    cen.getPoints().addAll(Reader.readFile(getClass().getResourceAsStream("../data/cen.csv")));

    Polygon se = new Polygon();
    se.getPoints().addAll(Reader.readFile(getClass().getResourceAsStream("../data/se.csv")));


    regionMap.put(mtn, new Region("Pacific Northwest & Mountain States",
                                  "../images/mtn.png"));

    regionMap.put(cal, new Region("California",
                                  "../images/cal.png"));

    regionMap.put(s, new Region("Southern Plains & Delta States",
                                "../images/s.png"));

    regionMap.put(n, new Region("Northern Plains",
                                "../images/n.png"));

    regionMap.put(cen, new Region("Heartland",
                                  "../images/cen.png"));

    regionMap.put(se, new Region("Southeast",
                                 "../images/se.png"));

    regionMap.put(ne, new Region("Northern Crescent",
                                 "../images/ne.png"));

    clickedRegion = regionMap.get(mtn);
    graphics.drawImage(clickedRegion.getImage(), 0, 0);
  }
}

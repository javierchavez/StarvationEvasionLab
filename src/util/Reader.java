package util;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Reader to parse .csv data files.
 */
public class Reader
{
  private static Reader ourInstance = new Reader();

  public static Reader getInstance()
  {
    return ourInstance;
  }

  private Reader()
  {
  }

  /**
   * Read in a .csv data file.
   *
   * File must be in the format:
   *   xx.xx,xx.xx\n
   *   xx.xx,xx.xx\n
   *
   *
   * @param is  Stream of the csv file
   * @return Collection of points
   */
  public static Collection<Double> readFile(InputStream is)
  {
    ArrayList<Double> points = new ArrayList<>();
    BufferedReader reader = new BufferedReader(new InputStreamReader(is));
    String line;
    try
    {
      while ((line = reader.readLine()) != null) {
        String[] _data = line.split(",");
        points.add(Double.parseDouble(_data[0]));
        points.add(Double.parseDouble(_data[1]));
      }
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
    return points;
  }
}
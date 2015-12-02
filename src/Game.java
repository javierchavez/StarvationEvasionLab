import controller.MainFrameController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * Beginning of the application
 */
public class Game extends Application
{

  private MainFrameController controller;
  private boolean running = true;


  @Override
  public void start(Stage primaryStage) throws Exception
  {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("view/mainframe.fxml"));
    BorderPane pane = loader.load();


    controller = loader.getController();

    primaryStage.setScene(new Scene(pane));
    primaryStage.setTitle("Starvation Evasion");
    primaryStage.show();
    run();

    primaryStage.setOnCloseRequest(event -> running = false);
  }


  private void run()
  {
    new Thread(() -> {
      Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
      Thread.currentThread().setName("GameThread");
      long delta = 0l;


      while (running)
      {
        long lastTime = System.nanoTime();
        controller.update(delta / 1000000000.0f);

        delta = System.nanoTime() - lastTime;
        if (delta < 20000000L)
        {
          try
          {
            Thread.sleep((20000000L - delta) / 1000000L);
          }
          catch (Exception e)
          {
          }
        }
      }
    }).start();

  }


  public static void main(String[] args)
  {
    launch(args);
  }
}

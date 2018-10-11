package utils;

import java.io.IOException;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class Context implements ServletContextListener {

  public static Log log = new Log();
  public static Config config = new Config();

  /** This method is called when the context is first started */
  @Override
  public void contextInitialized(ServletContextEvent servletContextEvent) {

    // We init config in order to read the file and set all the variables.
    try {
      config.initializeConfig();
    } catch (IOException e) {

      System.out.println("Can't read config");
      log.writeLog(this.getClass().getName(), this, "We are now including the config file", 2);

      e.printStackTrace();
    }

    // Here we can initialize our Logger class and write to our Logging.txt that the system has been
    // started
    System.out.println("Context is open");

    // Write to log that we've started the system
    log.writeLog(this.getClass().getName(), this, "We've started the system", 2);
  }

  @Override
  public void contextDestroyed(ServletContextEvent servletContextEvent) {

    // Logging for when the system is stopped
    System.out.println("Context is closed");

    // Write to log that we've closed the system
    log.writeLog(this.getClass().getName(), this, "The system has been stopped", 2);
  }
}

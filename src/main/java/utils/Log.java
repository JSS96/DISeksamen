package utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class Log {

  /**
   * Method responsible for writing the actual log with a switch for different events
   *
   * @param className name of Class to be logged in
   * @param eventObject the object which the event occurs "in"
   * @param eventDescription description of what happens at the event in question (e.g. "Server
   *     printed: ---"
   * @param logLevel the level of logging needed, from everything to the most severe (used for the
   *     switch)
   */
  public static void writeLog(
      String className, Object eventObject, String eventDescription, Integer logLevel) {

    // Initializes the log variable with the class in question
    Logger log = LoggerFactory.getLogger(className);

    // Switch responsible for choosing the correct logging according to severity
    switch (logLevel) {
      case 2:
        log.debug(eventDescription, eventObject);
        break;
      case 1:
        log.error(eventDescription, eventObject);
        break;
      case 0:
        log.info(eventDescription, eventObject);
        break;
      default:
        log.info(eventDescription, eventObject);
        break;
    }
  }
}

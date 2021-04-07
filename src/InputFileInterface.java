import java.util.List;

/**
 * Interfata  care contine lista de metode
 *        folosite in Clasa FREE_TIME
 */
public interface InputFileInterface {
      List<IntervalHours> computeIntervalHoursCalendar(String line) throws CalendarException;
      IntervalHours computeRangeHoursCalendar(String line) throws CalendarException;
      Boolean findErrorArray (String line) throws CalendarException;
      Boolean findIntervalError(String line) throws  CalendarException;
      Boolean noErrors(IntervalHours intervalHours,List<IntervalHours> intervalHoursList);
      Boolean OutOfRange(String s) throws CalendarException;
      Character ContainsJustDigit(String s) throws CalendarException;
}

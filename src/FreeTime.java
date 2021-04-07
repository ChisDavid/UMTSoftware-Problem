import javax.swing.*;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

/**
    Clasa care are ca vriabila instanta un obiect de tipul input
                in care se realizeaza citirea din fisier si se stocheaza datele in 2 calendare
    si 2 liste cu freeTimecalendare  ->  intervalele orare in care persoanele sunt libere
 */
public class FreeTime {

    private Input input ;
    private List <IntervalHours> freeTimeCalendar1 ;
    private List <IntervalHours> freeTimeCalendar2;

    /**
     *      Se instantiaza clasa Input
     *      si se stocheaza in cele 2 liste intervalele orare in care fiecare persoana este libera
     */
    public  FreeTime()  {
        try {
            input = new Input("Input.txt");
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null,"File Not Found");
            System.exit(0);
            e.printStackTrace();
        } catch (CalendarException e) {
            e.printStackTrace();
        }
        if(this.getFreeTime(input.getCalendar1())!=null)
        {
            freeTimeCalendar1 = this.getFreeTime(input.getCalendar1());
        }
        if(this.getFreeTime(input.getCalendar2())!=null)
        {
            freeTimeCalendar2 = this.getFreeTime(input.getCalendar2());
        }
    }

    /**
     *
     * @param calendar
     * @return
     *          pentru un calendar lista de intervale orare in care persoana persoana poate face un meeting
     */
    public List<IntervalHours> getFreeTime(Calendar calendar)
    {
        if (calendar==null)
        {
            return null;
        }
        Hour begin = calendar.getRange().getStartTime();
        List<IntervalHours> freeTime =  new ArrayList<>();
        for(IntervalHours i : calendar.getIntervalList())
        {
            if (i.getStartTime().compareHours(begin)!=0)
            {
                freeTime.add(new IntervalHours(begin,i.getStartTime()));
            }
            begin = i.getEndTime();
        }
        if (calendar.getRange().getEndTime().Diference(calendar.getIntervalList().get(calendar.getIntervalList().size()-1).getEndTime()).Diferit( new Hour(0,0)))
        {
            freeTime.add(new IntervalHours(calendar.getIntervalList().get(calendar.getIntervalList().size()-1).getEndTime(),calendar.getRange().getEndTime()));
        }
        return  freeTime;
    }

    /**
     *
     * @param interval1
     * @param interval2
     * @return
     *          returneaza intersectia dintre 2 intervale orare
     *          null in cazul in care cele 2 intervale nu se intersecteaza
     *
     *
     */
    public IntervalHours intersect(IntervalHours interval1,IntervalHours interval2){
        IntervalHours max = null;
        IntervalHours min = null;

        /**
         * Calculeaza max si min
         * min -> Intervalul de timp care incepe mai repede
         * max -> Intervalul de timp care incepe dupa intervalul
         */
      if (interval2.getStartTime().compareHours(interval1.getStartTime())!=1)
      {
          min = interval2;
          max = interval1;
      }
      else
      {
          min= interval1;
          max=interval2;
      }

   if (min.getEndTime().compareHours(max.getStartTime())!=1)
      {
          return null;
      }
      if (max.getStartTime().compareHours(min.getStartTime())==1)
      {
          if(max.getEndTime().compareHours(min.getEndTime())==-1)
          {
              return max;
          }
      }
          return new IntervalHours(max.getStartTime(),min.getEndTime());

    }

    /**
     *  se parcurge cele 2 liste de intervale orare libere pentru cele 2 persoane
     *  doar in cazul in care  ambele liste nu sunt nule
     *      se parcurg listele si se calcleaza intersectia dintre totate freeTimeIntervall urile
     *      si in cazul in care numarul de minute din intersectie depaseste numarul de minute predefinit in input (in fisier)
     *          atunci il adaugam in lista de intalniri
     *
     *
     *   Afisam lista de intalniri in cazul in care lista contine elemente
     *      si in cazul in care lista e goala nu se afisaza nimica
     *
     *
     */
    public void meetbetween()
    {
        ArrayList<IntervalHours> meet = new ArrayList<>();

        if (freeTimeCalendar1!=null && freeTimeCalendar2!=null &&freeTimeCalendar1.size()!=0 && freeTimeCalendar2.size()!=0 )
        {
            freeTimeCalendar1.stream().forEach(i->{
                freeTimeCalendar2.stream().forEach(j->{
                    IntervalHours intersect = intersect(i,j);
                    if (intersect !=null && intersect.getEndTime().Diference(intersect.getStartTime()).minutes()>=input.getMeetingTimeMinutes())
                    {
                        meet.add(intersect(i,j));
                    }
                });
            });
        }



        if (meet.size()!=0)
        {
            System.out.println("\n\nCele 2 persoane se pot intalii intre orele\n\n");
            meet.stream().forEach(i-> System.out.println(i) );
        }
        else
        {
            System.out.println("Cele 2 persoane nu se pot intalnii "+input.getMeetingTimeMinutes()+" de minute\n" +
                    "din cauza Calendarului");
        }
    }
    public List<IntervalHours> getFreeTimeCalendar1() {
        return freeTimeCalendar1;
    }
    public List<IntervalHours> getFreeTimeCalendar2() {
        return freeTimeCalendar2;
    }
}

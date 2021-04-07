import java.util.List;

/**
 * Aceasta clasa modeleaza un calendar
 * si are ca variabile instanta
 *           ->   o lista de intervale orare in care persoana isi desfasoara unele activitati
 *           ->   si un Interval Orar care o sa reprezinte range-ul intre care persoana isi
 *                desfasoara activitatile
 */
public class Calendar {
    private List<IntervalHours> intervalList;
    private IntervalHours Range;

    public Calendar(List<IntervalHours> intervalList, IntervalHours range) {
        this.intervalList = intervalList;
        Range = range;
    }
    public List<IntervalHours> getIntervalList() {
        return intervalList;
    }
    public void setIntervalList(List<IntervalHours> intervalList) {
        this.intervalList = intervalList;
    }
    public IntervalHours getRange() {
        return Range;
    }
    public void setRange(IntervalHours range) {
        Range = range;
    }
    @Override
    public String toString() {
        String intervallist ="";
        if (intervalList.size()!=0)
        {
            for (IntervalHours  intervalHours: intervalList)
            {
                intervallist+= intervalHours.toString();
                intervallist+="\n";
            }
        }
        return "Calendar{" +
                "intervalList=" + intervallist +
                ", Range=" + Range +
                '}';
    }
}

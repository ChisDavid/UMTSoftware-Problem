/**
 * O Clasa care modeleleaza un interfal orar
 * care are un StartTime si un EndTime cand incepe o activitate
 * De exemplu persoana X are o activitate Y de la StartTime la EndTime
 */

public class IntervalHours {
    private Hour StartTime;
    private Hour EndTime;

    public IntervalHours(Hour startTime, Hour endTime) {
        StartTime = startTime;
        EndTime = endTime;
    }

    public Hour getStartTime() {
        return StartTime;
    }

    public void setStartTime(Hour startTime) {
        StartTime = startTime;
    }

    public Hour getEndTime() {
        return EndTime;
    }

    public void setEndTime(Hour endTime) {
        EndTime = endTime;
    }

    @Override
    public String toString() {
        return "{" +
                "StartTime=" + StartTime .toString() +
                ", EndTime=" + EndTime .toString()+
                '}';
    }
}

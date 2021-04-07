import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Period;
import java.util.SortedMap;


public class Hour {
    private Integer minutes;
    private Integer hour;
    public Hour()
    {

    }
    public Hour( Integer hour,Integer minutes) {
        this.minutes = minutes;
        this.hour = hour;
    }

    @Override
    public String toString() {
        return "Hour{" +
                "hour=" + hour +
                ", minutes=" + minutes +

                '}';
    }

    /**
     *
     * @param hour
     * @return 1 daca this > hour 0 daca this == hour -1 daca this<hour
     * Comparam this.hour cu hour.hour
     *          Daca < returnam -1
     *          Daca > returnam 1
     *          Daca =   Comparam minutele
     *              Daca < returnam -1
     *              Daca > returnam 1
     *              Daca = returnam =
     */
    public int compareHours(Hour hour)
    {
        if (this.hour<hour.getHour())
        {
            return -1;
        }
        if (this.hour>hour.getHour())
        {
            return 1;
        }
        if (this.hour==hour.getHour())
        {
            if (this.minutes>hour.minutes)
            {
                return 1;
            }
            if (this.minutes<hour.minutes)
            {
                return -1;
            }
            if (this.minutes==hour.minutes)
            {
                return 0;
            }
        }
        return 0;

    }

    /**
     *
     * @param hour
     * @return Diferenta de timp dintre 2 ore sub forma unei ore
     *    Exemplu   12:37 - 11:30  = 01:07
     *    Exemplu2  13:37 - 13 :30 =00:07
     *    Scadem din this ora hour (dupa ore si minute)
     */
    public Hour Diference (Hour hour)
    {
        int h1 = this.hour;
        int m1 = this.minutes;
        int h2 = hour.hour;
        int m2 = hour.minutes;
        if (h1>=h2+1)
        {
            if (m1>m2)
            {
                return new Hour(h1-h2,m1-m2);
            }
            else {

                return new Hour(h1-h2-1,m1+60-m2);
            }
        }
        if (h1==h2)
        {
            if (m1>m2)
                return new Hour(0,m1-m2);
            else
                return new Hour(0,m2-m1);
        }
        return new Hour(0,0);
    }

    /**
     *
     * @return
     * Intervalul de timp corespunzator lui this
     * in minute
     * Exemplu this = 01:37  se returneaza 97 minute (60+37)
     */
    public Long minutes()
    {
        Long nr = 0L;
        int hour = this.hour;
        int min = this.minutes;
        while(hour!=0)
        {
            hour--;
            nr+=60;
        }
        nr+=this.minutes;
        return nr;
    }

    /**
     *
     * @param h  o ora (cu minute si secunde)
     * @return  true daca cele this /= h  si true daca this==h
     */
    public Boolean Diferit(Hour h)
    {
        if (this.hour!=h.getHour() && this.minutes!=h.getMinutes())
        {
            return Boolean.TRUE;
        }
        return false;
    }


    /**
     *
     * @return this.minutes
     */

    public Integer getMinutes() {
        return minutes;
    }

    /**
     *
     * @param minutes seteaza this.minutes cu valoarea respectiva
     */
    public void setMinutes(Integer minutes) {
        this.minutes = minutes;
    }

    /**
     *
     * @return this.hour
     * Example  this = 15:47  se returneaza 15
     */
    public Integer getHour() {
        return hour;
    }

    /**
     *
     * @param hour
     * se seteaza ora din this
     * this = 12:56  si se modifica ora12 cu paramentrul
     */
    public void setHour(Integer hour) {
        this.hour = hour;
    }




}

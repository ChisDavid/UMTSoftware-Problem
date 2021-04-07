import javax.swing.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.SortedMap;

/**
 * Aceasta Clasa modeleaza Inputul aplicatiei astfel are ca
 * variabile instanta
 *      un obiect de tipul inputStream (fisierul)
 *      e obiecte de tip Calendar in care o sa se salveze cele 2 calendare citite din fisier
 *      un obiect de tip integer in care o sa stocam numarul de minute corespunzatoare intalnirii dintre cele 2 persoane
 */

public class Input implements InputFileInterface{
    private InputStream inputStream;
    private Calendar Calendar1;
    private Calendar Calendar2;
    private Integer MeetingTimeMinutes = 0;
    /**
     *
     * @param fileName numele Fisierului pentru input Din care o sa se citeasca cele 2 calendare
     *
     * @throws FileNotFoundException
     * in cazul in care Fisierul nu o sa fie gasit Constructorul arunca o exceptie
     * de tipul FileNotFoundException
     *
     * @throws CalendarException
     * constructorul returneaza Erori de tipul CalendarException
     *
     */
    public  Input (String fileName) throws FileNotFoundException, CalendarException {
        /**
         * Deschidem fisierul
         */
        inputStream = new FileInputStream(fileName);
        Scanner scanner = new Scanner(inputStream);


         List<IntervalHours> CalendarList1 = new ArrayList<>();
         List<IntervalHours> CalendarList2 = new ArrayList<>();


         String CalendarString1  = scanner.nextLine();
        /**
         * In cazul in care prima linie din fisier corespunzatoare primului calendar
         *      este nula sau goala atunci o sa se arunce o exceptie
         *      si exceptia o sa fie prinsa
         *
         *      Si programul o sa se termine
         */
        if (CalendarString1 == null || CalendarString1.length()==0)
        {
            JOptionPane.showMessageDialog(null,"Calendar 1 invalid \n" +
                    "Linia 1 corespunzatoare calendarului 1 din fisier este goala");
            System.exit(0);
        }

        /**
         *
         * In cazul in care Stringul este valid o sa se apeleze metoda ComputeHoursCalendar care
         * o sa adauge in lista CalendarList1 intervalele orare in care persoana are
         * unele activitati
         *
         */
        try{
            CalendarList1 = computeIntervalHoursCalendar(CalendarString1);;
        }catch (CalendarException e)
        {
            Calendar1=null;
        }


        String RangeCalendar1 = scanner.nextLine();


        /**
         * In cazul in care a 2 linie din fisier este nula sau goala
         * o sa se arunce o exceptie de tipul CalendarException
         * aceasta exceptie o sa fie prinsa
         *
         * si programul o sa se Termine
         */

        if (RangeCalendar1==null || RangeCalendar1.length()==0)
        {
            JOptionPane.showMessageDialog(null,"Calendar 1 invalid \n" +
                    "Linia 2 Corespunzatoare intervalului orar al calendarului 2 este goala");
            System.exit(0);
        }


        /**
         * Decodificam linia citita si returnam in intervalHour
         */
        IntervalHours intervalCalendar1 = computeRangeHoursCalendar(RangeCalendar1);


        /**
         * In cazul in care nu avem erori se creeaza un obiect de tipul Calendar1
         */

       if (noErrors(intervalCalendar1,CalendarList1))
         {
             Calendar1 = new Calendar(CalendarList1,intervalCalendar1);
         }



         String CalendarString2  = scanner.nextLine();
        /**
         * In cazul in care linia corespunzatoare calendarului2 este nula
         * se arunca o exceptie si programul se termina
         */
        if (CalendarString2 == null || CalendarString2.length()==0)
        {
            JOptionPane.showMessageDialog(null,"Calendar 2 invalid \n" +
                    "Linia 3 corespunzatoare calendarului 2 din fisier este goala");
            System.exit(0);
        }


        /**
         * in cazul in care linia CalendarString2 nu arunca Exceptii
         * se introduce in lista de IntervalHours intervalele
         * altfel ramane NULL
         */
        try{
         CalendarList2 = computeIntervalHoursCalendar(CalendarString2);
       }catch (CalendarException e)
       {
           Calendar2=null;
       }
         String RangeCalendar2 = scanner.nextLine();
        /**
         * In cazul in care linia corespunzatoare Range-ului calendarului 2 este nula
         * sau are lungimea de 0 atunci
         * o sa se arunce o exceptie
         *
         * si programul o sa se termine
         */
        if (RangeCalendar2==null || RangeCalendar2.length()==0)
        {
            JOptionPane.showMessageDialog(null,"Calendar 2 invalid \n" +
                    "Linia 4 Corespunzatoare intervalului orar al calendarului 2 este goala");
            System.exit(0);
        }

        IntervalHours intervalCalendar2 = computeRangeHoursCalendar(RangeCalendar2);
        /**
         * In cazul in care Dupa apelarea celor 2 metode nu exista erori
         * atunci se creeaza un nou Calendar
         *
         * altfel ramane null
         */
        if (noErrors(intervalCalendar2,CalendarList2))
         {
             Calendar2 = new Calendar(CalendarList2,intervalCalendar2);
         }



         String MeetingTimeMinutesString = scanner.nextLine();

        /**
         * In cazul in care linia corespunzatoare inervalului de timp este nula
         * programul o sa se termine
         */
         if (MeetingTimeMinutesString==null || MeetingTimeMinutesString.length()==0)
         {
             JOptionPane.showMessageDialog(null,
                         "Linia 5 numarului de minute al intalnirii este goala ");
             System.exit(0);

         }
        /**
         * In cazul in care linia corespunzatoare numarului de minute al intalnirii dintre cele 2 persoane
         * contine si alte caractere inafara de Cifre
         * programul o sa se termine deoarece
         *          numarul de minute trebuie sa fie format din cifre
         */
        for (Character i :  MeetingTimeMinutesString.toCharArray())
         {
             if (!Character.isDigit(i))
             {
                 JOptionPane.showMessageDialog(null,
                         "Linia 5 corespunzatoare numarului de minute al intalnirii nu contine doar cifre \n" +
                                 "Contine si caracterul " +"\'"+i+"\'");
                 System.exit(0);
             }
         }

        MeetingTimeMinutes = Integer.parseInt(MeetingTimeMinutesString);
    }

    /**
     *
     * @param line linia  (lista de intervale orare) corespunzatoare calendarului
     * @return o lista de IntervalHours in care sunt Stocate toate activitatile din calendarul unei persoane
     * @throws CalendarException in cazul in care se gaseste vreo greseala in string
     *
     *          In cazul in care o ora nu este in intervalul 0-23
     *          In cazul in care minutul nu se afla in intervalul 0-59
     *          in cazul in care se gasesc caractere eronate ( care nu sunt numere de exemplu *( ){ } / - + .,#$ )
     *          In cazul in care 2 activitati se suprapun de exemplu
     *                      {12:40 , 15:50} {15:30 , 18:00} -> deoarece o activitatea 2 nu poate sa inceapa in cazul in care
     *                              una din activitatile anterioare nu s-au terminat
     *
     *
     */
    @Override
    public List<IntervalHours> computeIntervalHoursCalendar(String line) throws CalendarException {
        try{
            if (findErrorArray(line))
            {
                return null;
            }

        }catch (CalendarException e)
        {
            return null;
        }
        List<IntervalHours> list = new ArrayList<>();
        String []interval = line.split(", ");
        int size  = interval.length;

        interval[0] = interval[0].substring(1);
        interval[size-1]=interval[size-1].substring(0,interval[size-1].length()-1);
        Hour StartHour = new Hour();
        Hour EndHour = new Hour();

        for (String i : interval)
        {
            try{
            if  (findIntervalError(i)!=false)
            {
                    return null;
            }
            }catch (CalendarException e)
            {
                return null;
            }
            i = i.replace("[","");
            i = i.replace("]","");
            i = i.replace("'","");
            String [] hours = i.split(",");
            try{
                if (OutOfRange(hours[0])!=false)
                {
                    return null;
                }
                if (OutOfRange(hours[1])!=false)
                {
                    return null;
                }
            }
            catch (Exception e)
            {
                return null;
            }

            for (int j=0;j<2;j++)
            {
                if (!hours[j].contains(":"))
                {
                    JOptionPane.showMessageDialog(null,hours[j]+"nu contine caracterul :");
                    System.exit(0);
                }
                String []numbers = hours[j].split(":");
//
                Character error0 = ContainsJustDigit(numbers[0]);
                if (error0!=null)
                {
                    JOptionPane.showMessageDialog(null,numbers[0]+" contine un caracter invalid " +
                            "\' "+error0+'\'');
                    System.exit(0);
                }
                Character error1 = ContainsJustDigit(numbers[1]);
                if (error1!=null)
                {
                    JOptionPane.showMessageDialog(null,numbers[1]+" contine un caracter invalid " +
                            " \' "+error1+'\'');
                    System.exit(0);
                }
                Integer Hournumber = Integer.parseInt(numbers[0]);
                Integer Minutenumber = Integer.parseInt(numbers[1]);
                if (Hournumber<0 || Hournumber>23)
                {
                    throw new CalendarException("Hour "+ Hournumber + "out of range 0-23");
                }
                if (Minutenumber<0 || Minutenumber>59)
                {
                    throw new CalendarException("Minute "+ Minutenumber + "out of range 0-59");
                }
                if (j==0)
                {
                    StartHour = new Hour(Hournumber,Minutenumber);
                }
                else {
                    EndHour = new Hour(Hournumber,Minutenumber);
                }
            }
            IntervalHours intervalHours = new IntervalHours(StartHour,EndHour);
            list.add(intervalHours);
        }
        IntervalHours i = list.get(0);


        /**
         * in cazul in care o activitate incepe inainte ca o alta activitate anterioara
         *      sa se fii terminat se arunca o exceptie
         */
        for (int  j=1;j<list.size();j++)
        {
              if (list.get(j).getStartTime().compareHours(i.getEndTime())==-1)
            {
                throw new CalendarException("Calendar INVALID  \n" +
                        " deoarece\n" +
                        "activitatea \n"+list.get(j)+"\n" +
                        " incepe inainte ca activitatea\n" + i +"\n" +
                        "sa se fii terminat");
            }
            i = list.get(j);
        }
       return list;
    }

    /**
     *
     * @param line linia in care se poate gasii erori pentru RANGE ul unui calendar
     * @return false in cazul in care nu se gasesc erori
     * @throws CalendarException
     *              in cazul in care lista nu incepe cu caracterul [
     *              in cazul in care lista nu se termina cu caracterul ]
     *              in cazul in care intervalele orare nu sunt separate prin caracterul ,
     *
     */
    @Override
    public Boolean findErrorArray(String line) throws CalendarException {
        if (line.length()==0)
            return false;
        if (line.toCharArray()[0]!='[')
        {
            throw new CalendarException("The list does not begin with [ ");
        }
        if (line.toCharArray()[line.length()-1]!=']')
        {
            throw new CalendarException("The list does not ends with ] ");
        }
        if (!line.contains(", "))
        {
            throw new CalendarException("The intervals hours are not separated by  \", \" ");
        }
        return false;
    }

    /**
     *
     * @param line
     * @return false in cazul in care nu se gasesc erori de sintaxa
     * @throws CalendarException
     *              in cazul in care linia nu contine linia  ,
     *              in cazul in care orele nu sunt delimitate de caracterul '
     *              in cazul in care ora si minutul nu sunt departite prin caracterul :
     *              in cazul in care intervalul orar Begin End nu sunt delimitate intre caracterele [ ]
     *              in cazul in care se gasesc caractere invalide
     *              in cazul in care BeginTime este mai mare decat EndTime intr un interval
     *
     */
    @Override
    public Boolean findIntervalError(String line) throws CalendarException {


        if (!line.contains(","))
        {
            throw new CalendarException(line + " not contains , ");
        }
        if (!line.contains("'"))
        {
            throw new CalendarException(line + " not contains ' ");
        }
        if (!line.contains(":"))
        {
            throw new CalendarException(line + " not contains : ");
        }
        if (!line.contains("["))
        {
            throw new CalendarException(line + " not contains [ ");
        }
        if (!line.contains("]"))
        {
            throw new CalendarException(line + " not contains ] ");
        }
        String l = line;
        l = l.replace("[","");
        l = l.replace("]","");
        l = l.replace("'","");
        String []str = l.split(",");
        int ok=0;
        Hour h1 = new Hour();
        Hour h2 = new Hour();
        for(String i : str)
        {
            if (!i.contains(":"))
            {
                JOptionPane.showMessageDialog(null,"in "+ i+"  ora si minutul nu sunt desparite de caracterul \' : \'");
                System.exit(0);
            }
            String [] number = i.split(":");

            Character error0 = ContainsJustDigit(number[0]);
            if (error0!=null)
            {
                JOptionPane.showMessageDialog(null,number[0]+" contine un caracter invalid " +
                        "\'"+error0+'\'');
                System.exit(0);
            }
            Character error1 = ContainsJustDigit(number[1]);
            if (error1!=null)
            {
                JOptionPane.showMessageDialog(null,number[1]+" contine un caracter invalid  " +
                        "\'"+error1+'\'');
                System.exit(0);
            }
            if (ok==0)
            {
                ok++;
                 h1  = new Hour(Integer.parseInt(number[0]),Integer.parseInt(number[1]));
            }
            else
            {
                 h2  = new Hour(Integer.parseInt(number[0]),Integer.parseInt(number[1]));
            }
        }

        if (h1.compareHours(h2) == 1 || h1.compareHours(h2) ==0)
        {
            throw  new CalendarException("In "+line+ "Start Time "+str[0]+" is bigger then End Time"+str[1]);
        }

        return false;
    }


    /**
     * @param line String ul care reprezinta range ul unui calendar
     * @return un intervalHour care reprezinta Range-ul intre care persoana are activitati
     *
     */

    @Override
    public IntervalHours computeRangeHoursCalendar(String line){

        try{
            if (findIntervalError(line)!=false)
            {
                return null;
            }
        }
        catch (Exception e)
        {
            JOptionPane.showMessageDialog(null,e);
            return null;
        }

        line = line.replace("'","");
        line = line.replace("[","");
        line = line.replace("]","");

        String [] stringHours  = line.split(",");
        int odd = 0;
        Hour BeginHour = new Hour();
        Hour EndHour = new Hour();
        for (String i : stringHours)
        {

            if (!i.contains(":"))
            {
                JOptionPane.showMessageDialog(null,"in "+i  +" ora si minutul nu sunt separate prin :");
                System.exit(0);
            }
            String [] numbers = i.split(":");
            if (odd==0)
            {
                BeginHour = new Hour(Integer.parseInt(numbers[0]),Integer.parseInt(numbers[1]));
                odd=1;
            }
            else
            {
                EndHour = new Hour(Integer.parseInt(numbers[0]),Integer.parseInt(numbers[1]));
                odd=0;
            }
        }
        return new IntervalHours(BeginHour,EndHour);
    }

    /**
     *
     * @param intervalHours Range - ul  in care persoana are activitati
     * @param intervalHoursList Lista de intervalHours de activitati
     * @return
     *          True in cazul in care nu sunt erori
     *          False in cazul in care
     *              intervalul orar este null
     *              lista de intervale este nulla sau goala
     *              in cazul in care  activitatea 0 incepe inainte de de ora de incepere a activitatilor
     *              daca o activitate incepe inainte de Range
     *              in cazul in care ultima activitate se termina dupa EndTime din range
     *
     */
    @Override
    public Boolean noErrors(IntervalHours intervalHours, List<IntervalHours> intervalHoursList) {
        if (intervalHours==null || intervalHoursList ==null || intervalHoursList.size()==0)
        {
            return false;
        }
        int ok = intervalHours.getStartTime().compareHours(intervalHoursList.get(0).getStartTime()) ;
        if (ok ==-1)
        {
            return false;
        }
        int ok1=intervalHours.getEndTime().compareHours(intervalHoursList.get(intervalHoursList.size()-1).getEndTime());

        if (ok1==-1)
        {
            return false;
        }
        return true;
    }

    /**
     *
     * @param s reprezinta o ora
     * @return False in care in string nu sunt erori
     * @throws CalendarException
     *          in cazul in care minutul nu este in intervalul 0 59 se arunca o exceptie
     *          in cazul in care ora nu este in intervalul 0 23 se arunca o exceptie
     *          in cazul in care ora si minutul nu sunt delimitate orin :
     */
    @Override
    public Boolean OutOfRange(String s) throws CalendarException {
        if (!s.contains(":"))
        {
            throw new CalendarException("Ora si minutul nu sunt delimitate prin caracterul :");
        }
        String []split =s.split(":");
        int hour = Integer.parseInt(split[0]);
        int minute = Integer.parseInt(split[1]);
        if (hour <0 || hour >23 )
        {
            throw new CalendarException("Hour "+hour +" is out of range 0-23");
        }
        if (minute <0 || minute >59 )
        {
            throw new CalendarException("Minute "+minute +" is out of range 0-59");
        }
        return false;
    }

    /**
     *
     * @param s
     * @return
     *              null in cazul in care avem doar cifre
     *              si returneaza oricare CAracter care nu este cifra in cazul in care el exista
     *
     */
    @Override
    public Character ContainsJustDigit(String s)  {
        for (Character i : s.toCharArray())
        {
            if (!Character.isDigit(i))
            {
                return i;
            }
        }
        return null;
    }
    public Integer getMeetingTimeMinutes() {
        return MeetingTimeMinutes;
    }

    public Calendar getCalendar1() {
        return Calendar1;
    }

    public Calendar getCalendar2() {
        return Calendar2;
    }


}

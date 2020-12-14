package com.company;
import com.company.Main;

/**
 * <code>Date</code> class provides methods for storing and manipulating calendar fields.
 * It can be used to calculate week day for a specified date or quickly move in future or past using <code>nextWeek()</code> and <code>lastWeek()</code> methods.
 *
 */
class Date implements Comparable<Date>{
    /**
     * Value of the {@link #getDate(int)} method, long format example: Monday, 1 January 1970
    */
    public static final int FORMAT_LONG = 911261712;
    /**
     * Value of the {@link #getDate(int)} method, classic format example: 1 January 1970
     */
    public static final int FORMAT_CLASSIC = -26112566;
    /**
     * Value of the {@link #getDate(int)} method, roman format example: 01.I.1970
     */
    public static final int FORMAT_ROMAN = 666217956;
    /**
     * Value of the {@link #getDate(int)} method, shorty format example: 1-Jan-1970
     */
    public static final int FORMAT_SHORT = -126899233;

    Months months = new Months();
    private int day;
    private Month month;
    private int year;
    private boolean leap_year;
    private int weekday;

    /**
     * <code>Date(int day, int month, int year)</code>
     * Constructs a <code>Date</code> object for specified date.
     * @param day day of the date
     * @param month month number
     * @param year year of the date
     * @throws IllegalArgumentException
     *      <br>when month is out of 1 to 12 range <br>
     *      when day doesn`t exist on calendar with given month and year
     */
    public Date(int day,int month,int year) throws IllegalArgumentException{
        if(month > 12 || month < 1)
        {
            throw new IllegalArgumentException("Month must be within range of 1 to 12");
        }
        this.month = months.getMonth(month);
        this.year = year;
        leap_year = this.getLeapYear();
        if((day > this.month.getHowManyDays() && !(day == 29 && leap_year)) || day < 1)
        {
            throw new IllegalArgumentException("Day is incorrect");
        }
        this.day = day;
        weekday = this.calcWeekday();
    }

    /**
     * <code>Date(int day, int year)</code>
     * Constructs a <code>Date</code> object with 2 parameters - days passed since 1 January of <code>year</code>.
     * @param days days since 1 January
     * @param year year of the date
     */
    public Date(int days,int year){
        int days_passed=0;
        int i=1;
        while (days_passed<days)
        {
            days_passed += months.getMonth(i).getHowManyDays();
            i++;
        }
        this.month = months.getMonth(i-1);
        days = month.getHowManyDays() + (days - days_passed);
        this.day = days;
        this.year = year;
        leap_year = this.getLeapYear();
        weekday = this.calcWeekday();
    }

    /**
     * <code>public Date()</code>
     * Default constructor a <code>Date</code> object with unix epoch start date - 1 January 1970.
     */
    public Date()
    {
        this.day = 1;
        this.month = months.getMonth(1);
        this.year = 1970;
        leap_year = this.getLeapYear();
        weekday = this.calcWeekday();
    }

    /**
     * <Code>public void nextWeek()</Code><br>
     * Moves date in <code>Date</code> object one week forward.
     */
    public void nextWeek()
    {
        int howManyDays = month.getHowManyDays();
        if(month.getNumber() == 2 && leap_year)
        {
            howManyDays++;
        }
        day += 7;
        if(day > howManyDays)
        {
            day = day - howManyDays;
            if(month.getNumber()+2 > 12)
            {
                year++;
                month = months.getMonth(1);
            }
            else
            {
                month = months.getMonth(month.getNumber()+1);
            }
        }
    }
    /**
     * <Code>public void lastWeek()</Code><br>
     * Moves date in <code>Date</code> object one week back.
     */
    public void lastWeek(){
        int howManyDays = month.getHowManyDays();
        if(month.getNumber() == 2 && leap_year)
        {
            howManyDays++;
        }
        day -= 7;
        if(day < 0)
        {
            if(month.getNumber()-1 < 1)
            {
                year--;
                month = months.getMonth(12);
            }
            else
            {
                month = months.getMonth(month.getNumber()-1);
            }
            day = day + howManyDays;
        }
    }

    /**
     * <code>public int calcWeekday()</code><br>
     * Method calculates weekday for any <code>Date</code> object using modulo algorithm.
     * @return int in range 0 to 6, 0 being monday and 6 being sunday.
     */
    public int calcWeekday(){
        int dayspassed=0;
        int yy = (this.year - 1) % 100;
        int c = (this.year - 1) - yy;
        int g = yy + (yy/4);
        int weekday = (((((c/100) % 4) * 5) + g) % 7);
        for(int i=1;i<this.month.getNumber();i++)
        {
            dayspassed += this.months.getMonth(i).getHowManyDays();
        }
        if(this.leap_year && dayspassed >= 60)
        {
            dayspassed++;
        }
        dayspassed += this.day;
        weekday += dayspassed - 1;
        weekday %= 7;
        return weekday;
    }
    /**
     * <code>public int calcWeekdayReferential()</code><br>
     * Method calculates weekday for any <code>Date</code> object using reference.
     * @return int in range 0 to 6, 0 being monday and 6 being sunday.
     */
    public int calcWeekdayReferential(){
        Date ref = new Date(1,1,2020);
        int diff = 0;
        if(compareTo(ref) == 1) {
            while(compareTo(ref) > 1){
                ref.lastWeek();
            }
            diff = (this.day - ref.day) % 7;
        }
        else if(compareTo(ref) == -1){
            while(compareTo(ref) < 1){
                ref.nextWeek();
            }
            diff = (this.day - ref.day) % 7;
        }
        return (ref.weekday + diff) % 7;
    }

    /**
     * <code>public boolean getLeapYear()</code><br>
     * @return true if date in <code>Date</code> object is an leap year.
     */
    public boolean getLeapYear()
    {
        return (year % 4 == 0 && year % 100 != 0) || year % 400 == 0;
    }

    /**
     * <code>public String getDate(int par)</code>
     * @param par parameter that defines date format returned by method.<br>
     * Allowed parameters are:
     * <ul>
     *            <li>{@link #FORMAT_ROMAN}</li>
     *            <li>{@link #FORMAT_LONG}</li>
     *            <li>{@link #FORMAT_CLASSIC}</li>
     *            <li>{@link #FORMAT_SHORT}</li>
     * </ul>
     * @return String value of <code>Date</code> object, with format specified by parameter.
     * @exception IllegalArgumentException
     * if par is not one of constants defined by class.
     */
    public String getDate(int par)
    {
        String date="";
        switch(par)
        {
            case 911261712:
                date = this.getWeekday() + ", " + day + " " + month.getName() + " " + year;
                break;
            case -26112566:
                date = day + " " + month.getName() + " " + year;
                break;
            case 666217956:
                if(day<10) {
                    date = "0";
                }
                date = date + day + "." + month.getRoman() + "." + year;
                break;
            case -126899233:
                date = day + "-" + month.getName().substring(0,3) + "-" + year;
                break;
            default:
                throw new IllegalArgumentException("Date parameter must be a certain format");
        }
        return date;
    }

    /**
     * <code>public String getWeekday()</code><br>
     * Method translates int values returned by {@link #calcWeekday()} and {@link #calcWeekdayReferential()} to String
     * @return String value of weekday
     * @exception IllegalArgumentException
     * If weekday is outside of range 0 to 6
     */
    public String getWeekday(){
        switch(this.weekday)
        {
            case 0:
                return "Poniedziałek";
            case 1:
                return "Wtorek";
            case 2:
                return "Środa";
            case 3:
                return "Czwartek";
            case 4:
                return "Piątek";
            case 5:
                return "Sobota";
            case 6:
                return "Niedziela";
            default:
                throw new IllegalArgumentException("Weekday must be within range 0 to 6");
        }
    }

    /**
     * <code>public int compareTo(Date date)</code>
     * Method compares <b>this</b> <code>Date</code> object to <code>Date</code> object sent as <b>parameter</b>.
     * @param date <code>Date</code> object to compare with this
     * @return -1 if <code>this</code> is <b>later</b> date than <code>date</code><br>
     *          0 if they are equal<br>
     *          1 if <code>this</code> is <b>earlier</b> date than <code>date</code>
     */
    public int compareTo(Date date){
        if(this.year > date.year) {return -1;}
        else if(this.year < date.year) {return 1;}
        else if(this.month.getNumber() > date.month.getNumber()) {return -1;}
        else if(this.month.getNumber() < date.month.getNumber()) {return 1;}
        else if(this.day > date.day) {return -1;}
        else if(this.day < date.day) {return 1;}
        else {return 0;}
    }

}
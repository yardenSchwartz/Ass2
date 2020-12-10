import Entities.Users;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


public class Assignment {


    public static void main(String[] args) throws ParseException {
//        int x = Integer.parseInt("05");
//        System.out.println(x);
//        Date date = new Date();
//        DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
//        String day_of_birth = "01";
//        String year_of_birth = "2010";
//        int monthNumber = 5;
////        String date_string = day_of_birth+"/"+"0"+monthNumber+"/"+year_of_birth;
//        date = format.parse(day_of_birth+"/"+"0"+monthNumber+"/"+year_of_birth);
//
//        Timestamp a = new Timestamp(date.getTime());
//        System.out.println(date);
//        System.out.println(a);

//        insertUser("sch", "1234", "yarden", "schwartz", "08", "September", "1995");
        String x = validateUser("sch","1234");
        System.out.println(x);
    }

    /**
     * Q 2.e
     * @param username
     * @return
     */
    private static boolean isExistUsername(String username) {
        return false;
    }


    /**
     * Q 2.f
     * @param username
     * @param password
     * @param first_name
     * @param last_name
     * @param day_of_birth
     * @param month_of_birth
     * @param year_of_birth
     * @return
     */
    public static String insertUser(String username, String password, String first_name, String last_name,
                                    String day_of_birth, String month_of_birth, String year_of_birth){
        //checking validation
        if( (Integer.parseInt(day_of_birth) > 0 && Integer.parseInt(day_of_birth) <= 31 ) &&
                (Integer.parseInt(year_of_birth) <= 2020) ){
            if(isExistUsername(username))
                return null;
            else{
                try {
                    //convert string of date to date format
                    Date date = new Date();
                    DateFormat format = new SimpleDateFormat("dd/MM/yyyy");

                    String month_Name_Convert_To_Number = isValidMonthName(month_of_birth);

                    if(month_Name_Convert_To_Number!=null){
                        date = format.parse(day_of_birth+"/"+month_Name_Convert_To_Number+"/"+year_of_birth);
                    }
                    else{
                        int monthNumber = Integer.parseInt(month_of_birth);
                        if(isValidMonthNumber(monthNumber)){
                            date = format.parse(day_of_birth+"/"+"0"+monthNumber+"/"+year_of_birth);
                        }
                        else{
                            return null; //invalid month
                        }
                    }

                    // insert the user to Users table
                    addLineToUsersTable(username, password, first_name, last_name, date);
                }
                catch (Exception e){
                    System.out.println(e);
                }
            }
        }
        else //not valid
            return null;

        return null;
    }


    /**
     * check if month_of_birth is legal name: January-December
     * if yes - return true, else return false
     * @param month_of_birth
     * @return
     */
    private static String isValidMonthName(String month_of_birth){

        try {
            //check if month_of_birth is name of month
            HashMap<String, String> MonthsNames=new HashMap<>();
            MonthsNames.put("January","01");
            MonthsNames.put("February","02");
            MonthsNames.put("March","03");
            MonthsNames.put("April","04");
            MonthsNames.put("May","05");
            MonthsNames.put("June","06");
            MonthsNames.put("July","07");
            MonthsNames.put("August","08");
            MonthsNames.put("September","09");
            MonthsNames.put("October","10");
            MonthsNames.put("November","11");
            MonthsNames.put("December","12");
            if (MonthsNames.containsKey(month_of_birth))
                return MonthsNames.get(month_of_birth);

        }
        catch (Exception e){
            System.out.println(e);
        }
        return null;
    }

    /**
     * check if month_of_birth is legal number: between numbers 1-12
     * if yes - return true, else return false
     * @param month_of_birth
     * @return
     */
    private static boolean isValidMonthNumber(int month_of_birth){

        try {
            //check if month_of_birth is type of number
            if(month_of_birth>0 || month_of_birth <=12)
                return true;
        }
        catch (Exception e){
            System.out.println(e);
        }
        return false;
    }

    /**
     * This Function gets valid strings and add new record to Users table
     * @param username
     * @param password
     * @param first_name
     * @param last_name
     * @param date
     */
    private static void addLineToUsersTable(String username, String password, String first_name, String last_name, Date date) {

        Session session = null;
        Users new_user = new Users();

        try{
            session=HibernateUtil.currentSession();
            new_user.setUsername(username);
            new_user.setPassword(password);
            new_user.setFirstName(first_name);
            new_user.setLastName(last_name);
            new_user.setDateOfBirth(new Timestamp(date.getTime()));
            new_user.setRegistrationDate(new Timestamp(System.currentTimeMillis()));

            Transaction transaction = session.beginTransaction();
            session.saveOrUpdate(new_user);
            transaction.commit();
        }
        catch (Exception e){
            System.out.println(e);
        }
        finally
        {
            HibernateUtil.closeSession();
        }


    }


    /**
     * Q 2.h
     * This function gets username and password and checks if they exist in Users table
     * @param username
     * @param password
     * @return USERID if the values are equal to the values in the table
     * otherwise return “Not Found”
     */
    public static String validateUser (String username, String password){

        Session session = null;
        List <Users> query_Ans = null;
        try
        {
            session=HibernateUtil.currentSession();
            String query = "select users from Users users where users.username='"+username+"' and users.password='"+password+"'";
            query_Ans = session.createQuery(query).getResultList();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            HibernateUtil.closeSession();
        }
        if(query_Ans.size()>0){
            long uId = query_Ans.get(0).getUserid();
            return String.valueOf(uId);
        }

        return "Not Found";
    }


    /**
     * Q 2.j
     * @param userid
     * @param mid
     */
    public static void insertToHistory (String userid, String mid){

    }

    /**
     * Q 2.l
     * @param userid
     */
    public static void insertToLog (String userid){

    }

    /**
     * Q 2.n
     * @return from the table Users all users
     */
    public static List<Users> getUsers (){
        List<Users> listOfAllUsersFromQuery = null;
        Session session = null;

        try
        {
            session=HibernateUtil.currentSession();
            String query = "select users from Users users";
            listOfAllUsersFromQuery = session.createQuery(query).getResultList();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            HibernateUtil.closeSession();
        }

        return listOfAllUsersFromQuery;
    }
}

import genclass.GenericIO;
/**
 *
 *  General description:
 *      The Restaurant Problem - monitor-based solution.
 * 
 *  @authors Filipe Pires (85122) & Isaac dos Anjos (78191)
 */
public class TheRestaurantMain {
    
    /**
     *  Main method.
     *  @param args runtime arguments
     */
    public static void main(String[] args) {
        
        /* necessary variables */
        
        int nstudents = 7;                                  // number of Students that are going to the Restaurant
        int ndishes = 3;                                    // number of dishes/portions per Student
        Chef chef;                                          // Chef of the Restaurant
        Waiter waiter;                                      // Waiter of the Restaurant
        Student[] students = new Student[nstudents];        // list of Students that are going to the Restaurant
        Kitchen kitchen;                                    // Restaurant's Kitchen
        Bar bar;                                            // Restaurant's Bar
        Table table;                                        // Restaurant's Table
        
        /* problem initialization */
        
        GenericIO.writelnString();
        GenericIO.writelnString(" - The Restaurant - ");
        GenericIO.writelnString();
        
        kitchen = new Kitchen(nstudents, ndishes);
        bar = new Bar(nstudents);
        table = new Table(nstudents);
        
        chef = new Chef(kitchen, bar);
        GenericIO.writelnString("Chef initialized.");
        waiter = new Waiter(kitchen, bar, table);
        GenericIO.writelnString("Waiter initialized.");
        for(int i=0; i<nstudents; i++) {
            students[i] = new Student(bar,table);
            GenericIO.writelnString("Student no." + students[i].getID() + " initialized.");
        }
        GenericIO.writelnString();
        
        /* start of the simulation */
        
        chef.start();
        waiter.start();
        for(int i=0; i<nstudents; i++) {
            students[i].start();
            GenericIO.writelnString("Student no." + students[i].getID() + " is " + students[i].getStudentState());
        }
        GenericIO.writelnString ();
        
        /* wait for the end of the simulation */
        
        // ...
        //GenericIO.writelnString ();
    }
    
    public static enum ArrivalOrder {                       // Order of Arrival Enum
        FIRST,
        MIDDLE,
        LAST;
    }
}

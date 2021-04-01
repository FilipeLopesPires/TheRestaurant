package serverSide;
import comInf.*;
import clientSide.GeneralRepositoryStub;
import java.util.*;

/**
 * 
 *  General Description:
 *      Definition of the Table Shared Region - monitor-based solution.
 * 
 *  Authors Filipe Pires (85122) and Isaac dos Anjos (78191)
 */
public class Table {

    /**
     *  Internal Data
     */
    
    private static GeneralRepositoryStub repo;                                  // Restaurant's Repository
    private volatile Stack<Integer> toBeSaluted;                                // stack of Students not yet Saluted

    private boolean readyToGetThePad;                                           // tells the First Student if the Waiter is ready to recieve the order
    private volatile int deliveredPortions,                                     // tells the Students how many portions have been delivered This Variable needs volatile because multiple processes access this variable.
                         currentCourse,                                         // tells the Students which course are they currently having
                         ate,                                                   // the amount of students that have eaten
                         last_id,                                               // make sure the last student that has eaten executes certain regions
                         chosenStudents;                                        // tells the First Student how many Students have chosen their courses

    private volatile boolean payed,                                             // has the Last student that arrived payed
                             finished,                                          // have everyone finished eating
                             billattable;                                       // has the waiter placed the bill on the table

    /**
     *  Constructor
     *  Allocates a new Table Shared Region.
     * 
     *  @param repo GeneralRepositoryStub serving as a representative of the General Repository for the entities states' to be monitored through a file log.
     */
    public Table(GeneralRepositoryStub repo) {
        this.repo = repo;
        toBeSaluted = new Stack<>();
        chosenStudents = deliveredPortions = currentCourse = ate = 0;
        readyToGetThePad = finished = billattable = payed = false;
    }

    /**
     *  Table's Methods
     */
    
    /**
     *  Used by Student to define his inital state of GTTR and walk to the Restaurant.
     */
    public synchronized void walkABit(int studentid) {
        // possível alteração de estado da entidade
        if (((ComHandler) Thread.currentThread()).setStudentState(EntityStates.StudentState.GTTR)) {
            repo.updateStudentState(((ComHandler) Thread.currentThread()).getStudentState(), studentid);
        }
    }

    /**
     *  Used by Student to sit at the table.
     */
    public synchronized void enter(int studentid) {
        // sincronização entre Student a chegar e Waiter a cumprimentar
        toBeSaluted.push(studentid);
        this.notifyAll();                                                       

        // Student bloqueia enquanto Waiter não o cumprimenta
        while (toBeSaluted.contains(studentid)) {
            try {
                this.wait();                                                    
            } catch (InterruptedException ie) {
            }
        }
    }

    /**
     *  Used by Waiter to salute the arriving Students.
     */
    public synchronized void saluteTheStudent() {
        // possível alteração de estado da entidade
        if (((ComHandler) Thread.currentThread()).setWaiterState(EntityStates.WaiterState.TTO)) {
            repo.updateWaiterState(((ComHandler) Thread.currentThread()).getWaiterState());
        }

        // sincronização entre Student a chegar e Waiter a cumprimentar
        while (toBeSaluted.isEmpty()) {
            try {
                this.wait();                                                    
            } catch (InterruptedException ie) {
            }
        }

        // Waiter cumprimenta Student
        toBeSaluted.pop();
        this.notifyAll();                                                       
    }

    /**
     *  Used by Student to select the courses they are having.
     */
    public synchronized void readTheMenu(int studentid) {
        // possível alteração de estado da entidade
        
        if (((ComHandler) Thread.currentThread()).setStudentState(EntityStates.StudentState.STC)) {
            repo.updateStudentState(((ComHandler) Thread.currentThread()).getStudentState(), studentid);
        }
    }

    /** 
     *  Used by Student to inform their choice to the Student responsible for the order.
     */
    public synchronized void informCompanion(int studentid) {
        // possível alteração de estado da entidade
        if (((ComHandler) Thread.currentThread()).setStudentState(EntityStates.StudentState.CWC)) {
            repo.updateStudentState(((ComHandler) Thread.currentThread()).getStudentState(), studentid);
        }
        
        // simulação do tempo gasto a informar o companheiro
        try {
            Thread.sleep((int) (10 * Math.random()));                           
        } catch (Exception e) {}

        // Student informa companheiro da sua escolha
        chosenStudents++;
        this.notifyAll();                                                       
    }

    /**
     *  Used by Student to verify if everyone has made their choices for the courses. 
     * 
     *  @return Returns true if every Student has chosen their courses, false if not.
     */
    public synchronized boolean hasEverybodyChosen(int studentid) {
        // possível alteração de estado da entidade
        if (((ComHandler) Thread.currentThread()).setStudentState(EntityStates.StudentState.OTO)) {
            repo.updateStudentState(((ComHandler) Thread.currentThread()).getStudentState(), studentid);
        }

        // verificação do número de Students que já escolheram
        if (chosenStudents == GeneralRepositoryStub.nstudents - 1) {
            return true;
        }
        return false;
    }

    /**
     *  Used by Student to prepare the Table's order.
     */
    public synchronized void prepareTheOrder(int studentid) {
        // possível alteração de estado da entidade
        if (((ComHandler) Thread.currentThread()).setStudentState(EntityStates.StudentState.OTO)) {
            repo.updateStudentState(((ComHandler) Thread.currentThread()).getStudentState(), studentid);
        }
        
        // First Student bloqueia enquanto restantes escolhem
        if(!hasEverybodyChosen(studentid)) {
            try {
                this.wait();                                                    
            } catch (InterruptedException ie) {}
        }
    }

    /**
     *  Used by Waiter to recieve the information about the Table's order.
     */
    public synchronized void getThePad() {
        // possível alteração de estado da entidade
        if (((ComHandler) Thread.currentThread()).setWaiterState(EntityStates.WaiterState.TTO)) {
            repo.updateWaiterState(((ComHandler) Thread.currentThread()).getWaiterState());
        }
        
        // sincronização entre Waiter a chegar à Table e Student a descrever a order
        readyToGetThePad = true;
        this.notifyAll();                                                       

        // Waiter espera por First Student
        try {                                                                   
            this.wait();
        } catch (Exception e) {}
    }

    /**
     *  Used by Student to describe the order to the Waiter.
     */
    public synchronized void describeTheOrder(int studentid) {
        // possível alteração de estado da entidade
        if (((ComHandler) Thread.currentThread()).setStudentState(EntityStates.StudentState.CWC)) {
            repo.updateStudentState(((ComHandler) Thread.currentThread()).getStudentState(), studentid);
        }
       
        // sincronização entre Waiter a chegar à Table e Student a descrever a order
        while (!readyToGetThePad) {
            try {
                wait();
            } catch (Exception e) {}
        }
        this.notifyAll();                                                       
        
        // simulação do tempo gasto a descrever a order
        try {
            Thread.sleep((int) (10 * Math.random()));                           
        } catch (Exception e) {}
    }

    /**
     *  Used by Student to chat with companions while they wait for the food.
     */
    public synchronized void joinTheTalk(int studentid, EntityStates.ArrivalOrder arrivalOrder) {
        // possível alteração de estado da entidade
        if (((ComHandler) Thread.currentThread()).setStudentState(EntityStates.StudentState.CWC)) {
            repo.updateStudentState(((ComHandler) Thread.currentThread()).getStudentState(), studentid);
        }

        // Students bloqueiam a conversar
        while (currentCourse == 0 || currentCourse == GeneralRepositoryStub.ncourses || (currentCourse == GeneralRepositoryStub.ncourses + 1 && arrivalOrder != EntityStates.ArrivalOrder.LAST)) {
            try {
                wait();
            } catch (Exception e) {}
        }
    }

    /**
     *  Used by Waiter to deliver a portion at the table.
     */
    public synchronized void deliverPortion() {
        // possível passagem para o course seguinte
        if (deliveredPortions % GeneralRepositoryStub.nstudents == 0) {
            deliveredPortions = 0;
            currentCourse++;
        }
        
        // Waiter entrega 1 portion
        deliveredPortions++;                                                    
        this.notifyAll();
    }

    /**
     *  Used by Student to start eating the current course.
     * 
     *  @param nc integer variable with the number of a course.
     */
    public synchronized void startEating(int studentid, int nc) {
        // Students só podem comer portions do course atual
        while (nc != currentCourse) {
            try {
                this.wait();
            } catch (Exception e) {}
        }
        
        // Students só podem comer quando já todos forem servidos
        while (currentCourse == nc && deliveredPortions < GeneralRepositoryStub.nstudents) {
            try {
                wait();
            } catch (Exception e) {}
        }
        
        // possível alteração de estado da entidade
        if (((ComHandler) Thread.currentThread()).setStudentState(EntityStates.StudentState.ETM)) {
            repo.updateStudentState(((ComHandler) Thread.currentThread()).getStudentState(), studentid);
        }
        
        // Students começam a comer
        this.notifyAll();
    }

    /**
     *  Used by Student to finish eating.
     */
    public synchronized void endEating(int studentid) {
        // Students só devem cruzar os talheres se os seus companheiros já estiverem a comer bem
        if(!repo.isEveryStudentEating() && ate<1) {
            try {
                wait();
            } catch (Exception e) {}
        }
        
        // Student termina a portion
        
        ate++;
        this.notifyAll();
        // possível alteração de estado da entidade
        if (((ComHandler) Thread.currentThread()).setStudentState(EntityStates.StudentState.CWC)) {
            repo.updateStudentState(((ComHandler) Thread.currentThread()).getStudentState(), studentid);
        }

        // verificação do final da refeição
        if (ate == GeneralRepositoryStub.nstudents*GeneralRepositoryStub.ncourses) {
            finished = true;
            currentCourse++;
            last_id = studentid;
        }
    }

    /**
     *  Used by Student to verify if everyone has finished eating. 
     * 
     *  @return Returns true if every Student has finished eating, false if not.
     */
    public synchronized boolean hasEverybodyFinished(int studentid) {
        // verificação do final do course atual, apenas o Last Student retorna true
        if (finished && last_id == studentid) {
            this.notifyAll();
            return true;
        }
        
        // Students devem esperar pelo final do course
        while (!finished) {
            try {
                wait();
            } catch (Exception e) {}
        }
        return false;
    }

    /**
     *  Used by Waiter to present the bill to the Last Student.
     */
    public synchronized void presentTheBill() {
        // possível alteração de estado da entidade
        if (((ComHandler) Thread.currentThread()).setWaiterState(EntityStates.WaiterState.RP)) {
            repo.updateWaiterState(((ComHandler) Thread.currentThread()).getWaiterState());
        }

        // Waiter apresenta a conta
        billattable = true;
        this.notifyAll();

        // Waiter espera pelo pagamento
        while (billattable) {
            try {
                this.wait();
            } catch (Exception e) {}
        }
    }

    /**
     *  Used by Student to accept the fact that he will have to pay for the entire meal.
     */
    public synchronized void shouldHaveArrivedEarlier(int studentid) {
        // Last Student espera pela conta
        while (!billattable) {
            try {
                this.wait();
            } catch (Exception e) {}
        }
        
        // possível alteração de estado da entidade
        if (((ComHandler) Thread.currentThread()).setStudentState(EntityStates.StudentState.PTB)) {
            repo.updateStudentState(((ComHandler) Thread.currentThread()).getStudentState(), studentid);
        }
    }

    /**
     *  Used by Student to pay the Table's bill.
     */
    public synchronized void honorTheBill(int studentid) {
        // Last Student paga a conta
        billattable = false;
        payed = true;
        
    }

    /**
     *  Used by Student to leave the Restaurant.
     */
    public synchronized void exit(int studentid) {
        // Students devem esperar pelo pagamento da conta
        
        while (!payed || billattable) {
            try {
                
                wait();
            } catch (Exception e) {}
        }
       
        // possível alteração de estado da entidade
        if (((ComHandler) Thread.currentThread()).setStudentState(EntityStates.StudentState.GH)) {
            repo.updateStudentState(((ComHandler) Thread.currentThread()).getStudentState(), studentid);
        }
        
        // Student sai do restaurante
        this.notifyAll();
    }
}

package serverSide;
import comInf.*;
import clientSide.GeneralRepositoryStub;

/**
 * 
 *  General Description:
 *      Definition of the Bar Shared Region - monitor-based solution.
 * 
 *  Authors Filipe Pires (85122) and Isaac dos Anjos (78191)
 */
public class Bar {
    
    /**
     *  Internal Data
     */
    
    private GeneralRepositoryStub repo;                                         // Restaurant's Repository
    private volatile char alternative;                                          // tells the ComHandler what to do
                            /**
                             * 's' means salute student
                             * 'o' means take table order
                             * 'p' means wait for portions
                             * 'b' means process table bill
                             * 'g' means say goodbye
                             * 'e' means end
                             */
    private volatile int arrivedStudents,                                       // aux var to know in which place did each Student arrive
                         students_on_hold,                                      // how many students are on hold
                         signalled_once;                                        // guarantee that signal the waiter goes off once
    
    /**
     *  Constructor
     *  Allocates a new Bar Shared Region.
     * 
     *  @param repo GeneralRepositoryStub serving as a representative of the General Repository for the entities states' to be monitored through a file log.
     */
    public Bar(GeneralRepositoryStub repo){
        this.repo = repo;
        alternative = '\0';
        students_on_hold=0;
        signalled_once = 0;
    }
    
    /**
     *  Bar's Methods
     */
    
    /**
     *  Used by Waiter to return his current alternative (action to be done) or empty if there is nothing to be done.
     * 
     *  @return Returns: '\0' if Waiter has nothing to do; 's' if there is a Student to salute; 'o' if there is an order to take; 'p' if Waiter needs to wait for a portion; 'b' if there is a bill to process; 'g' if there is a Student to say goodbye; 'e' if all activities have been executed.
     */
    public synchronized char lookAround() {
        // possível alteração de estado da entidade
        if (((ComHandler)Thread.currentThread()).setWaiterState(EntityStates.WaiterState.AS) ) {
            repo.updateWaiterState(((ComHandler)Thread.currentThread()).getWaiterState());
        }
        
        // Waiter deve verificar se é para se despedir de algum Student
        if(repo.haveAllStudentLeft() && alternative != 'e'){
            alternative='g';                                                    
        }
        
        // Waiter mostra-se disponível às entidades que estejam à espera dele
        if(students_on_hold>0){
            this.notifyAll();
            students_on_hold=0;
        }
        
        // Waiter fica à espera que haja alguma coisa para ele fazer
        while(alternative == '\0' ) {
            try {
                wait();                                                    
            } catch (InterruptedException ie) {}
        }
        
        // retorno da tarefa a ser feita pelo Waiter
        return alternative;
    }
    
    /**
     *  Used by Student to inform the Waiter of his arrival.
     *  @param studentid ID of the Student
     *  @return Returns the Student's place of arrival (variable type = ArrivalOrder).
     */
    public synchronized EntityStates.ArrivalOrder enter(int studentid) {
        // Students devem esperar caso o Waiter esteja ocupado
        while(alternative != '\0') {
            try {
                this.wait();                                                    
            } catch (InterruptedException ie) {}
        }
        
        // possível alteração de estado da entidade
        if (((ComHandler) Thread.currentThread()).setStudentState(EntityStates.StudentState.TASATT)) {
            repo.updateStudentState(((ComHandler) Thread.currentThread()).getStudentState(), studentid);
        }
        
        // Student verifica em que lugar chegou ao restaurante
        EntityStates.ArrivalOrder arrivedIn;                           
        arrivedStudents++;
        if(arrivedStudents==1) {
            arrivedIn = EntityStates.ArrivalOrder.FIRST;
        } else if(arrivedStudents == GeneralRepositoryStub.nstudents) {
            arrivedIn = EntityStates.ArrivalOrder.LAST;
        } else {
            arrivedIn = EntityStates.ArrivalOrder.MIDDLE;
           
        }
        this.notifyAll();
        
        // Students devem esperar caso o Waiter esteja ocupado
        while(alternative != '\0' ) {
            try {
                students_on_hold++;
                this.wait();                                                    
            } catch (InterruptedException ie) {}
        }
        
        // Waiter deverá cumprimentar o Student que acabou de chegar
        alternative = 's';
        this.notifyAll();                                                       
        
        // retorno do lugar em que o Student chegou ao restaurante
        return arrivedIn;
    }
    
    /**
     *  Used by Waiter to return to the Bar and reset the alternative (action to be done).
     */
    public synchronized void returnToTheBar() { 
        // possível alteração de estado da entidade
        if (((ComHandler)Thread.currentThread()).setWaiterState(EntityStates.WaiterState.AS) ) {
            repo.updateWaiterState(((ComHandler)Thread.currentThread()).getWaiterState());
        }
        
        // Waiter mostra-se disponível às entidades que estejam à espera dele
        alternative = '\0';
        this.notifyAll();                                                       
    }
    
    /**
     *  Used by Student to notify the Waiter once every Student has chosen their courses.
     */
    public synchronized void callTheWaiter() {
        // First Student chama o Waiter assim que todos tenham escolhido os seus courses
        alternative = 'o';
        this.notifyAll();                                                       
    }
    
    /**
     *  Used by Chef to notify the Waiter everytime he finishes dishing a portion.
     */
    public synchronized void alertTheWaiter() {
        // possível alteração de estado da entidade
        if (((ComHandler)Thread.currentThread()).setChefState(EntityStates.ChefState.DeTP) ) {
            repo.updateChefState(((ComHandler)Thread.currentThread()).getChefState());
        }
        
        // Chef deve esperar caso o Waiter esteja ocupado
        while(alternative != '\0'  && alternative !='p') {
            try {
                this.wait();                                                    
            } catch (InterruptedException ie) {}
        }
        
        // Chef avisa o Waiter de que tem uma nova portion pronta para ser entregue
        alternative = 'p';
        this.notifyAll();                                                       
    }
    
    /**
     *  Used by Student to notify the Waiter once he is ready to pay the bill.
     */
    public synchronized void signalTheWaiter() {
        // apenas 1 Student deve chamar o Waiter
        if(signalled_once>0) {
            return;
        }
        signalled_once++;
        
        // Student deve esperar caso o Waiter esteja ocupado
        while(alternative != '\0')
            try{
                this.wait();
            }catch(Exception e){}                                    
        
        // Student chama o Waiter para proceder ao pagamento da refeição
        alternative = 'b';
        this.notifyAll();                                                       
    }
    
    /**
     *  Used by Waiter to prepare the Table's bill.
     */
    public synchronized void prepareTheBill() {
        // possível alteração de estado da entidade
        if (((ComHandler)Thread.currentThread()).setWaiterState(EntityStates.WaiterState.PTB) ) {
            repo.updateWaiterState(((ComHandler)Thread.currentThread()).getWaiterState());
        }
    }
    
    /**
     *  Used by Waiter to say goodbye to each and every Student.
     */
    public synchronized void sayGoodbye() {
        // Waiter despede-se de todos os Students e termina as suas funções
        alternative='e';
    }
}

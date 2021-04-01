package serverSide;

import comInf.*;
import clientSide.GeneralRepositoryStub;

/**
 *  General Description:
 *      Definition of the Kitchen Shared Region - monitor-based solution.
 * 
 *  Authors Filipe Pires (85122) S Isaac dos Anjos (78191)
 */
public class Kitchen {
    
    /**
     *  Internal Data
     */
    
    private static GeneralRepositoryStub repo;                                  // Restaurant's Repository
   
    private volatile boolean order,                                             // tells the Chef when an order is delivered
                             portionReady;                                      // tells the Waiter if the current portion is ready to be delivered at the table
    private volatile int     deliveredPortions,                                 // tells the Chef how many portions of the current course have been delivered
                             totalPortions;                                     // tells the Chef how many portions have been delivered (of all courses)
    
    /**
     *  Constructor
     *  Allocates a new Kitchen Shared Region.
     * 
     *  @param repo GeneralRepositoryStub serving as a representative of the General Repository for the entities states' to be monitored through a file log.
     */
    public Kitchen(GeneralRepositoryStub repo){
        this.repo = repo;
        order = false;
        portionReady = false;
        deliveredPortions = 0;
        totalPortions = 0;
    }
    
    /**
     *  Kitchen's Methods
     */
    
    /**
     *  Used by Chef to wait for an order to be handed to him.
     */
    public synchronized void watchTheNews() {
        // possível alteração de estado da entidade
        if(((ComHandler)Thread.currentThread()).setChefState(EntityStates.ChefState.WFAO) ) {
            repo.updateChefState(((ComHandler)Thread.currentThread()).getChefState());
        }
        
        // Chef espera por algo para cozinhar
        while(!order) {
            try {
                this.wait();                                                    
            } catch (InterruptedException ie) {}
        }
    }
    
    /**
     *  Used by Waiter to notify the Chef once the Students have chosen their courses.
     */
    public synchronized void handTheNoteToTheChef() {
        // possível alteração de estado da entidade
        if (((ComHandler)Thread.currentThread()).setWaiterState(EntityStates.WaiterState.PTO) ) {
            repo.updateWaiterState(((ComHandler)Thread.currentThread()).getWaiterState());
        }
        
        // Waiter entrega pedido ao Chef
        order = true;                                                                                                      
        this.notify();                                                              
    }
    
    /**
     *  Used by Chef to start preparing the order.
     * 
     * @param nCourse integer variable holding the number of the current course
     */
    public synchronized void startPreparation(int nCourse) {
        // possível alteração de course
        repo.updateCourse(nCourse);
        
        // possível alteração de estado da entidade
        if (((ComHandler)Thread.currentThread()).setChefState(EntityStates.ChefState.PTC) ) {
            repo.updateChefState(((ComHandler)Thread.currentThread()).getChefState());
        }
        
        // simulação do tempo de cozinhar o course
        try {
            Thread.sleep((int) (1000 * Math.random ()));                        
        } catch (Exception e) {}
    }
    
    /**
     *  Used by Chef to start dishing the portions of the current course.
     */
    public synchronized void proceedToPresentation() {
        // possível alteração de estado da entidade
        if (((ComHandler)Thread.currentThread()).setChefState(EntityStates.ChefState.DiTP) ) {
            repo.updateChefState(((ComHandler)Thread.currentThread()).getChefState());
        }
                       
        // Chef avisa o Waiter de que tem uma portion pronta
        portionReady = true;
        deliveredPortions = 0;
    }
    
    /**
     *  Used by Chef to verify if all portions of the current course have been delivered to the Waiter. 
     * 
     *  @return Returns true if all portions of the current course have been delivered, false if not.
     */
    public synchronized boolean haveAllPortionsBeenDelivered() {
        // verificação do número de portions que já fora entregues
        if(deliveredPortions == GeneralRepositoryStub.nstudents) {
            return true;
        }
        return false;
    }
    
    /**
     *  Used by Chef to deliver one portion to the Waiter.
     */
    public synchronized void haveNextPortionReady() {
        // possível alteração de estado da entidade
        if (((ComHandler)Thread.currentThread()).setChefState(EntityStates.ChefState.DiTP) ) {
            repo.updateChefState(((ComHandler)Thread.currentThread()).getChefState());
        }
        
        // Chef deve esperar que o Waiter pegue na portion atual para lhe disponibilizar a seguinte
        if(portionReady)
            try{wait();}catch(Exception e){}
        
        // Chef disponibiliza a portion seguinte ao Waiter
        portionReady = true;
        this.notifyAll();                                                       
    }
    
    /**
     *  Used by Waiter to collect the portion from the Chef.
     */
    public synchronized void collectPortion() {
        // possível alteração de estado da entidade
        if (((ComHandler)Thread.currentThread()).setWaiterState(EntityStates.WaiterState.WFP) ) {
            repo.updateWaiterState(((ComHandler)Thread.currentThread()).getWaiterState());
        }
        
        // Waiter espera enquanto Chef nao lhe prepara uma nova portion
        while(!portionReady) {
            try {
                this.wait();                                                    
            } catch (InterruptedException ie) {}
        }
        deliveredPortions++; totalPortions++;
        
        // Waiter pega na portion pronta
        portionReady = false;
        this.notifyAll();                                                       
    }
    
    /**
     *  Used by Waiter to verify if all portions of the current course have been delivered to the Students.
     * 
     *  @return Returns true if all portions of the current course have been delivered, false if not.
     */
    public synchronized boolean haveAllStudentsBeenServed() {
        // verificação do número de Students que já foram servidos do course atual
        if(deliveredPortions == GeneralRepositoryStub.nstudents ) {
            return true;
        }
        return false;
    }
    
    /**
     *  Used by Chef to verify if all portions from all courses have been delivered to the Waiter.
     * 
     *  @return Returns true if all portions from all courses have been delivered, false if not.
     */
    public synchronized boolean hasTheOrderBeenCompleted() {
        // possível alteração de estado da entidade
        if (((ComHandler)Thread.currentThread()).setChefState(EntityStates.ChefState.DeTP) ) {
            repo.updateChefState(((ComHandler)Thread.currentThread()).getChefState());
        }
        
        // verificação do número de Students que já foram servidos de todos os courses
        if(totalPortions == GeneralRepositoryStub.ncourses*GeneralRepositoryStub.nstudents){
            return true;
        }
       return false;
    }
    
    /**
     *  Used by Chef to continue preparing the remaining courses from the order.
     * 
     * @param nCourse integer variable holding the number of the current course
     */
    public synchronized void continuePreparation(int nCourse) {
        // possível alteração de course
        repo.updateCourse(nCourse+1);
        
        // possível alteração de estado da entidade
        
        if (((ComHandler)Thread.currentThread()).setChefState(EntityStates.ChefState.PTC) ) {
            repo.updateChefState(((ComHandler)Thread.currentThread()).getChefState());
        }
        
        // simulação do tempo de preparação do course seguinte
        try {
            Thread.sleep((int) (1000 * Math.random ()));                        
        } catch (Exception e) {}
        portionReady = true;
    }
    
    /**
     *  Used by Chef to close the service and clean the Kitchen.
     */
    public synchronized void cleanUp() {
        // possível alteração de estado da entidade
        // Chef termina o seu serviço
        if (((ComHandler)Thread.currentThread()).setChefState(EntityStates.ChefState.CS) ) {
            repo.updateChefState(((ComHandler)Thread.currentThread()).getChefState());
        }
    }
}

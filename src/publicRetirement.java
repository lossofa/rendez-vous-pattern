import java.util.List;
import java.util.concurrent.Semaphore;

public class publicRetirement extends Thread {

    static double publicRetirement;
    int minBoundary;
    int maxBoundary;
    int listSize;
    int count;
    int i;
    List<Employees> employeeList;
    Semaphore listPart1;
    Semaphore listPart2;
    Semaphore listPart3;
    Semaphore listPart4;
    Semaphore countMutex;
    Semaphore barrier;

    public publicRetirement(int listSize, List<Employees> employeesList, Semaphore listPart1, Semaphore listPart2, Semaphore listPart3, Semaphore listPart4, Semaphore countMutex, Semaphore barrier){
        this.listSize = listSize;
        this.employeeList = employeesList;
        this.listPart1 = listPart1;
        this.listPart2 = listPart2;
        this.listPart3 = listPart3;
        this.listPart4 = listPart4;
        this.countMutex = countMutex;
        this.barrier = barrier;
    }

    public void calculatePublicRetirement(int min, int max){
        double salary;
        Employees employee;
        for(i=min; i<=max; i++){
            employee = employeeList.get(i);
            salary = employee.fullSalary;
            publicRetirement = salary*0.08;
            employeeList.get(i).publicRetirementTaxDiscount = publicRetirement;
            employeeList.get(i).totalDiscounts = employee.totalDiscounts + publicRetirement;
            employeeList.get(i).liquidSalary = employee.liquidSalary - publicRetirement;
        }
    }

    public void printPart2ofListInTxt(int min, int max){
        for(i=min; i<=max; i++){
            System.out.print("Thread 2");
            System.out.println(employeeList.get(i));
        }
    }

    public void run(){

        try {

            //part 2
            minBoundary = 1 + listSize/4;
            maxBoundary = listSize/2;
            listPart2.acquire();
            calculatePublicRetirement(minBoundary, maxBoundary);
            listPart2.release();
            
            //part 3
            minBoundary = listSize/2 + 1;
            maxBoundary = listSize - listSize/4;
            listPart3.acquire();
            calculatePublicRetirement(minBoundary, maxBoundary);
            listPart3.release();

            //part 4
            minBoundary = listSize - listSize/4 + 1;
            maxBoundary = listSize;
            listPart4.acquire();
            calculatePublicRetirement(minBoundary, maxBoundary);
            listPart4.release();

            //part 1
            minBoundary = 1;
            maxBoundary = listSize/4;
            listPart1.acquire();
            calculatePublicRetirement(minBoundary, maxBoundary);
            listPart1.release();

            //Rendez-vouz -> Barrier logic
            countMutex.acquire();
                Main.countOnBarrier++;
                count = Main.countOnBarrier;
            countMutex.release();

            if (count == 4) {barrier.release();}
            barrier.acquire();
            barrier.release();

            minBoundary = 1 + listSize/4;
            maxBoundary = listSize/2;
            printPart2ofListInTxt(minBoundary, maxBoundary);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
    }
    
}

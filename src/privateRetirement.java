import java.util.List;
import java.util.concurrent.Semaphore;

public class privateRetirement extends Thread {

    static double privateRetirement;
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

    public privateRetirement(int listSize, List<Employees> employeesList, Semaphore listPart1, Semaphore listPart2, Semaphore listPart3, Semaphore listPart4, Semaphore countMutex, Semaphore barrier){
        this.listSize = listSize;
        this.employeeList = employeesList;
        this.listPart1 = listPart1;
        this.listPart2 = listPart2;
        this.listPart3 = listPart3;
        this.listPart4 = listPart4;
        this.countMutex = countMutex;
        this.barrier = barrier;
    }

    public void calculatePrivateRetirement(int min, int max){
        double salary;
        Employees employee;
        for(i=min; i<=max; i++){
            employee = employeeList.get(i);
            salary = employee.fullSalary;
            privateRetirement = salary*0.04;
            employeeList.get(i).publicRetirementTaxDiscount = privateRetirement;
            employeeList.get(i).totalDiscounts = employee.totalDiscounts + privateRetirement;
            employeeList.get(i).liquidSalary = employee.liquidSalary - privateRetirement;
        }
    }

    public void printPart3ofListInTxt(int min, int max){
        for(i=min; i<=max; i++){
            System.out.print("Thread 3");
            System.out.println(employeeList.get(i));
        }
    }

    public void run(){

        try {

            //part 3
            minBoundary = listSize/2 + 1;
            maxBoundary = listSize - listSize/4;
            listPart3.acquire();
            calculatePrivateRetirement(minBoundary, maxBoundary);
            listPart3.release();
            
            //part 4
            minBoundary = listSize - listSize/4 + 1;
            maxBoundary = listSize;
            listPart4.acquire();
            calculatePrivateRetirement(minBoundary, maxBoundary);
            listPart4.release();

            //part 1
            minBoundary = 1;
            maxBoundary = listSize/4;
            listPart1.acquire();
            calculatePrivateRetirement(minBoundary, maxBoundary);
            listPart1.release();

            //part 2
            minBoundary = 1 + listSize/4;
            maxBoundary = listSize/2;
            listPart2.acquire();
            calculatePrivateRetirement(minBoundary, maxBoundary);
            listPart2.release();

            //Rendez-vouz -> Barrier logic
            countMutex.acquire();
                Main.countOnBarrier++;
                count = Main.countOnBarrier;
            countMutex.release();

            if (count == 4) {barrier.release();}
            barrier.acquire();
            barrier.release();

            minBoundary = listSize/2 + 1;
            maxBoundary = listSize - listSize/4;
            printPart3ofListInTxt(minBoundary, maxBoundary);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
    }
    
}

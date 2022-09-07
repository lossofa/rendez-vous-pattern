import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.concurrent.Semaphore;

public class incomeTax extends Thread {

    static double incomeTax;
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

    public incomeTax(int listSize, List<Employees> employeesList, Semaphore listPart1, Semaphore listPart2, Semaphore listPart3, Semaphore listPart4, Semaphore countMutex, Semaphore barrier){
        this.listSize = listSize;
        this.employeeList = employeesList;
        this.listPart1 = listPart1;
        this.listPart2 = listPart2;
        this.listPart3 = listPart3;
        this.listPart4 = listPart4;
        this.countMutex = countMutex;
        this.barrier = barrier;
        
    }

    public void calculateIncomeTax(int min, int max){

        double salary;
        Employees employee;
        for(i=min; i<max; i++){
            employee = employeeList.get(i);
            salary = employee.fullSalary;
            incomeTax = salary*0.2;
            employeeList.get(i).incomeTaxDiscount = incomeTax;
            employeeList.get(i).totalDiscounts = employee.totalDiscounts + incomeTax;
            employeeList.get(i).liquidSalary = employee.liquidSalary - incomeTax;
        }
    }

    public void printPart1ofListInTxt(int min, int max) {
      FileManipulator fileManipulator = new FileManipulator(1);


            fileManipulator.print_employee_list(employeeList,min,max);

    }


    public void run(){

        try {

            //part 1
            System.out.println(getName() + " part 1");
            minBoundary = 0;
            maxBoundary = listSize/4;
            listPart1.acquire();
            calculateIncomeTax(minBoundary, maxBoundary);
            listPart1.release();
            
            //part 2
            System.out.println(getName() + " part 2");
            minBoundary = maxBoundary;
            maxBoundary = listSize/2;
            listPart2.acquire();
            calculateIncomeTax(minBoundary, maxBoundary);
            listPart2.release();

            //part 3
            System.out.println(getName() + " part 3");
            minBoundary = maxBoundary;
            maxBoundary = listSize - listSize/4;
            listPart3.acquire();
            calculateIncomeTax(minBoundary, maxBoundary);
            listPart3.release();

            //part 4
            System.out.println(getName() + " part 4");
            minBoundary = maxBoundary;
            maxBoundary = listSize;
            listPart4.acquire();
            calculateIncomeTax(minBoundary, maxBoundary);
            listPart4.release();

            //Rendez-vouz -> Barrier logic
            countMutex.acquire();
                Main.countOnBarrier++;
                count = Main.countOnBarrier;
            countMutex.release();

            if (count == 4) {barrier.release();}
            barrier.acquire();
            barrier.release();

            minBoundary = 0;
            maxBoundary = listSize/4;
            printPart1ofListInTxt(minBoundary, maxBoundary);


        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
    }
    
}

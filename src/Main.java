import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Semaphore;

public class Main {
    
    static int n = 2;
    static int i;
    static int countOnBarrier = 0;
    public static void main(String[] args){

        FileManipulator.clean_all_files();

        double salary;
        double minSalary = 1000.00;
        double maxSalary = 5000.00;
        Random salaryGenerator = new Random();
        List<Employees> employeeList = new ArrayList<Employees>();

        Semaphore countMutex = new Semaphore(1);
        Semaphore barrier = new Semaphore(0);
        Semaphore listPart1 = new Semaphore(1);
        Semaphore listPart2 = new Semaphore(1);
        Semaphore listPart3 = new Semaphore(1);
        Semaphore listPart4 = new Semaphore(1);

        incomeTax incomeTax = new incomeTax(n*1000, employeeList, listPart1, listPart2, listPart3, listPart4, countMutex, barrier);
        publicRetirement publicRetirement = new publicRetirement(n*1000, employeeList, listPart1, listPart2, listPart3, listPart4, countMutex, barrier);
        privateRetirement privateRetirement = new privateRetirement(n*1000, employeeList, listPart1, listPart2, listPart3, listPart4, countMutex, barrier);
        healthInsurance healthInsurance = new healthInsurance(n*1000, employeeList, listPart1, listPart2, listPart3, listPart4, countMutex, barrier);


        for (i = 1; i <= n*1000; i++){
            salary = salaryGenerator.nextDouble()*(maxSalary - minSalary) + minSalary;
            Random r = new Random();
            int id = r.nextInt(4000) + 1000;
            Employees employee = new Employees(id, salary , 0.00, 0.00, 0.00, 0.00, 0.00, salary);
            employeeList.add(employee);
        }

        System.out.println(employeeList.size());

        incomeTax.start();
        publicRetirement.start();
        privateRetirement.start();
        healthInsurance.start();     

    }
}

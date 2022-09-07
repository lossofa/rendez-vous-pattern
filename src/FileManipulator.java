import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class FileManipulator {

    static final String prefix_name = "part";
    static final String ext = ".txt";
    int fileId;
    String filename;

    public FileManipulator(int fileId){
        this.fileId = fileId;
        filename = create_filename(fileId);
    }

    static private String create_filename(int fileId){
        return prefix_name + fileId + ext;
    }
    static void clean_all_files(){
        for (int i = 1; i <= 4; i++) {
            try {
                PrintWriter out = new PrintWriter(create_filename(i));
                out.print("");
                out.close();
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }

    }

    void print_employee_list(List<Employees> employeeList, int min, int max){
//
//        try {
//            clean_file();
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
        for (int i=min; i<max; i++) {

            try {

                FileWriter out = new FileWriter(filename, true);
                Employees employee = employeeList.get(i);
                    out.write(
                            "\nemployee " + employee.employeeCode +
                                    "\nfull salary:  " + employee.fullSalary +
                                    "\ntax: -" + employee.incomeTaxDiscount +
                                    "\nhealth insurance: -" + employee.healthInsurance +
                                    "\nINSS: -" + employee.publicRetirementTaxDiscount +
                                    "\nprivate retirement tax: -" + employee.privateRetirementTaxDiscount +
                                    "\ntotal discounts: -" + employee.totalDiscounts +
                                    "\n----------------------------" +
                            "\nliquid salary: " + employee.liquidSalary
                    );

                out.write("\n================================");
                out.close();

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

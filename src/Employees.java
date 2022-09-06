public class Employees {
    
    int employeeCode;
    double fullSalary;
    double incomeTaxDiscount;
    double publicRetirementTaxDiscount;
    double privateRetirementTaxDiscount;
    double healthInsurance;
    
    double totalDiscounts;
    double liquidSalary;

    public Employees(int employeeCode,
                        double fullSalary,
                        double incomeTaxDiscount,
                        double publicRetirementTaxDiscount,
                        double privateRetirementTaxDiscount,
                        double healthInsurance,
                        double totalDiscounts,
                        double liquidSalary
    ){
        this.employeeCode = employeeCode;
        this.fullSalary = fullSalary;
        this.incomeTaxDiscount = incomeTaxDiscount;
        this.publicRetirementTaxDiscount = publicRetirementTaxDiscount;
        this.privateRetirementTaxDiscount = privateRetirementTaxDiscount;
        this.healthInsurance = healthInsurance;
        this.totalDiscounts = totalDiscounts;
        this.liquidSalary = liquidSalary;
    }

}

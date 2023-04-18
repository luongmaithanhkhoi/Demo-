public class FullTimeStaff extends Staff{
    protected int baseSalary;
    protected double bonusRate;

    public FullTimeStaff(String sID, String sName, int baseSalary, double bonusRate) {
        super(sID, sName);
        this.baseSalary = baseSalary;
        this.bonusRate = bonusRate;
    }
    public int getBaseSalary() {
        return this.baseSalary;
    }
    
    public void setBaseSalary(int baseSalary) {
        this.baseSalary = baseSalary;
    }

    public double getBonusRate() {
        return this.baseSalary;
    }
    
    public void setBonusRate(double bonusRate) {
        this.bonusRate = bonusRate;
    }

    @Override
    public double paySalary(int workedDays) {
        double bonusDay;
        if(workedDays <= 21) {
            bonusDay = 0.0;
        } else {
            bonusDay = workedDays - 21;
        }
        return this.bonusRate * this.baseSalary + bonusDay*(100000.0);
    }
    
    @Override
    public String toString() {
        return super.toString() + "_" + 
                this.bonusRate+ "_" + this.baseSalary  ;
    }

    
}

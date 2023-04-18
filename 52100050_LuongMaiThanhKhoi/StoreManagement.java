import java.io.*;
import java.util.*;

public class StoreManagement {
    private ArrayList<Staff> staffs;
    private ArrayList<String> workingTime;
    private ArrayList<Invoice> invoices;
    private ArrayList<InvoiceDetails> invoiceDetails;
    private ArrayList<Drink> drinks;

    public StoreManagement(String staffPath, String workingTimePath, String invoicesPath, String detailsPath, String drinksPath) {
        this.staffs = loadStaffs(staffPath);
        this.workingTime = loadFile(workingTimePath);
        this.invoices = loadInvoices(invoicesPath);
        this.invoiceDetails = loadInvoicesDetails(detailsPath);
        this.drinks = loadDrinks(drinksPath);
    }

    public ArrayList<Staff> getStaffs() {
        return this.staffs;
    }

    public void setStaffs(ArrayList<Staff> staffs){
        this.staffs = staffs;
    }
    
    public ArrayList<Drink> loadDrinks(String filePath) {
        ArrayList<Drink> drinksResult = new ArrayList<Drink>();
        ArrayList<String> drinks = loadFile(filePath);

        for (String drink : drinks) {
            String[] information = drink.split(",");
            drinksResult.add(new Drink(information[0], Integer.parseInt(information[1])));
        }

        return drinksResult;
    }

    public ArrayList<Invoice> loadInvoices(String filePath) {
        ArrayList<Invoice> invoiceResult = new ArrayList<Invoice>();
        ArrayList<String> invoices = loadFile(filePath);

        for (String invoice : invoices) {
            String[] information = invoice.split(",");
            invoiceResult.add(new Invoice(information[0], information[1], information[2]));
        }

        return invoiceResult;
    }

    public ArrayList<InvoiceDetails> loadInvoicesDetails(String filePath) {
        ArrayList<InvoiceDetails> invoiceResult = new ArrayList<InvoiceDetails>();
        ArrayList<String> invoices = loadFile(filePath);

        for (String invoice : invoices) {
            String[] information = invoice.split(",");
            invoiceResult.add(new InvoiceDetails(information[0], information[1], Integer.parseInt(information[2])));
        }

        return invoiceResult;
    }

    // requirement 1
    public ArrayList<Staff> loadStaffs(String filePath) {
        //code here and modify the return 
        ArrayList<Staff> resultStaff = new ArrayList<Staff>();
        ArrayList<String> personStaff = loadFile(filePath);
        for(String oneStaff : personStaff) {
            String[] informationOfOneStaff = oneStaff.split(","); 
            if (informationOfOneStaff.length == 3) {
                resultStaff.add(new SeasonalStaff(informationOfOneStaff[0], 
                informationOfOneStaff[1], Integer.parseInt(informationOfOneStaff[2])));
            } else if(informationOfOneStaff.length == 4) {
                resultStaff.add(new FullTimeStaff (informationOfOneStaff[0], 
                informationOfOneStaff[1], Integer.parseInt(informationOfOneStaff[2]), 
                Double.parseDouble(informationOfOneStaff[3])));
            } else {
                resultStaff.add(new Manager (informationOfOneStaff[0], informationOfOneStaff[1], 
                Integer.parseInt(informationOfOneStaff[2]), Double.parseDouble(informationOfOneStaff[3]), 
                Integer.parseInt(informationOfOneStaff[4])));
            }
        }
        return resultStaff;
    }

    // requirement 2
    public ArrayList<SeasonalStaff> getTopFiveSeasonalStaffsHighSalary() {
        //code here and modify the return value
        ArrayList<SeasonalStaff> resultSeasonalStaffs = new ArrayList<SeasonalStaff>();
        List<Double> arrays = new ArrayList<>();
        for(Staff person : staffs) {
            if ( person instanceof SeasonalStaff) {
                for (String w : this.workingTime) {
                    String[] informationOfTime = w.split(",");
                    if(informationOfTime[0].equals(person.sID)) {
                        int hourWork = Integer.parseInt(informationOfTime[1]);
                        Double salarys = person.paySalary(hourWork);
                        arrays.add(salarys);
                    }
                }     
            }  
        }
        Double[] arr = new Double[arrays.size()];
        for (int i = 0; i < arrays.size(); i++) {
            arr[i] = arrays.get(i);
        }
        Arrays.sort(arr);
        for (int i = arr.length-1; i >= arr.length-5; i--) {
            for(Staff person: this.staffs ) {
                if ( person instanceof SeasonalStaff) {
                    for (String w : this.workingTime) {
                        String[] informationOfTime = w.split(",");
                        if(informationOfTime[0].equals(person.sID)) {
                            int hourWork = Integer.parseInt(informationOfTime[1]);
                            Double salarys = person.paySalary(hourWork);
                            if ( salarys.equals(arr[i])) {
                                String result = "";
                                result += person;
                                String[] information = result.split("_");
                                resultSeasonalStaffs.add(new SeasonalStaff(information[0], information[1], Integer.parseInt(information[2])));
                               
                            }
                            
                        }
                    }     
                }   
            }
        }
        return resultSeasonalStaffs;
    }

    //requirement 3
    public ArrayList<FullTimeStaff> getFullTimeStaffsHaveSalaryGreaterThan(int lowerBound) {
        //code here and modify the return value
        ArrayList<FullTimeStaff> resultFullTimeStaffs = new ArrayList<FullTimeStaff>();
        List<Double> arrays = new ArrayList<>();
        List<String> arrays2 = new ArrayList<>();
        for(Staff person : staffs) {
            if (person instanceof FullTimeStaff) {
                for (String w : this.workingTime) {
                    String[] informationOfTime = w.split(",");
                    if(informationOfTime[0].equals(person.sID)) {
                        int workedHours = Integer.parseInt(informationOfTime[1]);
                        Double salarys = person.paySalary(workedHours)/1000.0;
                        String result = "";
                        result += person;
                        arrays2.add(result);
                        arrays.add(salarys);
                    }
                }
            }
        }
        Double[] arr = new Double[arrays.size()];
        Double[] arr2 = new Double[arrays.size()];
        for (int i = 0; i < arrays.size(); i++) {
            arr[i] = arrays.get(i);
        }
        Arrays.sort(arr);
        double intem;
        intem = lowerBound/1000.0;
        int count = 0;
        for (int i = 0; i < arr.length; i++) {
            if(arr[i] > intem ) {
                arr2[count] = arr[i];
                count++;
            }
        }
        for(int i =0 ; i <arrays.size(); i++) {
            for(int j = 0;j< count;j++){
                if(arrays.get(i) == arr2[j]){
                    String[] informationOfOneStaff = arrays2.get(i).split("_");
                    for(Staff person : staffs) {
                        String arraysG = "";
                        arraysG += person;
                        String[] information = arraysG.split("_");
                        if(information[0].equals(informationOfOneStaff[0])) {
                            resultFullTimeStaffs.add((FullTimeStaff)person);
    
                        }
                    }
                }
            }
            
        }
        return resultFullTimeStaffs;
    }
    // requirement 4
    public double totalInQuarter(int quarter) {
        double total = 0;
        List<Integer> arraysA = new ArrayList<>();
        List<String> arraysB = new ArrayList<>();
        List<Integer> arraysC = new ArrayList<>();
        List<String> arraysD = new ArrayList<>();
        for(Invoice invoice: invoices){
            String arrays = "";
            arrays += invoice;
            String[] information = arrays.split("_");
            String[] informationMonth = information[2].split("/");
            arraysA.add(Integer.parseInt(informationMonth[1]));
            arraysB.add(information[0]);
        }
        if(quarter == 1) {
            int[] arr1 = new int[] {1,2,3};
            for(int i = 0; i<arraysA.size();i++) {
                for (int wq : arr1) {
                    if(wq == arraysA.get(i)) {
                        for(InvoiceDetails w : invoiceDetails) {
                            String arrays = "";
                            arrays += w;
                            String[] information = arrays.split("_");
                            if(information[0].equals(arraysB.get(i))) {
                                arraysC.add(Integer.parseInt(information[2]));
                                arraysD.add(information[1]);
                            }
                        }
                    }
                }
            }
        }
        if(quarter == 2) {
            int[] arr2 = new int[] {4,5,6};
            for(int i = 0; i<arraysA.size();i++) {
                for (int wq : arr2) {
                    if(wq == arraysA.get(i)) {
                        for(InvoiceDetails w : invoiceDetails) {
                            String arrays = "";
                            arrays += w;
                            String[] information = arrays.split("_");
                            if(information[0].equals(arraysB.get(i))) {
                                arraysC.add(Integer.parseInt(information[2]));
                                arraysD.add(information[1]);
                            }
                        }
                    }
                }
            }
        }
        if(quarter == 3) {
            int[] arr3 = new int[] {7,8,9};
            for(int i = 0; i<arraysA.size();i++) {
                for (int wq : arr3) {
                    if(wq == arraysA.get(i)) {
                        for(InvoiceDetails w : invoiceDetails) {
                            String arrays = "";
                            arrays += w;
                            String[] information = arrays.split("_");
                            if(information[0].equals(arraysB.get(i))) {
                                arraysC.add(Integer.parseInt(information[2]));
                                arraysD.add(information[1]);
                            }
                        }
                    }
                }
            }
        }
        if(quarter == 4) {
            int[] arr3 = new int[] {10,11,12};
            for(int i = 0; i<arraysA.size();i++) {
                for (int wq : arr3) {
                    if(wq == arraysA.get(i)) {
                        for(InvoiceDetails w : invoiceDetails) {
                            String arrays = "";
                            arrays += w;
                            String[] information = arrays.split("_");
                            if(information[0].equals(arraysB.get(i))) {
                                arraysC.add(Integer.parseInt(information[2]));
                                arraysD.add(information[1]);
                            }
                        }
                    }
                }
            }
        }
        for(int i = 0; i< arraysD.size();i++) {
            for(Drink w: drinks) {
                String arrays = "";
                arrays += w;
                String[] information = arrays.split("_");
                if(arraysD.get(i).equals(information[0])) {
                    total += arraysC.get(i)*Integer.parseInt(information[1]);
                }
            }
        }
        return total;
    }
    // requirement 5
    public Staff getStaffHighestBillInMonth(int month) {
        Staff maxStaff = null;
        //code here
        List<String> arraysB = new ArrayList<>();
        List<String> arraysD = new ArrayList<>();
        for (Invoice invoice :invoices) {  
            String arrays = "";
            arrays += invoice;
            String[] information = arrays.split("_");
            String[] informationMonth = information[2].split("/");
            if(Integer.parseInt(informationMonth[1]) == month ) {
                arraysB.add(information[1]);
                arraysD.add(information[0]);
            }
        }
        Set<String> phanTu = new HashSet<String>(arraysB);
    
        List<String> arraysF = new ArrayList<>(phanTu);
        List<Double> arraysFinal = new ArrayList<>();
        for(String w : arraysF){
            Double totalOfDrink = 0.0;
            List<String> arrays = new ArrayList<>();
            for (int i = 0; i<arraysB.size();i++) {
                if(w.equals(arraysB.get(i))) {
                    arrays.add(arraysD.get(i));

                }
            }
            for(String q :arrays) {
                List<String> arraysA = new ArrayList<>();
                List<Integer> arraysC = new ArrayList<>();
                for(InvoiceDetails wth : invoiceDetails) {
                    String arraysTest = "";
                    arraysTest += wth;
                    String[] information = arraysTest.split("_");
                    if(q.equals(information[0])) {
                        arraysA.add(information[1]);
                        arraysC.add(Integer.parseInt(information[2]));
                    }
                }
                for(int i = 0; i<arraysA.size();i++) {
                    for(Drink withDrink: drinks) {
                        String arraysG = "";
                        arraysG += withDrink;
                        String[] information = arraysG.split("_");
                        if(information[0].equals(arraysA.get(i))) {
                            totalOfDrink += Double.parseDouble(information[1])*arraysC.get(i)/1000.0;
                        }
                    }     
                }
                
            }
            arraysFinal.add(totalOfDrink);        
        }
        Double[] arrD = new Double[arraysFinal.size()];
        for (int i = 0; i < arraysFinal.size(); i++) {
            arrD[i] = arraysFinal.get(i);
        }
        Arrays.sort(arrD);
        for(int i = 0; i< arraysFinal.size();i++) {
            if(arrD[arrD.length-1] ==arraysFinal.get(i) ) {
                for(Staff person : staffs) {
                    String arraysG = "";
                    arraysG += person;
                    String[] information = arraysG.split("_");
                    if(information[0].equals(arraysF.get(i))) {
                        maxStaff = person;
                    }
                }
                
            }
        }
        
        return maxStaff;
    }

    // load file as list
    public static ArrayList<String> loadFile(String filePath) {
        String data = "";
        ArrayList<String> list = new ArrayList<String>();

        try {
            FileReader reader = new FileReader(filePath);
            BufferedReader fReader = new BufferedReader(reader);

            while ((data = fReader.readLine()) != null) {
                list.add(data);
            }

            fReader.close();
            reader.close();

        } catch (Exception e) {
            System.out.println("Cannot load file");
        }
        return list;
    }

    public void displayStaffs() {
        for (Staff staff : this.staffs) {
            System.out.println(staff);
        }
    }

    public <E> boolean writeFile(String path, ArrayList<E> list) {
        try {
            FileWriter writer = new FileWriter(path);
            for (E tmp : list) {
                writer.write(tmp.toString());
                writer.write("\r\n");
            }

            writer.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("Error.");
            return false;
        }

        return true;
    }

    public <E> boolean writeFile(String path, E object) {
        try {
            FileWriter writer = new FileWriter(path);

            writer.write(object.toString());
            writer.close();

            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("Error.");
            return false;
        }

        return true;
    }
}
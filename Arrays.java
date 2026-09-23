public class Arrays{
    public static void main(String[] args ){
        int marks[] = {70,80,65,90,85};
        int Average;
        int sum = 0;
        for(int i;i=0;i<5;i++){
            int sum = sum + marks[i];
        }
         int average = sum/5;
          system.out.println("sum = " + sum );
        system.out.println("average =" + average); 
    }
}
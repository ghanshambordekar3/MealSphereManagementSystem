public class Main {
    public static void main(String args[]) {
        login obj = new login();                  // Step 1: Create an instance of the login class
        obj.show();                               // Step 2: Display the login window
        obj.setDefaultCloseOperation(3);          // Step 3: Set the close operation (3 = EXIT_ON_CLOSE)
    }
}

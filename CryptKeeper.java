import java.util.Scanner;
import java.io.*;

public class CryptKeeper {
    static Scanner sc = new Scanner(System.in);
    static CryptKeeper keep =  new CryptKeeper();
    static FileReader fr;
    static {
        try {
            fr = new FileReader("CryptKeeperData.txt");
        } catch (FileNotFoundException e) {
            System.out.println("Error: File not found");
        }
    }
    static BufferedReader br = new BufferedReader(fr);
    void authorize() throws IOException {
        System.out.println("Enter master password: ");
        String masterPassword = sc.nextLine();
        String retrieveMaster = " ";
        while((retrieveMaster = br.readLine()) != null){
            if(retrieveMaster.equals(masterPassword)){
                keep.menu();
            } else
                System.out.println("Wrong password");
        }
    }
    void menu() throws IOException{
        System.out.println("Welcome to CryptKeeper \n---Menu---");
        System.out.println("1. Add password");
        System.out.println("2. View password");
        System.out.println("3. Delete password");
        System.out.println("4. Exit");
        int ch = sc.nextInt();
        switch(ch){
            case 1:
                keep.add();
                break;
            case 2:
                keep.view();
                break;
            case 3:
                keep.delete();
                break;
            case 4:
                System.exit(0);
            default:
                System.out.println("Invalid choice number");
        }
    }
    void add() throws IOException{
        System.out.println("Enter site: ");
        String site = sc.nextLine();
        System.out.println("Enter username: ");
        String username = sc.nextLine();
        System.out.println("Enter password: ");
        String  password = sc.nextLine();

        FileWriter fw = new FileWriter("CryptKeeperData.txt", true);
        fw.write(site + "|" + username + "|" + password + "\n");
    }
    void view() throws IOException{
        String line  = br.readLine();
        String data[] = line.split("\\|");
        System.out.print(data[0] + "|" + data[1] + "|" + data[2]);
    }
    void  delete() throws IOException{
        BufferedWriter bw = new BufferedWriter(new FileWriter("temp.txt"));
        String line = " ";
        System.out.println("Enter site to delete: ");
        String target = sc.nextLine();
        while((line=br.readLine()) != null){
            String[] data = line.split("\\|");
            String site = data[0];
            if(!site.equals(target))
                bw.write(line + "\n");
        }
        new File("vault.txt").delete();
        new File("temp.txt").renameTo(new File("vault.txt"));
    }
}

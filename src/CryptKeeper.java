import javax.swing.*;
import java.util.Scanner;
import java.io.*;

public class CryptKeeper {
    static Scanner sc = new Scanner(System.in);
    static CryptKeeper keep = new CryptKeeper();
    String masterPassword;
    void authorize() throws IOException {
        BufferedReader mbr = new BufferedReader(new FileReader("master.txt"));
        System.out.println("Enter master password: ");
        masterPassword = sc.nextLine();
        String retrieveMaster = mbr.readLine();
        if(retrieveMaster!=null&&retrieveMaster.equals(masterPassword)){
            keep.menu();
        }else{
            System.out.println("Wrong password");
        }
        mbr.close();
    }
    void menu() throws IOException{
        System.out.println("Welcome to CryptKeeper \n---Menu---");
        System.out.println("1. Add password");
        System.out.println("2. View password");
        System.out.println("3. Delete password");
        System.out.println("4. Generate strong password");
        System.out.println("5. Exit");
        int ch = sc.nextInt();
        sc.nextLine();
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
                keep.generate();
                break;
            case 5:
                System.exit(0);
                break;
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
        String password = sc.nextLine();
        String key = masterPassword, encrypted = "";
        int keyIndex = 0;
        for(int i = 0; i<password.length(); i++){
            keyIndex = i%key.length();
            char keyChar = key.charAt(keyIndex);
            encrypted += (char)(password.charAt(i)^keyChar);
        }
        System.out.println("Saved Successful!");
        FileWriter fw = new FileWriter("CryptKeeperData.txt",true);
        fw.write(site+"|"+username+"|"+encrypted+"\n");
        fw.close();
    }
    void view() throws IOException{
        BufferedReader br = new BufferedReader(new FileReader("CryptKeeperData.txt"));
        String line = " ", key = masterPassword;
        int keyIndex = 0;
        while((line=br.readLine())!=null){
            String decrypted = "";
            String data[]=line.split("\\|");
            for(int i = 0; i<data[2].length(); i++){
                char ch = data[2].charAt(i);
                keyIndex = i%key.length();
                char keyChar = key.charAt(keyIndex);
                decrypted += (char)(ch^keyChar);
            }
            System.out.println(data[0]+"|"+data[1]+"|"+decrypted);
        }
        br.close();
    }
    void delete() throws IOException{
        BufferedReader br = new BufferedReader(new FileReader("CryptKeeperData.txt"));
        BufferedWriter bw = new BufferedWriter(new FileWriter("temp.txt"));
        String line=" ";
        System.out.println("Enter site to delete: ");
        String target = sc.nextLine();
        while((line=br.readLine())!=null){
            String[] data=line.split("\\|");
            String site=data[0];
            if(!site.equals(target))
                bw.write(line+"\n");
        }
        br.close();
        bw.close();
        new File("CryptKeeperData.txt").delete();
        new File("temp.txt").renameTo(new File("CryptKeeperData.txt"));
    }
    void generate() throws IOException {
        String allChars = " ";
        for(int i = 48; i<=57; i++)
            allChars += (char)i;
        for(int i = 65; i<=90; i++)
            allChars += (char)i;
        for(int i = 97; i<=122; i++)
            allChars += (char)i;
        allChars += "!@#$%^&*";
        System.out.println("Enter password length: ");
        int length = sc.nextInt();
        int randomIndex = 0;
        String password =" ";
        //Generate:
        for(int i = 0; i<length; i++){
            randomIndex = (int)(Math.random()*allChars.length());
            char ch = allChars.charAt(randomIndex);
            password += ch; //append
        }
        System.out.println("Generated password: "+password);
        System.out.println("Save this password? ");
        String ch = sc.nextLine().toUpperCase();
        if(ch.equals("Y") || ch.equals("YES")){
            System.out.println("Enter site: ");
            String site = sc.nextLine();
            System.out.println("Enter username: ");
            String username = sc.nextLine();
            String key = masterPassword,  encrypted = " ";
            int keyIndex = 0;
            for(int i = 0; i<password.length(); i++){
                keyIndex = i%key.length();
                char keyChar = key.charAt(keyIndex);
                encrypted += (char)(password.charAt(i)^keyChar);
            }
            FileWriter fw = new FileWriter("CryptKeeperData.txt", true);
            fw.write(site + "|" + username + "|" + encrypted + "\n");
            fw.close();
        }
        else
            System.exit(0);
    }
    public static void main(String[] args) throws IOException {
        keep.authorize();
    }
}
package Model;

import java.io.*;

public class RegisterManager {
    public static int userAlreadyRegistered(User user){
        int userAmount;

        String fileName = "registeredUsers.txt";
        String f = new File("").getAbsolutePath().concat("\\SmartPiano_server\\src\\Model\\Assets\\" + fileName);

        String line = null;

        try{
            FileReader fileReader = new FileReader(f);

            BufferedReader bufferedReader = new BufferedReader(fileReader);

            line = bufferedReader.readLine();
            userAmount = Integer.parseInt(line);
            //returns 0 when user is not registered yet
            for (int i = 0; true; i++){
                try{
                    line = bufferedReader.readLine();
                    if(line == null) break;

                    //returns 1 if the username already exists
                    if (line.equals(user.getUsername())) return 1;

                    //returns 2 if the mail already exists
                    if (line.equals(user.getMail())) return 2;
                } catch (IOException | NullPointerException e){
                    break;
                }
            }
            bufferedReader.close();
            return 0;
        }
        catch(FileNotFoundException ex){
            System.out.println(
                    "Unable to open file '" +
                            fileName + "'");
            System.out.println("\nMore info: ");
            ex.printStackTrace();
        }
        catch(IOException ex){
            System.out.println(
                    "Error reading file '" +
                            fileName + "'");
            System.out.println("\nMore info: ");
            ex.printStackTrace();
        }

        return -1;
    }

    public static void registerUser(User user) throws IOException {
        String fileName = "registeredUsers.txt";
        String f = new File("").getAbsolutePath().concat("\\SmartPiano_server\\src\\Model\\Assets\\" + fileName);
        FileWriter write = new FileWriter(f, true);
        PrintWriter print_line = new PrintWriter(write);

        print_line.printf("\n" + user.getUsername() + "\n" + user.getMail() + "\n" + user.getPassword());
        print_line.close();
    }
}

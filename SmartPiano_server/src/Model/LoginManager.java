package Model;

import java.io.*;

import Controller.LoginController;


public class LoginManager {
    public static boolean checkLoging(User user){
        boolean correct = false;
        int userAmount;

        String fileName = "registeredUsers.txt";
        String f = new File("").getAbsolutePath().concat("\\SmartPiano_server\\src\\Model\\Assets\\" + fileName);

        String line = null;

        try{
            FileReader fileReader = new FileReader(f);

            BufferedReader bufferedReader = new BufferedReader(fileReader);

            line = bufferedReader.readLine();
            userAmount = Integer.parseInt(line);

            for(int i = 0; true; i++){
                try{
                    line = bufferedReader.readLine();
                    if(line.equals(user.getUsername())){
                        line = bufferedReader.readLine();
                        line = bufferedReader.readLine();
                        if(line.equals(user.getPassword())) {
                            correct = true;
                            break;
                        }
                    } else {
                        line = bufferedReader.readLine();
                    }
                } catch (IOException | NullPointerException e){
                    break;
                }
            }
            bufferedReader.close();
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
        return correct;
    }

    public void showError(String user_or_password_incorrect) {
        LoginController.showError(user_or_password_incorrect);
    }
}

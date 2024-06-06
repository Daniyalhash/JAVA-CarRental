import GUI.introGUI;
import javax.swing.*;

public class AppLauncher {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable(){
            @Override
            public void run(){
                // instantiate a LoginFormGUI obj and make it visible
                new introGUI().setVisible(true);

                // check user test
//                System.out.println(MyJDBC.checkUser("username1234"));

                // check register test
//                System.out.println(MyJDBC.register("username1234", "password"));

                // check validate login test
//                System.out.println(MyJDBC.validateLogin("username1234", "password"));
            }
        });
    }
}

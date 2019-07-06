package cs203.project09;

import cs203.project08.App;
import cs203.battlearena.AbstractGym;

public class NewApp extends App{

    public NewApp() {
        super(new FinalGym());
    }

    public NewApp(AbstractGym gym) {
        super(gym);
    }

    public static void main(String[] args) {
        App app = new NewApp();
        javax.swing.SwingUtilities.invokeLater(new Runnable()
        {public void run() {
        try {
            app.showFrame();
            
        }

        catch (Exception ex) {
            ex.printStackTrace();
            System.exit(1);

        }
    }
});
        

    }

   
    }

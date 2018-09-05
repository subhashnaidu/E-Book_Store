
// Subhash Naidu
// Enterprise Computing
// Project 1 - E-book Store

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException
    {

/*

      This program utilizes a MVC (Model View Controller) Architecture.
      The model class holds and process all the information associated with the store. Such as parsing
      inventory and storing the shopping cart for the user.

      The createGUI class is the view part of MVC it creates and manages the view of the program.

      The Controller class is what manages interaction between the model and the view. The view and model
      don't know about the existence of the other. The controller is the middle man that manages both model and view.

*/



        Model model = new Model();
        createGUI gui = new createGUI();

        Controller eStore = new Controller(model,gui);
    }



}



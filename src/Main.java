import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) throws IOException
    {
        Model model = new Model();
        createGUI gui = new createGUI();

//        ArrayList<Book> books = model.getInventory();
//
//        for (int i = 0; i < books.size(); i++)
//        {
//            System.out.println(books.get(i).getPrice());
//        }

//        LocalDate local = LocalDate.now();
//        System.out.println(local);
//
//        LocalDateTime datetime = LocalDateTime.now();
//        System.out.println(datetime);
//
//        gui.showFinishDialog(4,new ArrayList<Book>(),52.45);


        Controller eStore = new Controller(model,gui);

//        gui.showFinishDialog(3,model.getShoppingCart(),32.33);




    }



}



import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Controller
{
    Model model;
    createGUI view;

    Controller(Model model, createGUI gui )
    {
        this.model = model;
        this.view = gui;



        this.view.addExitListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                System.exit(1);
            }
        });

        this.view.addProcessListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.setNumItems(view.getNumItems());
//                model.setQuantity(view.getQuantity());
                Book book = model.findBook(view.getBookID());
                if (book == null)
                {
                    view.showConfirmDialog(false,0,view.getBookID());
                }
                else
                {
                    String id = Integer.toString(book.bookId);
                    String title = book.getBookTitle();
                    String unitPrice = Float.toString(book.getPrice());

//                    System.out.println(book.getPrice());
//                    System.out.println(unitPrice);

                    String quantity = Integer.toString(view.getQuantity());
//                    String discount = Integer.toString(model.calculateDiscounts(view.getQuantity()));
                    double subTotal = book.getPrice()*view.getQuantity();

                    double discount = model.calculateDiscounts(view.getQuantity());
                    int discountint = (int)(discount*100);
                    double total = (subTotal - (discount*subTotal));

                    System.out.println(total);

                    String totalstr = String.format("%.2f",total);
                    String info = id + title + " $"+ unitPrice + " " + quantity + " "+ discountint +"% " + " $" +totalstr;

//                    System.out.println(quantity);

                    view.setItemInfo(info);
                    view.process.setEnabled(false);
                    view.confirm.setEnabled(true);
                }
            }
        });

       this.view.addConfirmListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               model.addToShoppingCart(view.getBookID(),view.getQuantity());
               view.setSubTotal(model.getShoppingCart());
               view.showConfirmDialog(true,model.getIteration(),view.getBookID());
               view.clearTextFields();
               model.iteration++;
               view.updateLables(model.getIteration());
               view.viewOrder.setEnabled(true);
               view.process.setEnabled(true);
               view.confirm.setEnabled(false);

               if(model.getIteration()-1 == model.numItems)
               {
                   view.finishOrder.setEnabled(true);
                   view.process.setEnabled(false);
                   view.confirm.setEnabled(false);
               }
           }
       });

       this.view.addFinishOrderListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {

               Date now = new Date();
               SimpleDateFormat sdfDate = new SimpleDateFormat("MM/dd/yyyy, h:mm:ss a");//dd/MM/yyyy
//        sdfDate.setTimeZone(TimeZone.getTimeZone("EDT"));
               String strDate = sdfDate.format(now);

                view.showFinishDialog(view.getNumItems(),model.getShoppingCart(),view.getSubTotal(), strDate);

                SimpleDateFormat sdfTransID = new SimpleDateFormat("ddMMyyyyhhmm");
                String transID = sdfTransID.format(now);

                try {
                    model.createTransactionsFile(transID , strDate);
                }
                catch (IOException f)
                {
                    System.out.println("IO Exception could not print file");
                }

                view.finishOrder.setEnabled(false);

           }
       });

       this.view.addViewOrderListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {

               view.showViewOrderDialog(model.getShoppingCart());


           }
       });

        this.view.addNewOrderListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.clearShoppingCart();
                model.setIteration(1);
                view.updateLables(1);
                view.process.setEnabled(true);
                view.confirm.setEnabled(false);
                view.viewOrder.setEnabled(false);
                view.finishOrder.setEnabled(false);
                view.clearTextFieldsForNewOrder();
            }
        });
    }





}

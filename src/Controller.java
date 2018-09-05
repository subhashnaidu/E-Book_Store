import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


/*
   The Controller manages the interaction between the Model and the view.
   It adds action listeners to the buttons in the view, and defines what needs to be done
   When a button is pressed.
*/

public class Controller
{
    Model model;
    createGUI view;

    Controller(Model model, createGUI gui )
    {
        this.model = model;
        this.view = gui;

        // Adds Listener for the Exit Button
        this.view.addExitListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                System.exit(1);
            }
        });

        // Adds Process for the Process Button
        this.view.addProcessListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.setNumItems(view.getNumItems());
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
                    String quantity = Integer.toString(view.getQuantity());

                    double subTotal = book.getPrice()*view.getQuantity();
                    double discount = model.calculateDiscounts(view.getQuantity());
                    int discountint = (int)(discount*100);
                    double total = (subTotal - (discount*subTotal));

                    String totalstr = String.format("%.2f",total);
                    String info = id + title + " $"+ unitPrice + " " + quantity + " "+ discountint +"% " + " $" +totalstr;

                    view.setItemInfo(info);
                    view.process.setEnabled(false);
                    view.confirm.setEnabled(true);
                }
            }
        });

        // Adds Listener for Confirm Order Button
       this.view.addConfirmListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               model.addToShoppingCart(view.getBookID(),view.getQuantity());
               view.setSubTotal(model.getShoppingCart());
               view.showConfirmDialog(true,model.getIteration(),view.getBookID());
               view.clearTextFields();
               model.iteration++;
               view.updateLabels(model.getIteration());
               view.viewOrder.setEnabled(true);
               view.process.setEnabled(true);
               view.confirm.setEnabled(false);

                /* If the num of items that the user set it == to the internal iteration, then disable buttons so user
                cannot add more items to his order.*/
               if(model.getIteration()-1 == model.numItems)
               {
                   view.bookIDLabel.setText(null);
                   view.quantityLabel.setText(null);
                   view.finishOrder.setEnabled(true);
                   view.process.setEnabled(false);
                   view.confirm.setEnabled(false);
               }
           }
       });

       // Adds Listener for Finish Button
       this.view.addFinishOrderListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {

               Date now = new Date();
               SimpleDateFormat sdfDate = new SimpleDateFormat("MM/dd/yyyy, h:mm:ss a");//dd/MM/yyyy
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

        // Adds Listener for View Order Button.
       this.view.addViewOrderListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               view.showViewOrderDialog(model.getShoppingCart());
           }
       });

       // Adds Listener for New Order Listener
        this.view.addNewOrderListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.clearShoppingCart();
                model.setIteration(1);
                view.updateLabels(1);
                view.process.setEnabled(true);
                view.confirm.setEnabled(false);
                view.viewOrder.setEnabled(false);
                view.finishOrder.setEnabled(false);
                view.clearTextFieldsForNewOrder();
            }
        });
    }





}

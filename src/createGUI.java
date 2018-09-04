import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class createGUI
{
    JFrame frame;
    JPanel formPanel;
    JPanel buttonPanel;

    JLabel numItemsLabel = new JLabel("Enter number of items in this order :");
    JLabel bookIDLabel = new JLabel("Enter Book ID for item #1 :");
    JLabel quantityLabel = new JLabel("Enter quantity for Item #1 :");
    JLabel infoLable = new JLabel("Item #1 info :");
    JLabel subtotalLable = new JLabel("Order Subtotal :");

    JTextField numItems = new JTextField("");
    JTextField bookId = new JTextField("");
    JTextField quantity = new JTextField("");
    JTextField itemInfo = new JTextField("");
    JTextField subTotal = new JTextField("");

    JButton process = new JButton("Process Item #1");
    JButton confirm = new JButton("Confirm Item #1");
    JButton viewOrder = new JButton("View Order");
    JButton finishOrder = new JButton("Finish Order");
    JButton newOrder = new JButton("New Order");
    JButton exit = new JButton("Exit");


    createGUI()
    {
        formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(0,2));
        formPanel.setBackground(Color.WHITE);
        formPanel.add(numItemsLabel);
        formPanel.add(numItems);
        formPanel.add(bookIDLabel);
        formPanel.add(bookId);
        formPanel.add(quantityLabel);
        formPanel.add(quantity);
        formPanel.add(infoLable);
        formPanel.add(itemInfo);
        formPanel.add(subtotalLable);
        formPanel.add(subTotal);

        buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.BLACK);
        buttonPanel.setLayout(new GridLayout(0,6));
        buttonPanel.add(process);
        buttonPanel.add(confirm);
        buttonPanel.add(viewOrder);
        buttonPanel.add(finishOrder);
        buttonPanel.add(newOrder);
        buttonPanel.add(exit);

        frame = new JFrame("E-Store");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setLayout(new GridLayout(2,0));
        frame.pack();
        frame.setSize(800,300);

        frame.add(formPanel);
        frame.add(buttonPanel,new BorderLayout());

        confirm.setEnabled(false);
        viewOrder.setEnabled(false);
        finishOrder.setEnabled(false);

        itemInfo.setEnabled(false);
        subTotal.setEnabled(false);
    }

    public void addExitListener(ActionListener listener)
    {
        exit.addActionListener(listener);
    }

    public void addProcessListener(ActionListener listener)
    {
        process.addActionListener(listener);
    }

    public void addConfirmListener(ActionListener listener)
    {
        confirm.addActionListener(listener);
    }

    public void addViewOrderListener(ActionListener listener)
    {
        viewOrder.addActionListener(listener);
    }

    public void addFinishOrderListener(ActionListener listener)
    {
        finishOrder.addActionListener(listener);
    }

    public void addNewOrderListener(ActionListener listener)
    {
        newOrder.addActionListener(listener);
    }

    public void updateLables(int iteration)
    {
        process.setText("Process Item #"+iteration);
        confirm.setText("Confirm Item #"+iteration);
        bookIDLabel.setText("Enter Book ID for item #"+iteration +":");
        quantityLabel.setText("Enter quantity for Item#" + iteration + ":");
        infoLable.setText("Item #" + iteration + ":");
    }

    public void clearTextFields()
    {
        //Clear Text fields after new order button is pressed.
//        numItems.setText("");
        bookId.setText("");
        quantity.setText("");
//        itemInfo.setText("");
//        subTotal.setText("");

    }

    public void clearTextFieldsForNewOrder()
    {
        //Clear Text fields after new order button is pressed.
        numItems.setText("");
        bookId.setText("");
        quantity.setText("");
        itemInfo.setText("");
        subTotal.setText("");

    }


    public int getNumItems()
    {
        return Integer.parseInt(numItems.getText());
    }

    public int getBookID()
    {
        return Integer.parseInt(bookId.getText());
    }

    public int getQuantity()
    {
        return Integer.parseInt(quantity.getText());
    }

    public void setItemInfo(String info)
    {

        itemInfo.setText(info);
    }

    public void setSubTotal(ArrayList<Book> order)
    {
        double subtotal;
        double total = 0;
        double discount = 0;

        for (int i = 0; i < order.size(); i++)
        {
            subtotal = (order.get(i).price) * (order.get(i).quantity);
            discount = order.get(i).getDiscount();
            total += ((subtotal) - (discount*subtotal));
        }

        String totalStr = String.format("%.2f",total);

        subTotal.setText(totalStr);
    }

    public double getSubTotal()
    {
        return Double.parseDouble(this.subTotal.getText());
    }

    public void showConfirmDialog(boolean confirm, int iteration, int bookId)
    {
        if (confirm == true)
        {
            JOptionPane.showMessageDialog(this.frame,"Item #"+iteration + " accepted");
        }
        else
        {
            JOptionPane.showMessageDialog(this.frame,"Book ID "+bookId + " not in file");
        }
    }

    public void showViewOrderDialog(ArrayList<Book> orderList)
    {
        double subtotal = 0;
        double discount = 0;
        double total = 0;

        String msg = "";

        for (int i = 0; i < orderList.size(); i++)
        {
            subtotal = (orderList.get(i).price) * (orderList.get(i).quantity);
            discount = orderList.get(i).getDiscount();
            total = ((subtotal) - (discount*subtotal));

            String totalStr = String.format("%.2f",total);


            msg +=  (i+1)+") "+ orderList.get(i).bookId + orderList.get(i).bookTitle + " " + "$" + orderList.get(i).price + " "
                    + orderList.get(i).quantity + " " + (int)(orderList.get(i).getDiscount()*100) + "% " + " $" + totalStr + "\n";
        }

        JOptionPane.showMessageDialog(this.frame,msg);

    }

    public void showFinishDialog(int numItems, ArrayList<Book> orderList, double subTotal, String strDate)
    {
        double subtotal = 0;
        double discount = 0;
        double total = 0;

//        SimpleDateFormat sdfDate = new SimpleDateFormat("MM/dd/yyyy, h:mm:ss a");//dd/MM/yyyy
////        sdfDate.setTimeZone(TimeZone.getTimeZone("EDT"));
//        Date now = new Date();
//        String strDate = sdfDate.format(now);

        String msg = "";

        for (int i = 0; i < orderList.size(); i++)
        {
            subtotal = (orderList.get(i).price) * (orderList.get(i).quantity);
            discount = orderList.get(i).getDiscount();
            total = ((subtotal) - (discount*subtotal));

            String totalStr = String.format("%.2f",total);


            msg +=  (i+1)+") "+ orderList.get(i).bookId + orderList.get(i).bookTitle + " " + "$" + orderList.get(i).price + " "
                    + orderList.get(i).quantity + " " + (int)(orderList.get(i).getDiscount()*100) + "% " + " $" + totalStr + "\n";
        }

        double taxAmount = (subTotal*.06);
        String taxAmountStr =  String.format("%.2f",taxAmount);
        double orderTotal = subTotal + taxAmount;
        String orderTotalStr = String.format("%.2f",orderTotal);

        JOptionPane.showMessageDialog(this.frame,"Date: " +  strDate +" EDT"+"\n\n" + "Number of line items: "+numItems + "\n\n Item# / ID / Title / Price / Qty / Disc% / Subtotal:\n\n" + msg + "\n\n" +
        "Order subtotal: $"+subTotal + "\n\nTax rate: 6%\n\n" + "Tax amount: " +taxAmountStr+"\n\n" + "Order total: " + orderTotalStr + "\n\n" + "Thanks for shopping at the Ye Olde Book Shoppe!" );
    }
}

import java.io.*;
import java.util.ArrayList;

// Model of the MVC architecture.
// Holds and process the Inventory and shopping cart
public class Model
{
    private ArrayList<Book> inventoryList;
    private ArrayList<Book> shoppingCart;
    int numItems;
    int iteration = 1;
    int quantity = 0;

    Model() throws IOException
    {
        // Opens inventory file and parses information.
        FileReader inventory = new FileReader("inventory.txt");
        this.inventoryList = parseInventory(inventory);
        shoppingCart = new ArrayList<>();
    }

    public void addToShoppingCart(int bookId, int quantity)
    {
        for (int i = 0; i < inventoryList.size(); i++)
        {
            if (bookId == inventoryList.get(i).bookId)
            {
                Book book = inventoryList.get(i);
                book.quantity = quantity;
                book.discount = calculateDiscounts(book.quantity);
                this.shoppingCart.add(book);
//                numItems--;
                break;
            }
        }
    }

    public void clearShoppingCart()
    {
        this.shoppingCart.clear();
    }

    private static ArrayList<Book> parseInventory(FileReader file) throws IOException
    {
        BufferedReader bufRead = new BufferedReader(file);
        String line;
        ArrayList<Book> booksInventory = new ArrayList<>();

        while (((line = bufRead.readLine()) != null))
        {
            String[] s = line.split(",");
            int bookID =Integer.parseInt(s[0]);
            String bookName = s[1];
            float price = Float.parseFloat(s[2]);
//            System.out.println("Double Value: "+val);
//            BigDecimal bigPrice = new BigDecimal(val);
//            System.out.println("Big Decimal Value: "+bigPrice);
            booksInventory.add(new Book(bookID,bookName,price,0));
        }

        return booksInventory;
    }

    public ArrayList<Book> getInventory()
    {
        return this.inventoryList;
    }

    public ArrayList<Book> getShoppingCart()
    {
        return this.shoppingCart;
    }

    public void setNumItems(int numItems)
    {
        this.numItems = numItems;
    }

    public void setQuantity(int quant)
    {
        this.quantity = quant;
    }

    public Book findBook(int bookId)
    {
        for (int i = 0; i < inventoryList.size(); i++)
        {
            if (bookId == inventoryList.get(i).bookId)
            {
                return inventoryList.get(i);
            }
        }

        return null;
    }

    public int getIteration()
    {
        return this.iteration;
    }

    public void setIteration(int iter)
    {
        this.iteration = iter;
    }

    // Returns the various discounts for buying different quantities.
    public double calculateDiscounts(int quantity)
    {
        double discount = 0;

        if (quantity >= 5 && quantity <= 9)
        {
            discount = .1;
        }
        else if(quantity >= 10 && quantity <= 14)
        {
            discount = .15;
        }
        else if(quantity >= 15)
        {
            discount = .20;
        }

        return discount;
    }

    // Creates the transactions file after clicking the finish order button.
    public void createTransactionsFile(String transID , String date) throws IOException
    {
        FileWriter fileWriter = new FileWriter("transactions.txt",true);
        PrintWriter printWriter = new PrintWriter(fileWriter);

        for (int i = 0; i < shoppingCart.size(); i++)
        {
            System.out.print("Printing text to transactions file...");

            double subtotal = (shoppingCart.get(i).price) * (shoppingCart.get(i).quantity);
            double discount = shoppingCart.get(i).getDiscount();
            double total = ((subtotal) - (discount*subtotal));
            String totalStr = String.format("%.2f",total);

            String str = transID + ", " + shoppingCart.get(i).getBookId() + ", " +
                    shoppingCart.get(i).getBookTitle() + ", " + shoppingCart.get(i).getPrice()
                    + ", " + shoppingCart.get(i).getQuantity() + ", " + shoppingCart.get(i).getDiscount() +
                    ", " + totalStr + ", " + date + "\n";
            printWriter.print(str);
        }
        printWriter.close();
    }




}

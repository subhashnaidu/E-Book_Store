
public class Book
{
    int bookId;
    String bookTitle;
    float price;
    int quantity;
    double discount;


    Book(int bookID,String bookTitle,float price, int quant)
    {
        this.bookId = bookID;
        this.bookTitle = bookTitle;
        this.price = price;
        this.quantity = quant;
    }

    public int getBookId()
    {
        return this.bookId;
    }

    public String getBookTitle()
    {
        return this.bookTitle;
    }

    public float getPrice()
    {
        return this.price;
    }

    public int getQuantity()
    {
        return this.quantity;
    }

    public double getDiscount()
    {
        return this.discount;
    }

    public void setDiscount(double discount)
    {
        this.discount = discount;
    }




}

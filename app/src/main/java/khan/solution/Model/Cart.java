package khan.solution.Model;

public class Cart {

    private String Price,Details,Quantity,Image_Uri,cart_id;

    public Cart() {
    }

    public Cart(String price, String details, String quantity, String image_Uri, String cart_id) {
        Price = price;
        Details = details;
        Quantity = quantity;
        Image_Uri = image_Uri;
        this.cart_id = cart_id;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getDetails() {
        return Details;
    }

    public void setDetails(String details) {
        Details = details;
    }

    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String quantity) {
        Quantity = quantity;
    }

    public String getImage_Uri() {
        return Image_Uri;
    }

    public void setImage_Uri(String image_Uri) {
        Image_Uri = image_Uri;
    }

    public String getCart_id() {
        return cart_id;
    }

    public void setCart_id(String cart_id) {
        this.cart_id = cart_id;
    }
}

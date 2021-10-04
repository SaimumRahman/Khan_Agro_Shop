package khan.solution.Model;

public class Products {

    private String cart_id,details,imageuri,price,quantity;

    public Products() {
    }

    public Products(String cart_id, String details, String imageuri, String price, String quantity) {
        this.cart_id = cart_id;
        this.details = details;
        this.imageuri = imageuri;
        this.price = price;
        this.quantity = quantity;
    }

    public String getCart_id() {
        return cart_id;
    }

    public void setCart_id(String cart_id) {
        this.cart_id = cart_id;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getImageuri() {
        return imageuri;
    }

    public void setImage_uri(String image_uri) {
        this.imageuri = image_uri;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}

package khan.solution.Model;

public class DishPost {

    private String Dish_Date,Dish_Description,Dish_Image_Uri,Dish_Price,Dish_Quantity,Dish_Time,Post_Id;

    public DishPost() {
    }

    public DishPost(String dish_Date, String dish_Description, String dish_Image_Uri, String dish_Price, String dish_Quantity, String dish_Time, String post_Id) {
        Dish_Date = dish_Date;
        Dish_Description = dish_Description;
        Dish_Image_Uri = dish_Image_Uri;
        Dish_Price = dish_Price;
        Dish_Quantity = dish_Quantity;
        Dish_Time = dish_Time;
        Post_Id = post_Id;
    }

    public String getDish_Date() {
        return Dish_Date;
    }

    public void setDish_Date(String dish_Date) {
        Dish_Date = dish_Date;
    }

    public String getDish_Description() {
        return Dish_Description;
    }

    public void setDish_Description(String dish_Description) {
        Dish_Description = dish_Description;
    }

    public String getDish_Image_Uri() {
        return Dish_Image_Uri;
    }

    public void setDish_Image_Uri(String dish_Image_Uri) {
        Dish_Image_Uri = dish_Image_Uri;
    }

    public String getDish_Price() {
        return Dish_Price;
    }

    public void setDish_Price(String dish_Price) {
        Dish_Price = dish_Price;
    }

    public String getDish_Quantity() {
        return Dish_Quantity;
    }

    public void setDish_Quantity(String dish_Quantity) {
        Dish_Quantity = dish_Quantity;
    }

    public String getDish_Time() {
        return Dish_Time;
    }

    public void setDish_Time(String dish_Time) {
        Dish_Time = dish_Time;
    }

    public String getPost_Id() {
        return Post_Id;
    }

    public void setPost_Id(String post_Id) {
        Post_Id = post_Id;
    }
}

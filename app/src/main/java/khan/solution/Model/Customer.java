package khan.solution.Model;

import com.google.firebase.auth.FirebaseUser;

public class Customer {

    private String user_details;
    private String user_id;

    public Customer() {
    }

    public Customer(String user_details, String user_id) {
        this.user_details = user_details;
        this.user_id = user_id;
    }

    public String getUser_details() {
        return user_details;
    }

    public void setUser_details(String user_details) {
        this.user_details = user_details;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}
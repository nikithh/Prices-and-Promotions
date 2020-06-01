package com.publicis.sapient.vendorretailer.beans;

import javax.validation.constraints.NotNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "admin")
public class Admin {

    @Id
    private int adminId;
    @NotNull
    private String userName;
    @NotNull
    private String password;

    public int getAdminId() {
        return this.adminId;
    }

    public void setAdminId(final int adminId) {
        this.adminId = adminId;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(final String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Admin [adminId=" + this.adminId + ", userName=" + this.userName + ", password=" + this.password + "]";
    }

}

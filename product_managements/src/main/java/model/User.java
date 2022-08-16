package model;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.*;

public class User {
    private int id;
    @NotEmpty(message = "Name not empty")
    @Length(min = 2, max = 50, message = "Lenght of Name form 2 - 50 character ")
    private String fullName;
    @Min(18)
    @Max(100)
    private int age;
    @NotEmpty
    @Pattern(regexp = "((84|0[1|2|3|4|5|6|7|8|9])+([0-9]{8})\\b)", message = "Số điện thoại phải bắt đầu từ số 0 và có 10 chữ số !!!")
    private String phone;
    @NotEmpty
    @Email(message = "Định dạng không đúng")
    @Pattern(regexp = "^([a-zA-Z0-9!#$%&'*+\\/=?^_`{|}~-]+(?:\\.[a-zA-Z0-9!#$%&'*+\\/=?^_`{|}~-]+)*@(?:[a-zA-Z0-9](?:[a-zA-Z0-9-]*[a-zA-Z0-9])?\\.)+[a-zA-Z0-9](?:[a-zA-Z0-9-]*[a-zA-Z0-9])?)$", message = "Format mail not right")
    private String email;
    private String passwordUser;
    private int address;

    public User() {
    }

    public User(int id, String fullName, int age, String phone, String email, String passwordUser, int address) {
        this.id = id;
        this.fullName = fullName;
        this.age = age;
        this.phone = phone;
        this.email = email;
        this.passwordUser = passwordUser;
        this.address = address;
    }

    public User(String fullName, int age, String phone, String email, String passwordUser, int address) {
        this.fullName = fullName;
        this.age = age;
        this.phone = phone;
        this.email = email;
        this.passwordUser = passwordUser;
        this.address = address;
    }


    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswordUser() {
        return passwordUser;
    }

    public void setPasswordUser(String passwordUser) {
        this.passwordUser = passwordUser;
    }

    public int getAddress() {
        return address;
    }

    public void setAddress(int address) {
        this.address = address;
    }
}

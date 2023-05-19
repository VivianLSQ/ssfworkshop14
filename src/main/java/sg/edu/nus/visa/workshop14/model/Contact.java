package sg.edu.nus.visa.workshop14.model;

import java.time.LocalDate;
import java.time.Period;
import java.util.Random;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

public class Contact{
    @NotNull(message ="Name cannot be empty")
    @Size(min=3, max=64, message ="Name should be between 3 to 64 characters ")
    private String name; 

    @NotEmpty(message ="Email cannot be empty")
    @Email(message ="Invalid email")
    private String email;

    @Size(min=7, message ="Invalid Phone Number")
    private String phoneNumber;

    @Past(message ="Date of birth should not be from the future")
    @NotNull(message ="Date of birth is a mandatory field")
    @DateTimeFormat (pattern = "MM-DD-YYYY")  
    private LocalDate dateOfBirth; 

    //Calculate age based on Date of Birth (in Getter and Setters section)
    //Validate the age 
    @Min(value =10, message ="User must be above 10 years old")
    @Max(value =100, message ="User must be not be older than 100 years old")
    private int age; 

    //Constructor 
    public Contact(String name, String email, String phoneNumber) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }
    public Contact(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    //Create ID 
    private String id;

    //public Contact model;

    public Contact() {
        this.id = generatedId(); 
    }

    private String generatedId(){
        Random r = new Random();
        StringBuilder sb = new StringBuilder();
        //Put (int i) between () in method signature if so 
            // while(sb.length() , i){
            //    sb.append(Integer.toHexString(r.nextInt(i)));
        while(sb.length()< 8){
            sb.append(Integer.toHexString(r.nextInt()));
            //remove the bound '8' in nextInt()
        } 
        return sb.toString().substring(0, 8); 
    }
    //Generate Getter and Setters for getID
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    

    //Getters and Setters 
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    } 

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }


    public void setDateOfBirth(LocalDate dateOfBirth) {
        //Calculate age here based on Date of Birth 
        int calculatedAge =0; 
        if (dateOfBirth != null){
            calculatedAge = Period.between(dateOfBirth,LocalDate.now()).getYears();
        }
        this.age = calculatedAge; 
        this.dateOfBirth = dateOfBirth;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    //ToString
    @Override
    public String toString() {
        return "Contact [name=" + name + ", email=" + email + ", phoneNumber=" + phoneNumber + "]";
    }
}
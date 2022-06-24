package entity;

import annotation.Entity;
import annotation.Field;

import java.io.Serializable;

public class Actor extends BaseEntity implements Serializable {
    @Field("actor_id")
    private Integer id;

    @Field("first_name")
    private String firstName;

    @Field("last_name")
    private String lastName;

    public Actor() {
    }

    public Actor(Integer id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}

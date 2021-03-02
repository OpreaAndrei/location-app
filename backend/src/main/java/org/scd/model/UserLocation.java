package org.scd.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.mapping.Set;
import org.scd.model.security.Role;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


@Entity
@Table(name = "USERS_LOCATION")
public class UserLocation implements Serializable {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name="latitude",nullable = false,length = 45)
    private String latitude;

    @Column(name="longitude",nullable = false,length = 45)
    private String longitude;

    @Column(name="date",nullable = false,length = 45)
    private Date date;

    @ManyToOne(fetch=FetchType.LAZY,optional = false)
    @JoinColumn(name="user_id",nullable = false)
    @JsonIgnore
    private User user;

    public UserLocation(){
    }

    public UserLocation(String latitude, String longitude,Date date, User user) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.date = date;
        this.user = user;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}

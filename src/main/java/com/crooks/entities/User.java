/*
 * Copyright (c) 2016.
 */

package com.crooks.entities;

import javax.persistence.*;

/**
 * Created by johncrooks on 6/27/16.
 */
@Entity
@Table(name="users")
public class User {
    @Id
    @GeneratedValue
    int id;

    @Column(nullable = false)
    String username;
    @Column(nullable = false)
    String address;
    @Column(nullable = false)
    String email;

    public User(String username, String address, String email) {
        this.username = username;
        this.address = address;
        this.email = email;
    }

    public User() {

    }

    public User(int id, String username, String address, String email) {

        this.id = id;
        this.username = username;
        this.address = address;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

package com.coforge.dummy.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"firstName"}),
        @UniqueConstraint(columnNames = {"email"})
})
public class User {
    private static final long serialVersionUID = 7156526077883281623L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;

}

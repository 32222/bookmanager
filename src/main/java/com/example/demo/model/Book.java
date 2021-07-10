package com.example.demo.model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    
    @NotBlank
    @Size(max = 40)
    private String title;

    @NotBlank
    @Size(max = 40)
    private String author;

    @NotBlank
    @Size(max = 40)
    private String publisher;

    private int status;
    private int medium;
    
    @DateTimeFormat(iso = ISO.DATE)
    private LocalDate purchaseDate;
    
    @ManyToOne
    private SiteUser user;
}

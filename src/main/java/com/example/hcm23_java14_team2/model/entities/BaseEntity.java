package com.example.hcm23_java14_team2.model.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;

import jakarta.persistence.Entity;
import jakarta.persistence.MappedSuperclass;
import lombok.*;

import java.util.Date;
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public class BaseEntity {
    private String createBy;

    @Column(name="createDate", updatable = false)
    @CreatedDate
    private Date createDate;

    private String modifiedBy;

    @Column(name="modifiedDate")
    @LastModifiedBy
    private Date modifiedDate;
}
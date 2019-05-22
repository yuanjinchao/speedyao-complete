package com.speedyao.bill.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by speedyao on 2019/5/20.
 */
@Data
@Entity
@Table(name="consume_record")
public class ConsumeRecord {
    @Id
    @GeneratedValue
    long id;
    String user;
    String month;
    String date;
    String time;
    String shop;
    Double amount;
}

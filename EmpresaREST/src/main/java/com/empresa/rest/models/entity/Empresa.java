/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.empresa.rest.models.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import lombok.Data;
/**
 *
 * @author erick
 */
@Data
@Entity
@Table(name = "empresas")
public class Empresa implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    @Size(min = 5, max = 50)
    @Column(nullable = false)
    private String nombre;

    @NotEmpty
    @Size(min = 9)
    @Column(nullable = false)
    private String nit;

    @Column(name = "fecha_fundacion")
    @Temporal(TemporalType.DATE)
    private Date fechaFundacion;

    @Column(nullable = false)
    @Size(min = 10, max = 100)
    @NotEmpty
    private String direccion;
}

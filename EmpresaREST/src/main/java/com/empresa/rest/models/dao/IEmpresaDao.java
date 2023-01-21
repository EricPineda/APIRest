/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.empresa.rest.models.dao;

import com.empresa.rest.models.entity.Empresa;
import org.springframework.data.repository.CrudRepository;


public interface IEmpresaDao extends CrudRepository<Empresa, Long>{
    
}
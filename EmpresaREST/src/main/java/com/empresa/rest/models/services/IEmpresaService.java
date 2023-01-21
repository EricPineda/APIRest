/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.empresa.rest.models.services;

import com.empresa.rest.models.entity.Empresa;
import java.util.List;

/**
 *
 * @author erick
 */
public interface IEmpresaService {

    public List<Empresa> ListarEmpresas();

    public Empresa encontrarEmpresaPorId(Long id);

    public Empresa guardarEmpresa(Empresa empresa);

    public void eliminarEmpresa(Long id);
}
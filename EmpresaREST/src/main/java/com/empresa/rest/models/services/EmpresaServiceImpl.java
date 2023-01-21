/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.empresa.rest.models.services;
import com.empresa.rest.models.dao.IEmpresaDao;
import com.empresa.rest.models.entity.Empresa;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
/**
 *
 * @author erick
 */


@Service
public class EmpresaServiceImpl implements IEmpresaService {

    @Autowired
    private IEmpresaDao empresaDao;

    @Override
    @Transactional(readOnly = true)
    public List<Empresa> ListarEmpresas() {
        return (List<Empresa>) empresaDao.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Empresa encontrarEmpresaPorId(Long id) {
        return empresaDao.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public Empresa guardarEmpresa(Empresa empresa) {
        return empresaDao.save(empresa);
    }

    @Override
    @Transactional(readOnly = true)
    public void eliminarEmpresa(Long id) {
        empresaDao.deleteById(id);
    }

}


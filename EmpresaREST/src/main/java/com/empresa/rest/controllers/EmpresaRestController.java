/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.empresa.rest.controllers;

import com.empresa.rest.models.entity.Empresa;
import com.empresa.rest.models.services.IEmpresaService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author erick
 */

@RestController
@RequestMapping("/empresas")
public class EmpresaRestController {

    @Autowired
    private IEmpresaService empresaService;

    @ApiOperation("Listar todas las empresas")
    @ApiResponse(code = 200, message = "OK")
    @GetMapping
    public List<Empresa> listarEmpresas() {
        return empresaService.ListarEmpresas();
    }

    @ApiOperation("Buscar una empresa por id")
    @ApiParam(value = "ID de la empresa", required = true, example = "1")
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 404, message = "NOT FOUND"),
        @ApiResponse(code = 500, message = "INTERNAL SERVER ERROR")})
    @GetMapping("/{id}")
    public ResponseEntity<?> empresaPorId(@PathVariable Long id) {

        Empresa empresa = null;
        Map<String, Object> response = new HashMap<>();

        try {
            empresa = empresaService.encontrarEmpresaPorId(id);
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al realizar la consulta en la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (empresa == null) {
            response.put("mensaje", "El registro : ".concat(id.toString().concat(" no existe en la base de datos!")));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<Empresa>(empresa, HttpStatus.OK);
    }

    @ApiOperation("Agregar una empresa")
    @ApiResponses({
        @ApiResponse(code = 201, message = "CREATED"),
        @ApiResponse(code = 404, message = "NOT FOUND"),
        @ApiResponse(code = 500, message = "INTERNAL SERVER ERROR"),
        @ApiResponse(code = 400, message = "BAD REQUEST")})
    @PostMapping
    public ResponseEntity<?> guardarEmpresa(@Valid @RequestBody Empresa empresa, BindingResult result) {

        Empresa empresaNueva = null;
        Map<String, Object> response = new HashMap<>();

        if (result.hasErrors()) {

            List<String> errors = result.getFieldErrors()
                    .stream()
                    .map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage())
                    .collect(Collectors.toList());

            response.put("errors", errors);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }

        try {
            empresaNueva = empresaService.guardarEmpresa(empresa);
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al realizar el insert en la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("mensaje", "La empresa ha sido creado con éxito!");
        response.put("Empresa", empresaNueva);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }

    @ApiOperation("Modificar una empresa")
    @ApiParam(value = "ID de la empresa", required = true, example = "1")
    @ApiResponses({
        @ApiResponse(code = 201, message = "CREATED"),
        @ApiResponse(code = 404, message = "NOT FOUND"),
        @ApiResponse(code = 500, message = "INTERNAL SERVER ERROR"),
        @ApiResponse(code = 400, message = "BAD REQUEST")})
    @PutMapping("/{id}")
    public ResponseEntity<?> editarEmpresa(@Valid @RequestBody Empresa empresa, BindingResult result, @PathVariable Long id) {

        Empresa empresaActual = empresaService.encontrarEmpresaPorId(id);

        Empresa empresaModificada = null;

        Map<String, Object> response = new HashMap<>();

        if (result.hasErrors()) {

            List<String> errors = result.getFieldErrors()
                    .stream()
                    .map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage())
                    .collect(Collectors.toList());

            response.put("errors", errors);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }

        if (empresaActual == null) {
            response.put("mensaje", "Error: no se pudo editar, la empresa con ID: "
                    .concat(id.toString().concat(" no existe en la base de datos!")));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }

        try {

            empresaActual.setNombre(empresa.getNombre());
            empresaActual.setNit(empresa.getNit());
            empresaActual.setFechaFundacion(empresa.getFechaFundacion());
            empresaActual.setDireccion(empresa.getDireccion());

            empresaModificada = empresaService.guardarEmpresa(empresaActual);

        } catch (DataAccessException e) {
            response.put("mensaje", "Error al actualizar la empresa en la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("mensaje", "La empresa ha sido actualizado con éxito!");
        response.put("empresa", empresaModificada);

        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }

    @ApiOperation("Eliminar una empresa")
    @ApiParam(value = "ID de la empresa", required = true, example = "1")
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 500, message = "INTERNAL SERVER ERROR"),})
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarEmpresa(@PathVariable Long id) {

        Map<String, Object> response = new HashMap<>();

        try {
            empresaService.eliminarEmpresa(id);
        } catch (DataAccessException e) {
            response.put("mensaje", "Error el registro que se desea eliminar no existe en la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("mensaje", "La empresa ha eliminado con éxito!");
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
    }

}


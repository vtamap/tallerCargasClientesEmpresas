package com.banco.nominabackend.controller;

import com.banco.nominabackend.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.MediaType;
import java.util.Map;

@RestController
@RequestMapping("/api/clientes")
@CrossOrigin(origins = "http://localhost:4200") // Permite que Angular se conecte
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @PostMapping(value = "/cargar-archivo", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> cargarArchivo(@RequestParam("archivo") MultipartFile archivo) {
        if (archivo.isEmpty()) {
            return ResponseEntity.badRequest().body("El archivo no contiene data, por favor reviselo");
        }

        try {
            // Llamamos al servicio para procesar
            Map<String, Object> resultado = clienteService.procesarArchivo(archivo);
            return ResponseEntity.ok(resultado);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error al procesar el archivo de texto, contactese con Soporte de la plataforma: " + e.getMessage());
        }
    }
}
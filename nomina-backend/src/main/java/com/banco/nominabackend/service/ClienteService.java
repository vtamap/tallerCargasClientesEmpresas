package com.banco.nominabackend.service;

import com.banco.nominabackend.model.Cliente;
import com.banco.nominabackend.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public Map<String, Object> procesarArchivo(MultipartFile archivo) throws Exception {
        List<String> errores = new ArrayList<>();
        int exitosos = 0;

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(archivo.getInputStream()))) {
            String linea;
            int numLinea = 0;
            while ((linea = reader.readLine()) != null) {
                numLinea++;
                if (linea.trim().isEmpty()) continue;

                // Suponemos formato: TIPO|ID|FECHA|VALOR|EMAIL|CELULAR
                String[] datos = linea.split("\\|");

                if (datos.length < 6) {
                    errores.add("Línea " + numLinea + ": Formato incompleto.");
                    continue;
                }

                // Aquí vendrán las validaciones que tienes en tu lista...
                // Por ahora, solo intentamos mapear un cliente básico
                try {
                    validarYGuardar(datos);
                    exitosos++;
                } catch (Exception e) {
                    errores.add("Línea " + numLinea + ": " + e.getMessage());
                }
            }
        }

        Map<String, Object> respuesta = new HashMap<>();
        respuesta.put("exitosos", exitosos);
        respuesta.put("errores", errores);
        return respuesta;
    }

    private void validarYGuardar(String[] datos) throws Exception {
        // Implementaremos las validaciones del checklist en el siguiente paso
        // Ejemplo rápido: Validación de Celular (10 dígitos)
        if (datos[5].length() != 10) {
            throw new Exception("El celular debe tener 10 dígitos.");
        }

        // Si todo está bien, se guardaría en DB
    }
}
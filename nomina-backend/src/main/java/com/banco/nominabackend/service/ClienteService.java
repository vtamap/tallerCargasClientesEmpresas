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

        // 1. Mapeo para los datos que vienen en el txt
        String tipoId = datos[0].trim();
        String numeroId = datos[1].trim();
        String fechaIngreso = datos[2].trim();
        String valorNominaStr = datos[3].trim();
        String email = datos[4].trim();
        String celular = datos[5].trim();

        // 2. Validación de cellular (pide que sea 10 dígitos)
        if (!celular.matches("\\d{10}")) {
            throw new Exception("El número de celular valido debe ser de 10 dígitos numéricos.");
        }

        // 3. Validación de mail (Formato estándar)
        if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            throw new Exception("El formato del correo electrónico no es correcto, reviselo.");
        }

        // 4. Validación de Valor Nómina (Debe ser numérico y positivo)
        Double valorNomina;
        try {
            valorNomina = Double.parseDouble(valorNominaStr);
            if (valorNomina <= 0) throw new Exception();
        } catch (Exception e) {
            throw new Exception("El valor de contenido de la nómina debe ser un número positivo.");
        }

        // 5. Verificación de Duplicados (Negocio)
        if (clienteRepository.findByNumeroIdentificacion(numeroId).isPresent()) {
            throw new Exception("El cliente con identificación " + numeroId + " ya existe en la base de datos.");
        }

        // 6. Si pasa todo, creamos el objeto Cliente
        Cliente nuevoCliente = new Cliente();
        nuevoCliente.setTipoIdentificacion(tipoId);
        nuevoCliente.setNumeroIdentificacion(numeroId);
        nuevoCliente.setFechaIngreso(fechaIngreso);
        nuevoCliente.setValorNomina(valorNomina);
        nuevoCliente.setCorreoElectronico(email);
        nuevoCliente.setNumeroCelular(celular);

        // Generación de cuenta con seguro para IDs cortos
        String sufijoId = (numeroId.length() >= 4) ? numeroId.substring(numeroId.length() - 4) : numeroId;
        nuevoCliente.setNumeroCuenta("CTA-" + System.currentTimeMillis() + "-" + sufijoId);

        // 7. Simulación de servicio externo y guardado (UNA SOLA VEZ)
        consultarDatabook(nuevoCliente);
        clienteRepository.save(nuevoCliente);
    }

    // Método para simular la consulta al servicio externo Databook
    private void consultarDatabook(Cliente cliente) {
        // En la vida real, aquí harías un RestTemplate.getForObject(...)
        // Por ahora, simularemos que Databook nos devuelve datos según el ID
        if (cliente.getNumeroIdentificacion().equals("1234567890")) {
            cliente.setNombres("Victor Javier");
            cliente.setApellidos("Tama Perez");
            cliente.setFechaNacimiento("1983-17-15");
        } else {
            cliente.setNombres("Usuario");
            cliente.setApellidos("Genérico");
            cliente.setFechaNacimiento("1985-01-01");
        }
    }
}
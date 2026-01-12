import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ClienteService {

  // URL probada en ambiente local de prueba
  private apiUrl = 'http://localhost:8080/api/clientes/cargar-archivo';

  constructor(private http: HttpClient) { }

  // Método para enviar el archivo
  subirArchivo(archivo: File): Observable<any> {
    const formData = new FormData();
    // 'archivo' es el nombre que espera el @RequestParam en Java
    formData.append('archivo', archivo);

    return this.http.post(this.apiUrl, formData);
  }
}

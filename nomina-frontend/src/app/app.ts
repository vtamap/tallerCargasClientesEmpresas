import { Component, ChangeDetectorRef } from '@angular/core'; // <--- 1. Importar ChangeDetectorRef
import { ClienteService } from './services/cliente';

@Component({
  selector: 'app-root',
  standalone: false,
  templateUrl: './app.html',
  styleUrls: ['./app.css']
})
export class App {
  title = 'nomina-frontend';

  archivoSeleccionado: File | null = null;
  resultado: any = null;
  cargando = false;

  constructor(
    private clienteService: ClienteService,
    private cd: ChangeDetectorRef // <--- 2. Inyectarlo aquí
  ) {}

  onFileSelected(event: any) {
    if (event.target.files.length > 0) {
      this.archivoSeleccionado = event.target.files[0];
      this.resultado = null;
    }
  }

  subir() {
    if (!this.archivoSeleccionado) return;

    this.cargando = true;
    console.log('Enviando archivo...'); // Log 

    this.clienteService.subirArchivo(this.archivoSeleccionado).subscribe({
      next: (res) => {
        console.log('Respuesta recibida:', res); // Ver en la consola del navegador
        this.resultado = res;
        this.cargando = false;

        this.cd.detectChanges(); // Obliga a actualizar la pantalla
      },
      error: (err) => {
        console.error('Error:', err);
        alert('Ocurrió un error. Revisa la consola.');
        this.cargando = false;

        this.cd.detectChanges();
      }
    });
  }
}

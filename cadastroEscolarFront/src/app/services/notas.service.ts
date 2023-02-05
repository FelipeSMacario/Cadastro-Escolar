import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { NotasDTO } from '../models/DTO/notasDTO';
import { Notas } from '../models/notas';

@Injectable({
  providedIn: 'root'
})
export class NotasService {

  constructor(private httpClient : HttpClient) { }

  private url = "http://localhost:8080/notas/";

  salvarNotas(nota : NotasDTO) : Observable<NotasDTO>{
    return this.httpClient.post<NotasDTO>(this.url + "cadastrar", nota);
  }
}

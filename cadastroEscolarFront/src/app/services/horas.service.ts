import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Horas } from '../models/horas';

@Injectable({
  providedIn: 'root'
})
export class HorasService {

  constructor(
    private httpClient : HttpClient
    ) { }

  private url = "http://localhost:8080/horas";


  findAll() : Observable<Horas[]>{
    return this.httpClient.get<Horas[]>(this.url + "/listar");
  }
}

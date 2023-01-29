import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Dia } from '../models/dia';

@Injectable({
  providedIn: 'root'
})
export class DiaService {

  constructor(private httpClient : HttpClient) { }

  private url = "http://localhost:8080/dia/";

  findAll() : Observable<Dia[]>{
    return this.httpClient.get<Dia[]>(this.url + "listar");
  }
}

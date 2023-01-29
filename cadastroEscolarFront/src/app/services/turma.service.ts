import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Turma } from '../models/turma';

@Injectable({
  providedIn: 'root'
})
export class TurmaService {

  constructor(private httpClient : HttpClient) { }

  private url = "http://localhost:8080/turma/";

  findAll() : Observable<Turma[]>{
    return this.httpClient.get<Turma[]>(this.url + "listar");
  }
}

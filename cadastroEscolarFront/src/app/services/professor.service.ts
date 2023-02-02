import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Pessoa } from '../models/pessoa';


@Injectable({
  providedIn: 'root'
})
export class ProfessorService {

  constructor(private httpClient : HttpClient) { }

  private url = "http://localhost:8080/professor/";

  findAll() : Observable<Pessoa[]>{
    return this.httpClient.get<Pessoa[]>(this.url + "listar");
  }
}

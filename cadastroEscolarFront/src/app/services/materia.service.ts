import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { MateriaDto } from '../models/DTO/materiaDTO';


@Injectable({
  providedIn: 'root'
})
export class MateriasService {

  constructor(private httpClient : HttpClient) { }

  private url = "http://localhost:8080/materias/";

  saveMateria(materia : MateriaDto) : Observable<Object>{
    return this.httpClient.post(this.url + "cadastrar", materia);
  }

}
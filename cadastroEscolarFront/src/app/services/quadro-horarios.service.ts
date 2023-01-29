import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Horas } from '../models/horas';
import { QuadroHorario } from '../models/quadroHorario';

@Injectable({
  providedIn: 'root'
})
export class QuadroHorariosService {

  constructor(private httpClient : HttpClient) { }

  private url = "http://localhost:8080/quadroHorario/";

  findByMatricula(matricula : number) : Observable<QuadroHorario[]>{
    return this.httpClient.get<QuadroHorario[]>(this.url + "buscar/HorarioPorMatricula/" + matricula);
  }

  findByTurma(idTurma : number) : Observable<QuadroHorario[]>{
    return this.httpClient.get<QuadroHorario[]>(this.url + "buscar/horariosPorTurma/" + idTurma);
  }

  findHorasLivres(diaId : number, salaId : number) : Observable<Horas[]>{
    return this.httpClient.get<Horas[]>(this.url + "buscar/horasPorDia/" + diaId + "/" + salaId);
  }
}



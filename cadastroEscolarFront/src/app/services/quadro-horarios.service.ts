import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { QuadroDTO } from '../models/DTO/quadroDTO';
import { Horas } from '../models/horas';
import { Materia } from '../models/materia';
import { QuadroHorario } from '../models/quadroHorario';
import { DefaultResponse } from '../models/Response/defaultResponse';

@Injectable({
  providedIn: 'root'
})
export class QuadroHorariosService {

  constructor(private httpClient : HttpClient) { }

  private url = "http://localhost:8080/api-aulas/quadroHorario/";

  findByMatricula(matricula : number, pagina? : number) : Observable<DefaultResponse>{
    return this.httpClient.get<DefaultResponse>(this.url + "buscar/HorarioPorMatricula/" + matricula + "?page=" + pagina);
  }

  findByTurma(idTurma : number, pagina? : number) : Observable<DefaultResponse>{
    return this.httpClient.get<DefaultResponse>(this.url + "buscar/horariosPorTurma/" + idTurma + "?page=" + pagina);
  }

  findHorasLivres(diaId : number, salaId : number) : Observable<DefaultResponse>{
    return this.httpClient.get<DefaultResponse>(this.url + "buscar/horasPorDia/" + diaId + "/" + salaId);
  }

  saveHorasLivres(quadro : QuadroDTO) : Observable<DefaultResponse>{
    return this.httpClient.post<DefaultResponse>(this.url + "cadastrar", quadro);
  }

  findById(id : number) : Observable<DefaultResponse>{
    return this.httpClient.get<DefaultResponse>(this.url + "busca/HorarioPorId/" + id);
  }
  updateQuadro(quadro : QuadroDTO) : Observable<DefaultResponse>{
    return this.httpClient.put<DefaultResponse>(this.url + "atualizar", quadro);
  }
  filtrarMaterias(dia: number, hora : number) : Observable<DefaultResponse>{
    return this.httpClient.get<DefaultResponse>(this.url + "busca/MateriaUsada/" + dia + "/" + hora);
  }
}



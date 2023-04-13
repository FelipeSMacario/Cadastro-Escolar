import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { NotaAtualizacaoDTO } from '../models/DTO/notaAtualizacaoDTO';
import { NotasDTO } from '../models/DTO/notasDTO';
import { DefaultResponse } from '../models/Response/defaultResponse';

@Injectable({
  providedIn: 'root'
})
export class NotasService {

  constructor(private httpClient : HttpClient) { }

  private url = "http://localhost:8080/api-aulas/notas/";

  salvarNotas(nota : NotasDTO) : Observable<DefaultResponse>{
    return this.httpClient.post<DefaultResponse>(this.url + "cadastrar", nota);
  }

  findByTurmaAndNotas(idTurma : number, idNotas : number) : Observable<DefaultResponse>{
    return this.httpClient.get<DefaultResponse>(this.url + "buscar/" + idTurma + "/" + idNotas);
  }
  findById(id : number) : Observable<DefaultResponse>{
    return this.httpClient.get<DefaultResponse>(this.url + "buscar/" + id);
  }
  updateNota(notas : NotaAtualizacaoDTO):Observable<DefaultResponse>{
    return this.httpClient.put<DefaultResponse>(this.url + "alterar", notas);
  }
  filtrarNotas(matricula : number) : Observable<DefaultResponse>{
    return this.httpClient.get<DefaultResponse>(this.url + "buscar/porMatricula/" + matricula);
  }
  filtraAlunos(idTurma : number, idNotas : number) : Observable<DefaultResponse>{
    return this.httpClient.get<DefaultResponse>(this.url + "buscarAlunos/" + idTurma + "/" + idNotas);
  }
}

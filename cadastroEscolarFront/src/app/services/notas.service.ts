import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { NotaAtualizacaoDTO } from '../models/DTO/notaAtualizacaoDTO';
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

  findByTurmaAndNotas(idTurma : number, idNotas : number) : Observable<Notas[]>{
    return this.httpClient.get<Notas[]>(this.url + "buscar/" + idTurma + "/" + idNotas);
  }
  findById(id : number) : Observable<Notas>{
    return this.httpClient.get<Notas>(this.url + "buscar/" + id);
  }
  updateNota(notas : NotaAtualizacaoDTO):Observable<Object>{
    return this.httpClient.put<Object>(this.url + "alterar", notas);
  }
  filtrarNotas(matricula : number) : Observable<Notas[]>{
    return this.httpClient.get<Notas[]>(this.url + "buscar/porMatricula/" + matricula);
  }
}

import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { EntradaTurmaAlunoDTO } from '../models/DTO/entradaAlunoTurmaDTO';
import { SaidaTurmaAlunoDTO } from '../models/DTO/saidaTurmaAlunoDTO';
import { Pessoa } from '../models/pessoa';
import { DefaultResponse } from '../models/Response/defaultResponse';
import { Turma } from '../models/turma';

@Injectable({
  providedIn: 'root'
})
export class TurmaService {

  constructor(private httpClient : HttpClient) { }

  private url = "http://localhost:8080/turma/";
  private urlTurmaALuno = "http://localhost:8080/turmaAlunos/";

  findAll() : Observable<Turma[]>{
    return this.httpClient.get<Turma[]>(this.url + "listar");
  }

  findAlunsByNumero(numero : number) : Observable<Pessoa[]>{
    return this.httpClient.get<Pessoa[]>(this.urlTurmaALuno + "buscarTurma/porNumero/" + numero);
  }

  findByTurma(id : number) : Observable<SaidaTurmaAlunoDTO>{
    return this.httpClient.get<SaidaTurmaAlunoDTO>(this.urlTurmaALuno + "buscarTurma/" + id);
  }

  findAlunoByAno(ano : number) : Observable<Pessoa[]>{
    return this.httpClient.get<Pessoa[]>(this.urlTurmaALuno + "buscarAluno/porAno/" + ano)
  }

  saveTurma(entrada : EntradaTurmaAlunoDTO) : Observable<DefaultResponse>{
    return this.httpClient.post<DefaultResponse>(this.urlTurmaALuno + "cadastrar", entrada);
  }
}


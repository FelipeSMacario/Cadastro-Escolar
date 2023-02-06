import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { SaidaTurmaAlunoDTO } from '../models/DTO/saidaTurmaAlunoDTO';
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

  findByTurma(id : number) : Observable<SaidaTurmaAlunoDTO>{
    return this.httpClient.get<SaidaTurmaAlunoDTO>(this.urlTurmaALuno + "buscarTurma/" + id);
  }
}

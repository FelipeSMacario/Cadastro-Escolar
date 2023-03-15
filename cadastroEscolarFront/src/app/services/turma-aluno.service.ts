import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { AlunoTurmaDTO } from '../models/DTO/alunoTurmaDTO';
import { EntradaTurmaAlunoDTO } from '../models/DTO/entradaAlunoTurmaDTO';
import { DefaultResponse } from '../models/Response/defaultResponse';

@Injectable({
  providedIn: 'root'
})
export class TurmaAlunoService {

  private urlTurmaALuno = "http://localhost:8081/turmaAlunos/";

  constructor(private httpClient : HttpClient) { }

  findAllTurmaAluno() : Observable<DefaultResponse>{
    return this.httpClient.get<DefaultResponse>(this.urlTurmaALuno);
  }

  saveTurma(entrada : EntradaTurmaAlunoDTO) : Observable<DefaultResponse>{
    return this.httpClient.post<DefaultResponse>(this.urlTurmaALuno + "cadastrar", entrada);
  }

  findAlunsByNumero(numero : number) : Observable<DefaultResponse>{
    return this.httpClient.get<DefaultResponse>(this.urlTurmaALuno + "buscarTurma/porNumero/" + numero);
  }

  findTurmaAlunoByNome(nome : string): Observable<DefaultResponse>{
    return this.httpClient.get<DefaultResponse>(this.urlTurmaALuno + "buscarTurma/porNome/" + nome);
  }

  findAlunoByAno(ano : number) : Observable<DefaultResponse>{
    return this.httpClient.get<DefaultResponse>(this.urlTurmaALuno + "buscarAluno/porAno/" + ano)
  }

  findTurmaByMatricula(matricula : number) : Observable<DefaultResponse>{
    return this.httpClient.get<DefaultResponse>(this.urlTurmaALuno + "buscarTurma/porMatricula/" + matricula)
  }

  findTurmaAluno(matricula : number, turma : number) : Observable<DefaultResponse>{
    return this.httpClient.get<DefaultResponse>(this.urlTurmaALuno + "buscaTurma/porMatricula/porTurma/" + matricula + "/" + turma);
  }

  deleteTurmaAluno(matricula : number, turma : number) : Observable<DefaultResponse>{
    return this.httpClient.delete<DefaultResponse>(this.urlTurmaALuno + "removerAluno/" + matricula + "/" + turma);
  }

  updateAlunoTurma(saida : AlunoTurmaDTO) : Observable<DefaultResponse>{
    return this.httpClient.put<DefaultResponse>(this.urlTurmaALuno + "atualizarAlunoTurma", saida);
  }

  findAlunoPorTurma(id : number) : Observable<DefaultResponse>{
    return this.httpClient.get<DefaultResponse>(this.urlTurmaALuno + "buscaAluno/porTurma/" + id)
  }

  findAlunoTurmaById(id : number) : Observable<DefaultResponse>{
    return this.httpClient.get<DefaultResponse>(this.urlTurmaALuno + "buscarTurma/" + id);
  }
  
}

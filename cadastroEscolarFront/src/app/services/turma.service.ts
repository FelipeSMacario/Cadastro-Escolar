import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { AlunoTurmaDTO } from '../models/DTO/alunoTurmaDTO';
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

  findAlunsByNumero(numero : number) : Observable<AlunoTurmaDTO[]>{
    return this.httpClient.get<AlunoTurmaDTO[]>(this.urlTurmaALuno + "buscarTurma/porNumero/" + numero);
  }

  findAllTurmaAluno() : Observable<AlunoTurmaDTO[]>{
    return this.httpClient.get<AlunoTurmaDTO[]>(this.urlTurmaALuno);
  }

  findTurmaAlunoByNome(nome : string): Observable<AlunoTurmaDTO[]>{
    return this.httpClient.get<AlunoTurmaDTO[]>(this.urlTurmaALuno + "buscarTurma/porNome/" + nome);
  }

  findTurmaByMatricula(matricula : number) : Observable<DefaultResponse>{
    return this.httpClient.get<DefaultResponse>(this.urlTurmaALuno + "buscarTurma/porMatricula/" + matricula)
  }

  findAlunoByAno(ano : number) : Observable<Pessoa[]>{
    return this.httpClient.get<Pessoa[]>(this.urlTurmaALuno + "buscarAluno/porAno/" + ano)
  }

  saveTurma(entrada : EntradaTurmaAlunoDTO) : Observable<DefaultResponse>{
    return this.httpClient.post<DefaultResponse>(this.urlTurmaALuno + "cadastrar", entrada);
  }

  deleteTurmaAluno(matricula : number, turma : number) : Observable<DefaultResponse>{
    return this.httpClient.delete<DefaultResponse>(this.urlTurmaALuno + "removerAluno/" + matricula + "/" + turma);
  }

  findTurmaAluno(matricula : number, turma : number) : Observable<DefaultResponse>{
    return this.httpClient.get<DefaultResponse>(this.urlTurmaALuno + "buscaTurma/porMatricula/porTurma/" + matricula + "/" + turma);
  }

  findTurmaPorANo(ano : number) : Observable<Turma[]>{
    return this.httpClient.get<Turma[]>(this.url + "buscar/porAno/" + ano)
  }

  findAlunoTurmaById(id : number) : Observable<DefaultResponse>{
    return this.httpClient.get<DefaultResponse>(this.urlTurmaALuno + "buscarTurma/" + id);
  }

  updateAlunoTurma(saida : AlunoTurmaDTO) : Observable<DefaultResponse>{
    return this.httpClient.put<DefaultResponse>(this.urlTurmaALuno + "atualizarAlunoTurma", saida);
  }
  
  findAlunoPorTurma(id : number) : Observable<Pessoa[]>{
    return this.httpClient.get<Pessoa[]>(this.urlTurmaALuno + "buscaAluno/porTurma/" + id)
  }
}


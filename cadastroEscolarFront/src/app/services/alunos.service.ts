import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Pessoa } from '../models/pessoa';

@Injectable({
  providedIn: 'root'
})
export class AlunosService {

  pessoa : Pessoa[] = [];

  constructor(private httpClient : HttpClient) { }

  private url = "http://localhost:8080/alunos";

  findAllAlunos() : Observable<Pessoa[]> {
    return this.httpClient.get<Pessoa[]>(this.url + "/listar");
  }

  findAlunosByNome(nome : string){
    return this.httpClient.get<Pessoa[]>(`${this.url}/buscar/porNome/${nome}`);
  }

  findAlunosByMatricula(matricula : number) : Observable<Pessoa>{
    return this.httpClient.get<Pessoa>(`${this.url}/buscar//${matricula}`);
  }

  salvarAluno(pessoa : Pessoa) : Observable<Object> {
    return this.httpClient.post(this.url + "/cadastrar", pessoa);
  }

}

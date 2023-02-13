import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Pessoa } from '../models/pessoa';

@Injectable({
  providedIn: 'root'
})
export class PessoaService {

  constructor(private httpClient : HttpClient) { }

  private url = "http://localhost:8080/";

  findAllAlunos(cargo : string) : Observable<Pessoa[]> {
    return this.httpClient.get<Pessoa[]>(this.url + cargo + "/listar");
  }

  findAlunosByNome(cargo : string, nome : string){
    return this.httpClient.get<Pessoa[]>(`${this.url}${cargo}/buscar/porNome/${nome}`);
  }

  findAlunosByMatricula(cargo : string, matricula : number) : Observable<Pessoa>{
    return this.httpClient.get<Pessoa>(`${this.url}${cargo}/buscar/${matricula}`);
  }

  salvarAluno(cargo : string, pessoa : Pessoa) : Observable<Object> {
    return this.httpClient.post(this.url + cargo + "/cadastrar", pessoa);
  }

  updateAlunos(cargo : string, pessoa : Pessoa) : Observable<Object> {
    return this.httpClient.put(this.url + cargo +  "/atualizar", pessoa);
  }

}

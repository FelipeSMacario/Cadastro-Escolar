import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Materia } from '../models/materia';
import { DefaultResponse } from '../models/Response/defaultResponse';

@Injectable({
  providedIn: 'root'
})
export class MateriasService {
  userValue: { token: any; };
  logout() {
    throw new Error('Method not implemented.');
  }

  constructor(private httpClient : HttpClient) { }

  private url = "http://localhost:8080/materias";

  listarMateria() : Observable<Materia[]>{
    return this.httpClient.get<Materia[]>(this.url + "/listar");
  }

  cadastrarMateria(materia : Materia) : Observable<DefaultResponse>{
    return this.httpClient.post<DefaultResponse>(this.url + "/cadastrar", materia);
  }

  buscarMateriaPorNome(nome : string) : Observable<DefaultResponse>{
    return this.httpClient.get<DefaultResponse>(this.url + "/buscar/" + nome);
  }

  atualizarMateria(materia : Materia) : Observable<DefaultResponse>{
    return this.httpClient.put<DefaultResponse>(this.url + "/atualizar", materia);
  }

  deletarMateria(id : number) : Observable<DefaultResponse> {
    return this.httpClient.delete<DefaultResponse>(this.url + "/deletar/" + id);
  }
}

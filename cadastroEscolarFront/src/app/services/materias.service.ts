import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Materia } from '../models/materia';

@Injectable({
  providedIn: 'root'
})
export class MateriasService {

  constructor(private httpClient : HttpClient) { }

  private url = "http://localhost:8080/materias";

  listarMateria() : Observable<Materia[]>{
    return this.httpClient.get<Materia[]>(this.url + "/listar");
  }

  cadastrarMateria(materia : Materia) : Observable<Object>{
    return this.httpClient.post(this.url + "/cadastrar", materia);
  }

  buscarMateriaPorNome(nome : string) : Observable<Materia>{
    return this.httpClient.get<Materia>(this.url + "/buscar/" + nome);
  }

  atualizarMateria(materia : Materia) : Observable<Object>{
    return this.httpClient.put(this.url + "/atualizar", materia);
  }

  deletarMateria(id : number) {
    return this.httpClient.delete(this.url + "/deletar/" + id);
  }
}

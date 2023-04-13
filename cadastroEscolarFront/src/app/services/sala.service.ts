import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { DefaultResponse } from '../models/Response/defaultResponse';

@Injectable({
  providedIn: 'root'
})
export class SalaService {

  constructor(private httpClient : HttpClient) { }

  private url = "http://localhost:8080/api-turma-client/sala/";

  findAll() : Observable<DefaultResponse>{
    return this.httpClient.get<DefaultResponse>(this.url + "listar");
  }
}

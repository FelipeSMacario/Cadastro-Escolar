import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { DefaultResponse } from '../models/Response/defaultResponse';

@Injectable({
  providedIn: 'root'
})
export class HorasService {

  constructor(
    private httpClient : HttpClient
    ) { }

  private url = "http://localhost:8080/horas";


  findAll() : Observable<DefaultResponse>{
    return this.httpClient.get<DefaultResponse>(this.url + "/listar");
  }
}

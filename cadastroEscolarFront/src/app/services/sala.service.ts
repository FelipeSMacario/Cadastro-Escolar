import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Sala } from '../models/sala';

@Injectable({
  providedIn: 'root'
})
export class SalaService {

  constructor(private httpClient : HttpClient) { }

  private url = "http://localhost:8080/sala/";

  findAll() : Observable<Sala[]>{
    return this.httpClient.get<Sala[]>(this.url + "listar");
  }
}

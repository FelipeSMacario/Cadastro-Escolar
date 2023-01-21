import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class AlunosService {

  constructor(private httpClient : HttpClient) { }

  url : String = 'http://localhost:8080/alunos';
}

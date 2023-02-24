import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { LoginDTO } from '../models/DTO/loginDTO';
import { DefaultResponse } from '../models/Response/defaultResponse';
import { LoginResponse } from '../models/Response/loginResponse';

@Injectable({
  providedIn: 'root'
})
export class LoginService {


  private url = "http://localhost:8080/login";

  constructor(
    private httpClient : HttpClient
    ) { }

    logar(login : LoginDTO) : Observable<DefaultResponse>{
      return this.httpClient.post<DefaultResponse>(this.url + "/logar", login);
    }

}

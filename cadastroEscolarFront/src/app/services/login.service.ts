import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { LoginDTO } from '../models/DTO/loginDTO';

import { Auth } from '../models/auth';
import { Router } from '@angular/router';
import { JwtHelperService } from '@auth0/angular-jwt';
import { PessoaService } from './pessoa.service';
import { Pessoa } from '../models/pessoa';

@Injectable({
  providedIn: 'root'
})
export class LoginService {


  private url = "http://localhost:8080/auth/oauth/token";
  
  private jwtPayload: any;

  pessoa : Pessoa;

  constructor(
    private http: HttpClient,
    private router: Router,
    private jwtConvert: JwtHelperService,
    private pessoaService : PessoaService
    ) {
    
  }

    login(login : LoginDTO) {

      const headers = new HttpHeaders()
        .append('Authorization', 'Basic YW5ndWxhcjoxMjM0NTY=') //TODO mudar para variavel de ambiente
        .append('Content-Type', 'application/x-www-form-urlencoded');
  
      const body = new HttpParams()
        .set('username', login.usuario)
        .set('password', login.senha)
        .set('grant_type', 'password');
  
      this.http.post<Auth>(this.url, body, {headers, withCredentials: true}).subscribe({
        next: (response) => {
          let jwt = response.access_token;
          localStorage.setItem('token', jwt);
          this.addJwtPayload(jwt);
        },
        error: (err) => console.log('Error', err)
      });
    }
  
    validaPermissao(permisao: string) {
       return this.jwtPayload.authorities.includes(permisao)
    }
  
    validaPermissoes(permissoes: string[]) {
      for (const permissao of permissoes) {
        if (this.validaPermissao(permissao)){
          return true;
        }
      }
      return false;
    }

    setaPessoa(matricula : number){
        this.pessoaService.findUsuarioByMatricula(matricula).subscribe({
          next : people => {
            this.pessoa = people;
            localStorage.setItem("pessoa", JSON.stringify(this.pessoa))
            localStorage.setItem("mostrarMenu", JSON.stringify(true))
            this.router.navigate(['/']).then(() => window.location.reload())
        },error : err => console.log("errou " + err)
        })
    }
  
  
  
    addJwtPayload(jwt: string) {
      this.jwtPayload = this.jwtConvert.decodeToken(jwt);
      this.setaPessoa(this.jwtPayload.user_name);
    }
  
    recuperarjwtPayload(){
      const token = localStorage.getItem('token')!;
      if (this.addJwtPayload === null){
        this.addJwtPayload(token);
      }
    }
  
    logout() {
      localStorage.clear()
      console.log("saiiiiir")
      //TODO excluir o token no backend
    }
  
    refreshToken() {
    }
  



}

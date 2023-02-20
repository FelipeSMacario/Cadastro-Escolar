import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot } from '@angular/router';
import { Observable } from 'rxjs';
import { Pessoa } from '../models/pessoa';

@Injectable({
  providedIn: 'root'
})
export class AlunoGuard implements CanActivate{

  constructor(private router : Router) { }

  aluno : Pessoa

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<boolean> | boolean {
   this.aluno = JSON.parse(localStorage.getItem("pessoa")!);

    if(this.aluno.cargo !== "Aluno"){
      return true
    } else {
      this.router.navigate(['404'])
      return false;
    }
  }
}

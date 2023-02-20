import { Injectable } from '@angular/core';
import { CanActivate, Router, ActivatedRouteSnapshot, RouterStateSnapshot } from '@angular/router';
import { Observable } from 'rxjs';
import { Pessoa } from '../models/pessoa';

@Injectable({
  providedIn: 'root'
})
export class ProfessorGuard implements CanActivate{

  aluno : Pessoa

  constructor(private router : Router) { }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<boolean> | boolean {
    this.aluno = JSON.parse(localStorage.getItem("pessoa")!);
 
     if(this.aluno.cargo !== "Professor"){
       return true
     } else {
       this.router.navigate(['404'])
       return false;
     }
   }
}

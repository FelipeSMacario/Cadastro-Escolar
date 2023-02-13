import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Pessoa } from '../models/pessoa';

@Component({
  selector: 'app-nav-bar',
  templateUrl: './nav-bar.component.html',
  styleUrls: ['./nav-bar.component.css']
})
export class NavBarComponent implements OnInit{

  mostrarMenu : boolean;

  constructor(  private router: Router){}

  pessoa : Pessoa;

  ngOnInit(): void {
    this.pessoa = JSON.parse(localStorage.getItem("pessoa")!);
    this.mostrarMenu = JSON.parse(localStorage.getItem("mostrarMenu")!);
  }

  logout(){
    localStorage.clear();
    localStorage.setItem("mostrarMenu", JSON.stringify(false))
    this.router.navigate(['/login']).then(() => window.location.reload())
  }

}

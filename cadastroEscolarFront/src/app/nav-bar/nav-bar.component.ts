import { Component, OnInit } from '@angular/core';
import { DomSanitizer, SafeResourceUrl } from '@angular/platform-browser';
import { Router } from '@angular/router';
import { Pessoa } from '../models/pessoa';

@Component({
  selector: 'app-nav-bar',
  templateUrl: './nav-bar.component.html',
  styleUrls: ['./nav-bar.component.css']
})
export class NavBarComponent implements OnInit{

  mostrarMenu : boolean;
  isTherePhoto: boolean = false;

  fotoUrl : SafeResourceUrl;

  constructor(  
    private router: Router,
    private sanitizer : DomSanitizer
    ){}

  pessoa : Pessoa;

  ngOnInit(): void {
    this.pessoa = JSON.parse(localStorage.getItem("pessoa")!);
    this.mostrarMenu = JSON.parse(localStorage.getItem("mostrarMenu")!);
    if(this.pessoa.urlFoto){
      this.isTherePhoto = true;
    }
    this.fotoUrl =  this.sanitizer.bypassSecurityTrustResourceUrl(this.pessoa.urlFoto);
  }

  logout(){
    localStorage.clear();
    localStorage.setItem("mostrarMenu", JSON.stringify(false))
    this.router.navigate(['/login']).then(() => window.location.reload())
  }

}

import { Component, OnInit } from '@angular/core';
import { Pessoa } from 'src/app/models/pessoa';

@Component({
  selector: 'app-aula-home',
  templateUrl: './aula-home.component.html',
  styleUrls: ['./aula-home.component.css']
})
export class AulaHomeComponent implements OnInit{
  ngOnInit(): void {
    this.pessoa = JSON.parse(localStorage.getItem("pessoa")!);
  }

  pessoa : Pessoa

 

}

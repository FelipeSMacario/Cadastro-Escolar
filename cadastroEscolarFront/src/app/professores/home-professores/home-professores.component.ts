import { Component, OnInit } from '@angular/core';
import { Pessoa } from 'src/app/models/pessoa';

@Component({
  selector: 'app-home-professores',
  templateUrl: './home-professores.component.html',
  styleUrls: ['./home-professores.component.css']
})
export class HomeProfessoresComponent implements OnInit{

  pessoa : Pessoa;


  ngOnInit(): void {
    this.pessoa = JSON.parse(localStorage.getItem("pessoa")!);
  }

}

import { Component, OnInit } from '@angular/core';
import { Pessoa } from 'src/app/models/pessoa';

@Component({
  selector: 'app-home-alunos',
  templateUrl: './home-alunos.component.html',
  styleUrls: ['./home-alunos.component.css']
})
export class HomeAlunosComponent implements OnInit{

  pessoa : Pessoa;

  ngOnInit(): void {
    this.pessoa = JSON.parse(localStorage.getItem("pessoa")!);
  }

}

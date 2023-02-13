import { Component, OnInit } from '@angular/core';
import { Pessoa } from 'src/app/models/pessoa';

@Component({
  selector: 'app-home-escola',
  templateUrl: './home-escola.component.html',
  styleUrls: ['./home-escola.component.css']
})
export class HomeEscolaComponent implements OnInit{

  pessoa : Pessoa;

  ngOnInit(): void {
    this.pessoa = JSON.parse(localStorage.getItem("pessoa")!);
  }

}

import { Component, OnInit } from '@angular/core';
import { Pessoa } from 'src/app/models/pessoa';

@Component({
  selector: 'app-home-notas',
  templateUrl: './home-notas.component.html',
  styleUrls: ['./home-notas.component.css']
})
export class HomeNotasComponent implements OnInit{

  pessoa : Pessoa;

  ngOnInit(): void {
    this.pessoa = JSON.parse(localStorage.getItem("pessoa")!);
  }

}

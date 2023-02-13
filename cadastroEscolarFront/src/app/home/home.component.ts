import { Component, OnInit } from '@angular/core';
import { Pessoa } from '../models/pessoa';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit{
  pessoa : Pessoa;

  ngOnInit(): void {
    this.pessoa = JSON.parse(localStorage.getItem("pessoa")!);
  }

}

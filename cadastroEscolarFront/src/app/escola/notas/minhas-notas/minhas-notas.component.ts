import { Component, OnInit } from '@angular/core';
import { Notas } from 'src/app/models/notas';
import { Pessoa } from 'src/app/models/pessoa';
import { NotasService } from 'src/app/services/notas.service';

@Component({
  selector: 'app-minhas-notas',
  templateUrl: './minhas-notas.component.html',
  styleUrls: ['./minhas-notas.component.css']
})
export class MinhasNotasComponent implements OnInit{
  notas : Notas[];
  pessoa : Pessoa;
  
  ngOnInit(): void {
    this.pessoa = JSON.parse(localStorage.getItem("pessoa")!);
    this.filtrarNotas(this.pessoa)
  }

  constructor(
    private notaService : NotasService){}

  filtrarNotas(pessoa : Pessoa){
    this.notaService.filtrarNotas(pessoa.matricula).subscribe({
      next : not => {
        this.notas = not;
      },
      error : err => console.log(err)
    })
  }


}

import { Component, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Notas } from 'src/app/models/notas';
import { Pessoa } from 'src/app/models/pessoa';
import { DefaultResponse } from 'src/app/models/Response/defaultResponse';
import { RespostaNotas } from 'src/app/models/Response/respostaNotas';
import { NotasService } from 'src/app/services/notas.service';

@Component({
  selector: 'app-minhas-notas',
  templateUrl: './minhas-notas.component.html',
  styleUrls: ['./minhas-notas.component.css']
})
export class MinhasNotasComponent implements OnInit{
  notas : Notas[];
  pessoa : Pessoa;
  resposta : DefaultResponse;
  respostaNotas : RespostaNotas;
  
  ngOnInit(): void {
    this.pessoa = JSON.parse(localStorage.getItem("pessoa")!);
    this.filtrarNotas(this.pessoa)
  }

  constructor(
    private notaService : NotasService,
    private _snackBar: MatSnackBar){}

  filtrarNotas(pessoa : Pessoa){
    this.notaService.filtrarNotas(pessoa.matricula).subscribe({
      next : not => {
        this.resposta = not;

        if(this.resposta.success){
          this.respostaNotas = this.resposta.data;
          this.notas = this.respostaNotas.notas;
        }else {
          this._snackBar.open(this.resposta.messagem, "", {duration : 3000})
        }
      }
    })
  }


}

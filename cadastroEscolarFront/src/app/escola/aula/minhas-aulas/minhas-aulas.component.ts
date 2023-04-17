import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Page } from 'src/app/models/page';
import { Pessoa } from 'src/app/models/pessoa';
import { QuadroHorario } from 'src/app/models/quadroHorario';
import { DefaultResponse } from 'src/app/models/Response/defaultResponse';
import { ResponseQuadroHorario } from 'src/app/models/Response/responseQuadroHorario';
import { QuadroHorariosService } from 'src/app/services/quadro-horarios.service';

@Component({
  selector: 'app-minhas-aulas',
  templateUrl: './minhas-aulas.component.html',
  styleUrls: ['./minhas-aulas.component.css']
})
export class MinhasAulasComponent implements OnInit{

  quadroHorario : QuadroHorario [] = [];
  quadroHorarioFiltro : QuadroHorario [] = [];
  formulario : FormGroup;
  pessoa : Pessoa;
  resposta : DefaultResponse;
  respostaQuadroHorario : ResponseQuadroHorario;


  ngOnInit(): void {
    this.pessoa = JSON.parse(localStorage.getItem("pessoa")!);
    this.formulario = this.fb.group({
      filtro : [0]
    });
    this.quadroHorarioService.findByMatriculaSemPaginacao(this.pessoa.matricula, this.pessoa.cargo).subscribe({
    next : quad => {
      this.resposta = quad;
      
      if(this.resposta.success){
        this.quadroHorario = this.resposta.data;
      }else {
        this._snackBar.open(this.resposta.messagem, "", {duration : 3000})
      }
      
    }
   })
  
  }

  constructor(
    private quadroHorarioService : QuadroHorariosService,
    private fb : FormBuilder,
    private _snackBar: MatSnackBar
  ){}

  filtrar(valor : number){
    this.quadroHorarioFiltro = this.quadroHorario;
    this.quadroHorarioFiltro = this.quadroHorario.filter(v => this.filtroDia(v.dia.id, valor.toString()));
  }

  filtroDia(dia : number, filtro : string){
    return dia === parseInt(filtro);
  }
   

}

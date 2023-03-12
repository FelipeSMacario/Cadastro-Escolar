import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { QuadroHorario } from 'src/app/models/quadroHorario';
import { DefaultResponse } from 'src/app/models/Response/defaultResponse';
import { ResponseQuadroHorario } from 'src/app/models/Response/responseQuadroHorario';
import { QuadroHorariosService } from 'src/app/services/quadro-horarios.service';

@Component({
  selector: 'app-buscar-aulas',
  templateUrl: './buscar-aulas.component.html',
  styleUrls: ['./buscar-aulas.component.css']
})
export class BuscarAulasComponent implements OnInit{

  formulario : FormGroup;
  quadroHorario : QuadroHorario [];
  resposta : DefaultResponse;
  respostaQuadroHorario : ResponseQuadroHorario;

  ngOnInit(): void {
    this.formulario = this.fb.group({
      filtro : [null, [Validators.required]],
      valor : [0, [Validators.required, Validators.min(1), Validators.max(2)]]
    });
  }

  constructor(
    private quadroHorarioService : QuadroHorariosService,
    private fb : FormBuilder,
    private router: Router,
    private _snackBar: MatSnackBar
  ){}

  filtrar(){
    if(this.formulario.controls["valor"].value == 1){
      this.quadroHorarioService.findByMatricula(this.formulario.controls["filtro"].value).subscribe({
        next: quadr => {
          this.resposta = quadr;

          if(this.resposta.success){
            this.quadroHorario = this.quadroVazio();
            this.respostaQuadroHorario = this.resposta.data;
            this.quadroHorario = this.respostaQuadroHorario.quadroHorarios;
          }else {
            this._snackBar.open(this.resposta.messagem, "", {duration : 3000})
          }
         
        }
      }) 
    }
    if(this.formulario.controls["valor"].value == 2){
      this.quadroHorarioService.findByTurma(this.formulario.controls["filtro"].value).subscribe({
        next: quadr => {
          this.resposta = quadr;

          if(this.resposta.success){
            this.quadroHorario = this.quadroVazio();
            this.respostaQuadroHorario = this.resposta.data;
            this.quadroHorario = this.respostaQuadroHorario.quadroHorarios;
          }else {
            this._snackBar.open(this.resposta.messagem, "", {duration : 3000})
          }
        }
      }) 
    }
  }

  quadroVazio() : QuadroHorario[] {
    this.quadroHorario = [];
    return this.quadroHorario;
  }

  editaAula(id : number){
    this.router.navigate(['aula/atualizar', id])
  }
}

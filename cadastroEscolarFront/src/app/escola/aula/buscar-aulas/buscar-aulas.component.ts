import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder } from '@angular/forms';
import { Router } from '@angular/router';
import { QuadroHorario } from 'src/app/models/quadroHorario';
import { QuadroHorariosService } from 'src/app/services/quadro-horarios.service';

@Component({
  selector: 'app-buscar-aulas',
  templateUrl: './buscar-aulas.component.html',
  styleUrls: ['./buscar-aulas.component.css']
})
export class BuscarAulasComponent implements OnInit{

  formulario : FormGroup;
  quadroHorario : QuadroHorario [] = [];

  ngOnInit(): void {
    this.formulario = this.fb.group({
      filtro : [null],
      valor : [0]
    });
  }

  constructor(
    private quadroHorarioService : QuadroHorariosService,
    private fb : FormBuilder,
    private router: Router
  ){}

  filtrar(){
    if(this.formulario.controls["valor"].value == 1){
      this.quadroHorarioService.findByMatricula(this.formulario.controls["filtro"].value).subscribe({
        next: quadr => {
          this.quadroHorario = this.quadroVazio();
          this.quadroHorario = quadr;
        },
        error : err => console.log("Error", err)
      }) 
    }
    if(this.formulario.controls["valor"].value == 2){
      this.quadroHorarioService.findByTurma(this.formulario.controls["filtro"].value).subscribe({
        next: quadr => {
          this.quadroHorario = this.quadroVazio();
          this.quadroHorario = quadr;
        },
        error : err => console.log("Error", err)
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

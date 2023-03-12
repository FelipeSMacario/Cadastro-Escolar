import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Dia } from 'src/app/models/dia';
import { QuadroDTO } from 'src/app/models/DTO/quadroDTO';
import { Horas } from 'src/app/models/horas';
import { Materia } from 'src/app/models/materia';
import { QuadroHorario } from 'src/app/models/quadroHorario';
import { DefaultResponse } from 'src/app/models/Response/defaultResponse';
import { ResponseDias } from 'src/app/models/Response/responseDias';
import { ResponseFiltroTurma } from 'src/app/models/Response/responseFiltroTurma';
import { ResponseHoras } from 'src/app/models/Response/ResponseHoras';
import { ResponseMaterias } from 'src/app/models/Response/responseMaterias';
import { Turma } from 'src/app/models/turma';
import { DiaService } from 'src/app/services/dia.service';
import { MateriasService } from 'src/app/services/materias.service';
import { QuadroHorariosService } from 'src/app/services/quadro-horarios.service';
import { TurmaService } from 'src/app/services/turma.service';

@Component({
  selector: 'app-cadastrar-aulas',
  templateUrl: './cadastrar-aulas.component.html',
  styleUrls: ['./cadastrar-aulas.component.css']
})
export class CadastrarAulasComponent implements OnInit{

  formulario : FormGroup;
  turma : Turma[] = [];
  materias : Materia[] = [];
  dias : Dia[] = [];
  horas : Horas[] = [];
  quadro : QuadroDTO = new QuadroDTO();
  quadroHorario : QuadroHorario = new QuadroHorario();
  resposta : DefaultResponse;
  respostaTurma : ResponseFiltroTurma;
  respostaMateria : ResponseMaterias;
  respostaDias : ResponseDias;
  respostaHoras : ResponseHoras;

  ngOnInit(): void {
    this.formularioVazio();
    this.listarTurma();
    this.listarMaterias();
    this.listarDias();
  }
  
  constructor(
    private quadroHorarioService : QuadroHorariosService,
    private turmaService : TurmaService,
    private materiaService : MateriasService,
    private diaService : DiaService,
    private fb : FormBuilder,
    private _snackBar: MatSnackBar
  ){}

  formularioVazio(){
    this.formulario = this.fb.group({
      turma : [null, [Validators.required]],
      materia : [null, [Validators.required]],
      dia : [null, [Validators.required]],
      hora : [null, [Validators.required]],
    });
  }


  listarTurma(){
    this.turmaService.findAll().subscribe({
      next : tur => {
        this.resposta = tur;
        if(this.resposta.success){
          this.respostaTurma = this.resposta.data;
          this.turma = this.respostaTurma.turmaList;
        }else {
          this._snackBar.open(this.resposta.messagem, "", {duration : 3000})
        }
        
      }
    });
  } 


  listarMaterias(){
    this.materiaService.listarMateria().subscribe({
      next : mat => {
        this.resposta = mat;
        if(this.resposta.success){
          this.respostaMateria = this.resposta.data;
          this.materias = this.respostaMateria.materias;
        }else {
          this._snackBar.open(this.resposta.messagem, "", {duration : 3000})
        }
      }
    });
  }
  listarDias(){
    this.diaService.findAll().subscribe({
      next : dia => {
        this.resposta = dia;
        if(this.resposta.success){
          this.respostaDias = this.resposta.data;
          this.dias = this.respostaDias.dias;
        }else {
          this._snackBar.open(this.resposta.messagem, "", {duration : 3000})
        }
      }
    });
  } 


  atualizar(dia : number, turma : number){
    if(dia && turma){
      console.log("passei")
    this.quadroHorarioService.findHorasLivres(dia, turma).subscribe({
      next : hor => {
        this.resposta = hor;
        if(this.resposta.success){
          this.respostaHoras = this.resposta.data;
          this.horas = this.respostaHoras.horas;
        }else {
          this._snackBar.open(this.resposta.messagem, "", {duration : 3000})
        }
      }
    })
  }
  }

  resetaDia(){
    this.formulario.controls['dia'].setValue(null);
  }

  cadastrar(){   
    this.quadroHorarioService.saveHorasLivres(this.defineQuadro()).subscribe({
      next : quad => {
        this.resposta = quad;
        this._snackBar.open(this.resposta.messagem, "", {duration : 5000});
        if(this.resposta.success){
          this.formularioVazio();
        }
      },
    })
  }

  defineQuadro() : QuadroDTO{
    this.quadro.idTurma = this.formulario.value.turma
    this.quadro.idSala = this.formulario.value.sala
    this.quadro.idMateria = this.formulario.value.materia
    this.quadro.idDia = this.formulario.value.dia
    this.quadro.idHora = this.formulario.value.hora

    return this.quadro;
  }

  filtrarMateria(dia : number, hora : number){
    if(dia && hora){
    this.quadroHorarioService.filtrarMaterias(dia, hora).subscribe({
      next : mat => {
        this.resposta = mat;
        if(this.resposta.success){
          this.respostaMateria = this.resposta.data;
          this.materias = this.respostaMateria.materias;
          this.formulario.controls['materia'].setValue(null);
        }else {
          this._snackBar.open(this.resposta.messagem, "", {duration : 3000})
        }
      }
    })
  }
}

}

import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute, Router } from '@angular/router';
import { Notas } from 'src/app/models/notas';
import { DefaultResponse } from 'src/app/models/Response/defaultResponse';
import { ResponseFiltroTurma } from 'src/app/models/Response/responseFiltroTurma';
import { ResponseMaterias } from 'src/app/models/Response/responseMaterias';
import { RespostaNotas } from 'src/app/models/Response/respostaNotas';
import { NotasService } from 'src/app/services/notas.service';

@Component({
  selector: 'app-atualizar-notas',
  templateUrl: './atualizar-notas.component.html',
  styleUrls: ['./atualizar-notas.component.css']
})
export class AtualizarNotasComponent implements OnInit{

  id : number;
  formulario : FormGroup;
  resposta : DefaultResponse;
  nota : Notas;
  respostaTurma : ResponseFiltroTurma;
  respostaMateria : ResponseMaterias;
  respostaNota : RespostaNotas;

 constructor(
  private fb : FormBuilder,
  private activatedRoute : ActivatedRoute,
  private notaService : NotasService,
  private _snackBar: MatSnackBar,
  private router: Router,
 ){} 
  ngOnInit(): void {
    this.id = this.activatedRoute.snapshot.params['id'];
    this.formularioVazio();
    this.buscarNotas();
  }

  formularioVazio(){
    this.formulario = this.fb.group({
      notaId : [null],
      matriculaProfessor: [null],
      matriculaAluno: [null],
      matriculaNome : [null],
      materiaNome: [null],
      materia : [null],
      nota: [null],
      trimeste: [null],
      turma : [null],
      turmaId : [null]
    });
  }
  formularioPreenchido(nota : Notas){
    this.formulario = this.fb.group({
      notaId : nota.id,
      matriculaProfessor: nota.professor.matricula,
      matriculaAluno: nota.aluno.matricula,
      matriculaNome : nota.aluno.nome,
      materiaNome: nota.materia.nome,
      materia : [nota.materia.id, [Validators.required]],
      nota: [nota.nota, [Validators.required]],
      trimeste:nota.trimestre,
      turma :nota.turma.numero,
      turmaId : nota.turma.id
    });
  }

  buscarNotas(){
    this.notaService.findById(this.id).subscribe({
      next : not => {
        this.resposta = not;
        if(this.resposta.success){
          this.nota = this.resposta.data;
          this.formularioPreenchido(this.nota)  
        }
        
      },
      error : err => this._snackBar.open(err, "", {duration : 5000})
    })
  }

  atualizarNotas(){
    this.notaService.updateNota(this.formulario.value).subscribe({
      next : async not => {
        this._snackBar.open("Nota atualizada com sucesso", "", {duration : 5000});
          await new Promise(f => setTimeout(f, 5000));
          this.router.navigate(['escola/notas/buscar'])
      },
      error : err => this._snackBar.open(err, "", {duration : 5000})
    })
  }

}

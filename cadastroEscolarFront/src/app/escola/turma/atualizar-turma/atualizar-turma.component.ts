import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute } from '@angular/router';
import { AlunoTurmaDTO } from 'src/app/models/DTO/alunoTurmaDTO';
import { Pessoa } from 'src/app/models/pessoa';
import { DefaultResponse } from 'src/app/models/Response/defaultResponse';
import { ResponseFiltroTurma } from 'src/app/models/Response/responseFiltroTurma';
import { Turma } from 'src/app/models/turma';
import { TurmaAlunoService } from 'src/app/services/turma-aluno.service';
import { TurmaService } from 'src/app/services/turma.service';

@Component({
  selector: 'app-atualizar-turma',
  templateUrl: './atualizar-turma.component.html',
  styleUrls: ['./atualizar-turma.component.css']
})
export class AtualizarTurmaComponent implements OnInit{

  formulario : FormGroup;
  id : number;
  resposta : DefaultResponse;
  turmas : Turma[];
  alunoTurma : AlunoTurmaDTO;
  alunoTurmaSaida : AlunoTurmaDTO;
  aluno : Pessoa;
  respostaTurma : ResponseFiltroTurma;

  constructor(
    private fb : FormBuilder,
    private turmaService : TurmaService,
    private turmaAlunoService : TurmaAlunoService,
    private activatedRoute : ActivatedRoute,
    private _snackBar: MatSnackBar
    ){}

  ngOnInit(): void {
    this.formularioVazio();
    this.id =this.activatedRoute.snapshot.params['id'];
    
    if(this.id){
      this.turmaAlunoService.findAlunoTurmaById(this.id).subscribe({
        next : resp => {
          this.resposta = resp;

          if(this.resposta.success){
            this.alunoTurma = this.resposta.data;
            this.formularioPreenchido(this.alunoTurma)
          }else {
            this._snackBar.open(this.resposta.messagem, "", {duration : 3000})
          }
        }
      })
    }
  }

  formularioVazio(){
    this.formulario = this.fb.group({
      id : null,
      turmaId : null,
      alunoMatricula : null,
      aluno : null
    });
  }

  formularioPreenchido(objetoTurmaAluno : AlunoTurmaDTO){
    this.filtrarTurmasPorAno(objetoTurmaAluno.turma.ano);
    this.aluno = objetoTurmaAluno.aluno;

    this.formulario = this.fb.group({
      id : [objetoTurmaAluno.id],
      turmaId : [objetoTurmaAluno.turma.id],
      alunoMatricula : [this.aluno.nome + " " + this.aluno.sobreNome],
      aluno : [objetoTurmaAluno.aluno] 
    
    })
  }

  filtraTurma(id : number) : Turma{
    let turma = this.turmas.filter(v => v.id === id)[0]
    return turma;
  }


  atualizar(){
    this.turmaAlunoService.updateAlunoTurma(this.montaSaida()).subscribe({
      next : resp => {
        this.resposta = resp;
          this._snackBar.open(this.resposta.messagem, "", {duration : 3000})
      }
    })
  }

  filtrarTurmasPorAno(ano : number){
    this.turmaService.findTurmaPorANo(ano).subscribe({
      next : tur => {
        this.resposta = tur;
        if(this.resposta.success){
          this.respostaTurma = this.resposta.data;
          this.turmas = this.respostaTurma.turmaList;
        }else {
          this._snackBar.open(this.resposta.messagem, "", {duration : 3000})
        }
      }
    })
  }

  montaSaida() : AlunoTurmaDTO{
    return new AlunoTurmaDTO(this.formulario.value.id, this.formulario.value.aluno, this.filtraTurma(this.formulario.value.turmaId));
  }

}

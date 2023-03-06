import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { AlunoTurmaDTO } from 'src/app/models/DTO/alunoTurmaDTO';
import { Pessoa } from 'src/app/models/pessoa';
import { DefaultResponse } from 'src/app/models/Response/defaultResponse';
import { Turma } from 'src/app/models/turma';
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

  constructor(
    private fb : FormBuilder,
    private turmaService : TurmaService,
    private activatedRoute : ActivatedRoute,
    ){}

  ngOnInit(): void {
    this.formularioVazio();
    this.id =this.activatedRoute.snapshot.params['id'];
    
    if(this.id){
      this.turmaService.findAlunoTurmaById(this.id).subscribe({
        next : resp => {
          this.resposta = resp;

          if(this.resposta.success){
            this.alunoTurma = this.resposta.data;
            this.formularioPreenchido(this.alunoTurma)
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
    this.turmaService.updateAlunoTurma(this.montaSaida()).subscribe({
      next : resp => {
        this.resposta = resp;

        if(this.resposta.success){
          console.log(this.resposta.messagem)
        }
      }
    })
  }

  filtrarTurmasPorAno(ano : number){
    this.turmaService.findTurmaPorANo(ano).subscribe({
      next : tur => this.turmas = tur,
      error : err => console.log(err)
    })
  }

  montaSaida() : AlunoTurmaDTO{
    return new AlunoTurmaDTO(this.formulario.value.id, this.formulario.value.aluno, this.filtraTurma(this.formulario.value.turmaId));
  }

}

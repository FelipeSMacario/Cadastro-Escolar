import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { ModalConfirmacaoComponent } from 'src/app/modal/modal-confirmacao/modal-confirmacao.component';
import { AlunoTurmaDTO } from 'src/app/models/DTO/alunoTurmaDTO';
import { Pessoa } from 'src/app/models/pessoa';
import { DefaultResponse } from 'src/app/models/Response/defaultResponse';
import { PessoaService } from 'src/app/services/pessoa.service';
import { TurmaService } from 'src/app/services/turma.service';

@Component({
  selector: 'app-filtrar-turma',
  templateUrl: './filtrar-turma.component.html',
  styleUrls: ['./filtrar-turma.component.css']
})
export class FiltrarTurmaComponent  implements OnInit{

  private readonly cargo : string = "alunos";
  formulario : FormGroup;
  pessoas : Pessoa[];
  resposta : DefaultResponse;
  turmaAluno : AlunoTurmaDTO[];

  constructor(
    private alunoService : PessoaService,
    private fb : FormBuilder,
    private router: Router,
    private turmaService : TurmaService,
    private dialog : MatDialog,
    private _snackBar: MatSnackBar,
  ){}
  
  ngOnInit(): void {
    this.formularioVazio();
  }

  formularioVazio(){
    this.formulario = this.fb.group({
      valor : [0],
      filtro : [null]
    })  
  }

  atualizar(matricula : number){
    this.router.navigate(['turmas/atualizar', matricula])
  }

  buscar(){

    if (this.formulario.controls["valor"].value == 1) {
      this.turmaAluno = this.removeTodos();
      this.turmaService.findTurmaAlunoByNome( this.formulario.controls["filtro"].value).subscribe({
        next: pessoa => {
          this.turmaAluno = pessoa;
        },
        error : err => console.log("Error", err)
      }) 
    }

    if (this.formulario.controls["valor"].value == 2) {
      this.turmaAluno = this.removeTodos();
      this.turmaService.findTurmaByMatricula(this.formulario.controls["filtro"].value).subscribe({
        next: pessoa => {
          this.resposta = pessoa;

          if(this.resposta.success){
            this.turmaAluno = this.removeTodos();
            this.turmaAluno.push(this.resposta.data);
          }else {
            console.log("Error", this.resposta.messagem)
          }         
        }
      }) 

      
    }

    if (this.formulario.controls["valor"].value == 3) {
      this.turmaAluno = this.removeTodos();
      this.turmaService.findAlunsByNumero(this.formulario.controls["filtro"].value).subscribe({
        next: pessoa => {
          this.turmaAluno = pessoa;
        },
        error : err => console.log("Error", err)
      }) 
    }   

    if (this.formulario.controls["valor"].value == 4) {
      this.turmaAluno = this.removeTodos();
      this.listarTodos();
    }   

  }

  removeTodos() : AlunoTurmaDTO[]{
    this.turmaAluno = [];
      return this.turmaAluno;
  }

  modalDeletar(matricula : number, turma : number){
    const dialogRef = this.dialog.open(ModalConfirmacaoComponent, {
      data: "Tem certeza que deseja excluir o vinculo entre aluno e turma?",
    });
  
    dialogRef.afterClosed().subscribe((result : boolean) => {
      if(result){
        this.deletarTurmaAluno(matricula, turma);      
      }      
    });
  }

  deletarTurmaAluno(matricula : number, turma : number){
    this.turmaService.deleteTurmaAluno(matricula, turma).subscribe({
      next : mat =>  {
         this.resposta = mat;
         
         if(this.resposta.success){
          this._snackBar.open(this.resposta.messagem, "", {duration : 3000})
          this.listarTodos()
         } else {
          this._snackBar.open(this.resposta.messagem, "", {duration : 3000})
         }
      }
    })
  }

  listarTodos(){
    this.turmaAluno = this.removeTodos();
    this.turmaService.findAllTurmaAluno().subscribe({
      next: pessoa => {
        this.turmaAluno = pessoa;
      },
      error : err => console.log("Error", err)
    }) 
  }


}

import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { ModalConfirmacaoComponent } from 'src/app/modal/modal-confirmacao/modal-confirmacao.component';
import { AlunoTurmaDTO } from 'src/app/models/DTO/alunoTurmaDTO';
import { Page } from 'src/app/models/page';
import { Pessoa } from 'src/app/models/pessoa';
import { DefaultResponse } from 'src/app/models/Response/defaultResponse';
import { ResponseTurmaAluno } from 'src/app/models/Response/responseTurmaAluno';
import { TurmaAlunoService } from 'src/app/services/turma-aluno.service';

@Component({
  selector: 'app-filtrar-turma',
  templateUrl: './filtrar-turma.component.html',
  styleUrls: ['./filtrar-turma.component.css']
})
export class FiltrarTurmaComponent  implements OnInit{

  formulario : FormGroup;
  pessoas : Pessoa[];
  resposta : DefaultResponse;
  turmaAluno : AlunoTurmaDTO[];
  objetoTurmaAluno : AlunoTurmaDTO;
  respostaTurmaALuno : ResponseTurmaAluno;
  pagina : Page;

  constructor(
    private fb : FormBuilder,
    private router: Router,
    private turmaService : TurmaAlunoService,
    private dialog : MatDialog,
    private _snackBar: MatSnackBar,
  ){}
  
  ngOnInit(): void {
    this.formularioVazio();
  }

  formularioVazio(){
    this.formulario = this.fb.group({
      valor : [0, [Validators.required, Validators.min(1), Validators.max(4)]],
      filtro : [null, [Validators.required]]
    })  
  }

  atualizar(matricula : number, turma : number){
    this.turmaAluno = this.removeTodos();
    this.turmaService.findTurmaAluno(matricula, turma).subscribe({
      next : res => {
        this.resposta = res;
        if(this.resposta.success){
          this.objetoTurmaAluno  = this.resposta.data;
          this.router.navigate(['turmas/atualizar', this.objetoTurmaAluno.id])
        }else {
          console.log(this.resposta.messagem + " erro")
      }
    }
    })
  }

  buscar(){

    if (this.formulario.controls["valor"].value == 1) {
     
      this.turmaService.findTurmaAlunoByNome( this.formulario.controls["filtro"].value).subscribe({
        next: pessoa => {
          this.resposta = pessoa;

          if(this.resposta.success){
            this.turmaAluno = this.removeTodos();
            this.respostaTurmaALuno = this.resposta.data;
            this.turmaAluno = this.respostaTurmaALuno.alunoTurmaDTOList;
          } else {
            this._snackBar.open(this.resposta.messagem, "", {duration : 3000})
          }
        }
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
            this._snackBar.open(this.resposta.messagem, "", {duration : 3000})
          }         
        }
      }) 

      
    }

    if (this.formulario.controls["valor"].value == 3) {
      this.filtrarPorturma();
    }   

  }

  filtrarPorturma(pagina? : number){
    this.turmaAluno = this.removeTodos();
    this.turmaService.findAlunsByNumero(this.formulario.controls["filtro"].value, pagina).subscribe({
      next: pessoa => {
        this.resposta = pessoa;

        if(this.resposta.success){
          this.pagina = this.resposta.data;
          this.turmaAluno = this.pagina.content;
        } else {
          this._snackBar.open(this.resposta.messagem, "", {duration : 3000})
        }
      }
    }) 
  }

  removeTodos() : AlunoTurmaDTO[]{
    this.turmaAluno = [];
    this.pagina = new Page();
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
        this.resposta = pessoa;

        if(this.resposta.success){
          this.turmaAluno = this.resposta.data;
        } else {
          this._snackBar.open(this.resposta.messagem, "", {duration : 3000})
        }
      },
      error : err => console.log("Error", err)
    }) 
  }

  proximo(){
    this.filtrarPorturma(this.pagina.number + 1);
  }

  anterior(){
    this.filtrarPorturma(this.pagina.number - 1);
  }

  definePagina(pagina : number){
    this.filtrarPorturma(pagina - 1);
  }




}

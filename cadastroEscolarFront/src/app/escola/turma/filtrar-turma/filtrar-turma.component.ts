import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
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

  constructor(
    private alunoService : PessoaService,
    private fb : FormBuilder,
    private router: Router,
    private turmaService : TurmaService
  ){}
  
  ngOnInit(): void {
    this. formularioVazio();
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
      this.pessoas = this.removeTodos();
      this.alunoService.findAlunosByNome(this.cargo, this.formulario.controls["filtro"].value).subscribe({
        next: pessoa => {
          this.pessoas = pessoa;
        },
        error : err => console.log("Error", err)
      }) 
    }

    if (this.formulario.controls["valor"].value == 2) {
      this.pessoas = this.removeTodos();
      this.alunoService.findAlunosByMatricula(this.cargo, this.formulario.controls["filtro"].value).subscribe({
        next: pessoa => {
          this.resposta = pessoa;

          if(this.resposta.success){
            this.pessoas = this.removeTodos();
            this.pessoas.push(this.resposta.data);
          }else {
            console.log("Error", this.resposta.messagem)
          }
         
        }
      }) 
    }

    if (this.formulario.controls["valor"].value == 3) {
      this.turmaService.findAlunsByNumero(this.formulario.controls["filtro"].value).subscribe({
        next: pessoa => {
          this.pessoas = pessoa;
        },
        error : err => console.log("Error", err)
      }) 
    }   

    if (this.formulario.controls["valor"].value == 4) {
      this.alunoService.findAllAlunos(this.cargo).subscribe({
        next: pessoa => {
          this.pessoas = pessoa;
        },
        error : err => console.log("Error", err)
      }) 
    }   
  }

  removeTodos() : Pessoa[]{
    this.pessoas = [];
      return this.pessoas;
  }


}

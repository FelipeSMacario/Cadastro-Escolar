import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { Pessoa } from 'src/app/models/pessoa';
import { DefaultResponse } from 'src/app/models/Response/defaultResponse';
import { PessoaService } from 'src/app/services/pessoa.service';

@Component({
  selector: 'app-buscar-alunos',
  templateUrl: './buscar-alunos.component.html',
  styleUrls: ['./buscar-alunos.component.css']
})
export class BuscarAlunosComponent implements OnInit{

  pessoas : Pessoa[];
  resposta : DefaultResponse;
  formulario : FormGroup;

  private readonly cargo : string = "alunos";

  constructor(
    private alunoService : PessoaService,
    private fb : FormBuilder,
    private router: Router){ }

  ngOnInit(): void {  
    this.formulario = this.fb.group({
      valor : [0, [Validators.required, Validators.min(1), Validators.max(3)]],
      filtro : [null, [Validators.required]]
    })  
  }

  buscar(){
    
    if (this.formulario.controls["valor"].value == 1) {
      this.alunoService.findAlunosByNome(this.cargo, this.formulario.controls["filtro"].value).subscribe({
        next: pessoa => {
          this.pessoas = this.removeTodos();
          this.pessoas = pessoa;
        },
        error : err => console.log("Error", err)
      }) 
    }

    if (this.formulario.controls["valor"].value == 2) {
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
      this.alunoService.findAllAlunos(this.cargo).subscribe({
        next: pessoa => {
          this.pessoas = this.removeTodos();
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

  atualizar(matricula : number){
    this.router.navigate(['alunos/atualizar', matricula])
  }

}

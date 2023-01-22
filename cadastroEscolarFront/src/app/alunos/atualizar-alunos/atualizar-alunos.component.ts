import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Pessoa } from 'src/app/models/pessoa';
import { AlunosService } from 'src/app/services/alunos.service';

@Component({
  selector: 'app-atualizar-alunos',
  templateUrl: './atualizar-alunos.component.html',
  styleUrls: ['./atualizar-alunos.component.css']
})
export class AtualizarAlunosComponent implements OnInit{

  pessoa : Pessoa = new Pessoa();
  formulario : FormGroup;
  matricula : number;

  constructor(
    private fb : FormBuilder,
    private alunoService : AlunosService,
    private activatedRoute : ActivatedRoute
    ){}
  
  ngOnInit(): void {

    

    this.matricula = this.activatedRoute.snapshot.params['matricula'];

    this.alunoService.findAlunosByMatricula(this.matricula).subscribe({
      next: pessoa => {
       this.pessoa = pessoa;    
      },
      error : err => console.log("Error", err)
    });

    this.formulario = this.fb.group({
      matricula : [null],
      cpf : [null],
      nome : [null],
      sobreNome : [null],
      dataNascimento : [null],
      dataCadastro : [null],
      cargo : [null],
      status : [null]
    });

  }

  teste(){
    console.log(this.formulario)
  }

  salvarAluno(){}

}

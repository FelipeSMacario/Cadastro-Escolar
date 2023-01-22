import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { take } from 'rxjs';
import { Pessoa } from 'src/app/models/pessoa';
import { PessoaService } from 'src/app/services/pessoa.service';

@Component({
  selector: 'app-atualizar-alunos',
  templateUrl: './atualizar-alunos.component.html',
  styleUrls: ['./atualizar-alunos.component.css']
})
export class AtualizarAlunosComponent implements OnInit{

  pessoa : Pessoa = new Pessoa();
  formulario : FormGroup;
  matricula : number;
  private readonly cargo : string = "alunos";

  constructor(
    private fb : FormBuilder,
    private alunoService : PessoaService,
    private activatedRoute : ActivatedRoute
    ){}
  
  ngOnInit(): void {

    

    this.matricula = this.activatedRoute.snapshot.params['matricula'];

    this.alunoService.findAlunosByMatricula(this.cargo, this.matricula).subscribe({
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


  salvarAluno(){
    this.alunoService.updateAlunos(this.cargo, this.formulario.value).pipe(take(1)).subscribe({
      next : user => console.log("Cadastrado com sucesso", user),
      error : err => console.log(err)
    })
  }

}

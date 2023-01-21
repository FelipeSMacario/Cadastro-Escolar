import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { take } from 'rxjs';
import { Pessoa } from 'src/app/models/pessoa';
import { AlunosService } from 'src/app/services/alunos.service';

@Component({
  selector: 'app-cadastro-alunos',
  templateUrl: './cadastro-alunos.component.html',
  styleUrls: ['./cadastro-alunos.component.css']
})
export class CadastroAlunosComponent implements OnInit{

  formulario : FormGroup;
  pessoa : Pessoa = new Pessoa();

  constructor(
    private fb : FormBuilder,
    private alunoService : AlunosService){
    
  }
  ngOnInit(): void {
    this.formulario = this.fb.group({
      cpf : [null],
      dataNascimento : [null],
      nome : [null],
      sobreNome : [null]
    })
  }
  salvarAluno(){
    this.alunoService.salvarAluno(this.formulario.value).pipe(take(1)).subscribe({
      next : user => console.log("Cadastrado com sucesso", user),
      error : err => console.log(err)
    })

    console.log(this.formulario)
  }

}

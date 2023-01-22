import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder } from '@angular/forms';
import { take } from 'rxjs';
import { Pessoa } from 'src/app/models/pessoa';
import { PessoaService } from 'src/app/services/pessoa.service';

@Component({
  selector: 'app-cadastro-professores',
  templateUrl: './cadastro-professores.component.html',
  styleUrls: ['./cadastro-professores.component.css']
})
export class CadastroProfessoresComponent  implements OnInit{

  formulario : FormGroup;
  pessoa : Pessoa = new Pessoa();
  private readonly cargo : string = "professor";

  constructor(
    private fb : FormBuilder,
    private alunoService : PessoaService){
    
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
    this.alunoService.salvarAluno(this.cargo, this.formulario.value).pipe(take(1)).subscribe({
      next : user => console.log("Cadastrado com sucesso", user),
      error : err => console.log(err)
    })
  }


}

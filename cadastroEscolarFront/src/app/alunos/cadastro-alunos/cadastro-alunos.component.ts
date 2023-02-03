import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { DomSanitizer, SafeResourceUrl } from '@angular/platform-browser';
import { min, take } from 'rxjs';
import { Pessoa } from 'src/app/models/pessoa';
import { PessoaService } from 'src/app/services/pessoa.service';

@Component({
  selector: 'app-cadastro-alunos',
  templateUrl: './cadastro-alunos.component.html',
  styleUrls: ['./cadastro-alunos.component.css']
})
export class CadastroAlunosComponent implements OnInit{

  formulario : FormGroup;
  pessoa : Pessoa = new Pessoa();
  private readonly cargo : string = "alunos";
  urlFoto : SafeResourceUrl;
  imagem2 : any;

  constructor(
    private fb : FormBuilder,
    private alunoService : PessoaService,
    private sanitizer : DomSanitizer){
    
  }
  ngOnInit(): void {
    this.formulario = this.fb.group({
      cpf : [null, [Validators.required, Validators.minLength(11)]],
      dataNascimento : [null, [Validators.required]],
      nome : [null, [Validators.required]],
      sobreNome : [null, [Validators.required]],
      urlFoto : [null]
    })
  }
  salvarAluno(){
    this.alunoService.salvarAluno(this.cargo, this.formulario.value).pipe(take(1)).subscribe({
      next : user => console.log("Cadastrado com sucesso", user),
      error : err => console.log(err)
    })
  }

  carregaImagem(event : Event){
    let valor  = this.base(event);
  }

  base(event : Event) : any{
    const target = event.target as HTMLInputElement;
  
    const file : File = (target.files as FileList)[0];
    const reader = new FileReader();
    reader.readAsDataURL(file);
    reader.onload = () => {
        this.imagem2 = reader.result;
        this.urlFoto = this.sanitizer.bypassSecurityTrustResourceUrl(reader.result as string)
        this.formulario.controls['urlFoto'].setValue( reader.result as string)
  
    };
    return reader.result;
  }

}

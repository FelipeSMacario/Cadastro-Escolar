import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { DomSanitizer, SafeResourceUrl } from '@angular/platform-browser';
import { min, take } from 'rxjs';
import { Pessoa } from 'src/app/models/pessoa';
import { DefaultResponse } from 'src/app/models/Response/defaultResponse';
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
  resposta : DefaultResponse;

  constructor(
    private fb : FormBuilder,
    private alunoService : PessoaService,
    private sanitizer : DomSanitizer,
    private _snackBar: MatSnackBar){
    
  }
  ngOnInit(): void {
    this.formularioVazio();
  }

  formularioVazio(){
    this.formulario = this.fb.group({
      cpf : [null,[Validators.required, Validators.minLength(11), Validators.maxLength(11)]],
      dataNascimento : [null, [Validators.required]],
      nome : [null, [Validators.required, Validators.minLength(3), Validators.maxLength(50)]],
      sobreNome : [null, [Validators.required, Validators.minLength(3), Validators.maxLength(50)]],
      urlFoto : [null],
      email : [null, [Validators.required, Validators.email, Validators.maxLength(100)]],
      ano : [null,  [Validators.required, Validators.min(1), Validators.max(3)]]
    })
  }
  salvarAluno(){
    this.alunoService.salvarAluno(this.cargo, this.formulario.value).pipe(take(1)).subscribe({
      next : user => {
        this.resposta = user;

        if(this.resposta.success){
          this._snackBar.open("Aluno cadastrado com sucesso", "", {duration : 5000});
          this.formularioVazio();
        }else {
          this._snackBar.open(this.resposta.messagem, "", {duration : 5000});
        }
        
      }
     
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

import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { DomSanitizer, SafeResourceUrl } from '@angular/platform-browser';
import { Pessoa } from 'src/app/models/pessoa';
import { DefaultResponse } from 'src/app/models/Response/defaultResponse';
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
      cpf : [null, [Validators.required, Validators.minLength(11), Validators.maxLength(11)]],
      dataNascimento : [null, [Validators.required]],
      nome : [null, [Validators.required, Validators.minLength(3), Validators.maxLength(50)]],
      sobreNome : [null, [Validators.required, Validators.minLength(3), Validators.maxLength(50)]],
      urlFoto : [null],
      email : [null, [Validators.required, Validators.email, Validators.maxLength(100)]],
      ano : [0]
    })
  }

  salvarAluno(){
    this.alunoService.salvarAluno(this.cargo, this.formulario.value).subscribe({
      next : async user => {
        this.resposta = user;

        if(this.resposta.success){
          this._snackBar.open("Professor cadastrado com sucesso", "", {duration : 5000});
          await new Promise(f => setTimeout(f, 3000));
          window.location.reload()
        }else{
          this._snackBar.open(this.resposta.messagem, "", {duration : 5000})
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

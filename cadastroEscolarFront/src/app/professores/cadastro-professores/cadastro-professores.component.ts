import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder } from '@angular/forms';
import { DomSanitizer, SafeResourceUrl } from '@angular/platform-browser';
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
  urlFoto : SafeResourceUrl;
  imagem2 : any;

  constructor(
    private fb : FormBuilder,
    private alunoService : PessoaService,
    private sanitizer : DomSanitizer){
    
  }

  ngOnInit(): void {
    this.formulario = this.fb.group({
      cpf : [null],
      dataNascimento : [null],
      nome : [null],
      sobreNome : [null],
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

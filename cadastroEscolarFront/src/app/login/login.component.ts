import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { DefaultResponse } from '../models/Response/defaultResponse';
import { LoginService } from '../services/login.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit{

  formulario : FormGroup;
  resposta : DefaultResponse;

  constructor(
    private fb : FormBuilder,
    private router: Router,
    private loginService : LoginService,
    private _snackBar: MatSnackBar,
  ) { }

  ngOnInit(): void {
    this.formulario = this.fb.group({
      usuario : [null],
      senha : [null]
    })
  }

  logar(){
    this.loginService.logar(this.formulario.value).subscribe({
      next : log => {
        this.resposta = log;

        if(this.resposta.success){
        localStorage.setItem("token", this.resposta.data.token)
        localStorage.setItem("pessoa", JSON.stringify(this.resposta.data.pessoa))
        localStorage.setItem("mostrarMenu", JSON.stringify(true))
        this.router.navigate(['/']).then(() => window.location.reload())
      } else {
        this._snackBar.open(this.resposta.messagem, "", {duration : 3000})
      }
      }
    })
  }


  

}

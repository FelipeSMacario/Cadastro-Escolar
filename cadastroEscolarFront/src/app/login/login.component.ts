import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { DefaultResponse } from '../models/Response/defaultResponse';
import { LoginService } from '../services/login.service';
import { Auth } from '../models/auth';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit{

  formulario : FormGroup;
  resposta : DefaultResponse;

  token : Auth;

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
    this.loginService.login(this.formulario.value)
  }


  

}

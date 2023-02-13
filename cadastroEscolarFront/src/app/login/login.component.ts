import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { LoginService } from '../services/login.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit{

  formulario : FormGroup;

  constructor(
    private fb : FormBuilder,
    private router: Router,
    private loginService : LoginService
  ) { }

  ngOnInit(): void {
    this.formulario = this.fb.group({
      usuario : [null],
      senha : [null]
    })
  }
  /** [Validators.required, Validators.minLength(11), Validators.maxLength(11)] */

  logar(){
    this.loginService.logar(this.formulario.value).subscribe({
      next : log => {
        localStorage.setItem("token", log.token)
        localStorage.setItem("pessoa", JSON.stringify(log.pessoa))
        localStorage.setItem("mostrarMenu", JSON.stringify(true))
        this.router.navigate(['/']).then(() => window.location.reload())
      },
      error : err => console.log(err)
    })
  }


  

}

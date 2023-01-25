import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit{

  formulario : FormGroup;

  constructor(
    private fb : FormBuilder
  ) { }

  ngOnInit(): void {
    this.formulario = this.fb.group({
      login : [null],
      senha : [null, [Validators.required, Validators.minLength(11), Validators.maxLength(11)]]
    })
  }

}

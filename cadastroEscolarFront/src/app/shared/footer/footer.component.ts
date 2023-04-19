import { Component, OnInit } from '@angular/core';
import { Pessoa } from 'src/app/models/pessoa';

@Component({
  selector: 'app-footer',
  templateUrl: './footer.component.html',
  styleUrls: ['./footer.component.css']
})
export class FooterComponent implements OnInit{
  mostrarMenu : boolean;
  pessoa : Pessoa;
  condition: boolean = true;

  ngOnInit(): void {
    this.mostrarMenu = JSON.parse(localStorage.getItem("mostrarMenu")!);
    this.pessoa = JSON.parse(localStorage.getItem("pessoa")!);

    if(this.pessoa){
      this.condition = true;
    } else {
      this.condition = false;
    }
  }
}

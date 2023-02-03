import { Component, OnInit } from '@angular/core';
import { DomSanitizer, SafeResourceUrl } from '@angular/platform-browser';
import { QuadroHorariosService } from 'src/app/services/quadro-horarios.service';


@Component({
  selector: 'app-notas-home',
  templateUrl: './notas-home.component.html',
  styleUrls: ['./notas-home.component.css']
})
export class NotasHomeComponent implements OnInit{

  imageUrl : SafeResourceUrl;

  imagem : string;


  imagem2 : any;

ngOnInit(): void {
}

constructor(
  private sanitizer : DomSanitizer){
}



abc(){
  let data = this.imagem2;
  console.log(data)
  this.imageUrl = this.sanitizer.bypassSecurityTrustResourceUrl(data)
}

private readBase64(file : File): Promise<any> {
  const reader = new FileReader();
  const future = new Promise((resolve, reject) => {
    reader.addEventListener('load', function () {
      resolve(reader.result);
    }, false);
    reader.addEventListener('error', function (event) {
      reject(event);
    }, false);
  
    reader.readAsDataURL(file);
  });
  return future;
}

base(event : Event) : any{
  const target = event.target as HTMLInputElement;

  const file : File = (target.files as FileList)[0];
  const reader = new FileReader();
  reader.readAsDataURL(file);
  reader.onload = () => {
      this.imagem2 = reader.result;
      this.imageUrl = this.sanitizer.bypassSecurityTrustResourceUrl(reader.result as string)

  };
  return reader.result;
}

teste3(event : Event){
  let valor  = this.base(event);
  console.log("Valor " +  this.imagem2)
}

}
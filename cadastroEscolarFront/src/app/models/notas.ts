import { Materia } from "./materia";
import { Pessoa } from "./pessoa";
import { Turma } from "./turma";

export class Notas {
    
     id : number;

    professor : Pessoa;

    materia : Materia;

    dataInclusao : Date;

    aluno : Pessoa;

    nota : number;

    trimestre : number;

    turma : Turma;
}
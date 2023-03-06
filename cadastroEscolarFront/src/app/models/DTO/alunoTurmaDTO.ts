import { Pessoa } from "../pessoa";
import { Turma } from "../turma";

export class AlunoTurmaDTO{
    id : number;
    aluno : Pessoa;
    turma : Turma

    constructor(id : number, aluno : Pessoa, turma : Turma){
        this.id = id;
        this.aluno = aluno;
        this.turma = turma;
    }
}
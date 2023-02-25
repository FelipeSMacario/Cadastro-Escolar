import { Dia } from "./dia";
import { Horas } from "./horas";
import { Materia } from "./materia";
import { Turma } from "./turma";

export class QuadroHorario {
    id : number;

    turma : Turma;

    materia : Materia;

    horas : Horas;

    dia : Dia;

}
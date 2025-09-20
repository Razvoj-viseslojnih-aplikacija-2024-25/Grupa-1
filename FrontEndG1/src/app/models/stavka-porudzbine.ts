import { Artikl } from './artikl';
import { Porudzbina } from './porudzbina';

export class StavkaPorudzbine {
  id!: number;
  redniBroj!: number;
  kolicina!: number;
  jedinicaMere!: String;
  cena!: number;
  artikl!: Artikl;
  porudzbina!: Porudzbina;
}

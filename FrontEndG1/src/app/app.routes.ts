import { Routes } from '@angular/router';
import { ArtiklComponent } from './components/main/artikl-component/artikl-component';
import { DobavljacComponent } from './components/main/dobavljac-component/dobavljac-component';
import { PorudzbinaComponent } from './components/main/porudzbina-component/porudzbina-component';
import { StavkaPorudzbineComponent } from './components/main/stavka-porudzbine-component/stavka-porudzbine-component';
import { HomeComponent } from './components/utility/home-component/home-component';
import { AboutComponent } from './components/utility/about-component/about-component';
import { AuthorComponent } from './components/utility/author-component/author-component';

export const routes: Routes = [
  { path: 'artikl', component: ArtiklComponent },
  { path: 'dobavljac', component: DobavljacComponent },
  { path: 'porudzbina', component: PorudzbinaComponent},
  { path: 'stavka-porudzbine', component: StavkaPorudzbineComponent },
  { path: 'home', component: HomeComponent },
  { path: 'about', component: AboutComponent },
  { path: 'author', component: AuthorComponent },
  { path: '', component: HomeComponent, pathMatch: 'full' }
];

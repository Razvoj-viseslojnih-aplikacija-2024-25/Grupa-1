import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { StavkaPorudzbine } from '../models/stavka-porudzbine';

@Injectable({
  providedIn: 'root',
})
export class StavkaPorudzbineService {
  constructor(private httpClient: HttpClient) {}

  public getAllStavkaPorudzbines(): Observable<any> {
    return this.httpClient.get('http://localhost:8080/stavkaPorudzbines');
  }

  public getStavkeByPorudzbina(porudzbinaId: number): Observable<any> {
    return this.httpClient.get(`http://localhost:8080/stavkaPorudzbine/porudzbina/${porudzbinaId}`);
  }

  public createStavkaPorudzbine(stavkaPorudzbine: StavkaPorudzbine): Observable<any> {
    return this.httpClient.post('http://localhost:8080/stavkaPorudzbine', stavkaPorudzbine);
  }

  public updateStavkaPorudzbine(stavkaPorudzbine: StavkaPorudzbine): Observable<any> {
    return this.httpClient.put(
      `http://localhost:8080/stavkaPorudzbine/${stavkaPorudzbine.id}`,
      stavkaPorudzbine
    );
  }

  public deleteStavkaPorudzbine(id: number): Observable<any> {
    return this.httpClient.delete(`http://localhost:8080/stavkaPorudzbine/${id}`, {
      responseType: 'text',
    });
  }
}
